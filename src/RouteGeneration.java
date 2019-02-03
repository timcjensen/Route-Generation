/*
    Route Generation program
    CS-410 Software Engineering: Iyengar
    Due: 1/30/2019
    Elky Ratliff
    Tim Jensen
 */

/*
    Input:
            Address.dat, a list of random addresses
            Layout.dat, a list of adjacent streets
    Output:
            Route.dat, an organized list of addresses
                        that is a suggested route
 */

/*
    TODO:
        - UI?
        - Export/create presentation
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RouteGeneration {

    private static BufferedReader reader;
    private static int routeCount = 0;

    public static void main(String[] args) throws IOException {
        String addressSource = "Address.dat";
        String layoutSource = "Layout.dat";
        String outputFile = "Route.dat";

        List<Address> addressList = parseAddress(addressSource);

        System.out.println("Addresses:\n");
        for(Address a : addressList){
            System.out.println(a.toString());
        }
        List<Layout> layoutList = parseLayout(layoutSource);

        System.out.println("\nLayouts:\n");
        for(Layout l : layoutList){
            System.out.println(l.toString());
        }

        List<String> routeList = determineRoute(addressList, layoutList);

        System.out.println("\nRoute:\n");
        for(String s: routeList){
            System.out.println(s);
        }

        writeResult(routeList, outputFile);


        JFrame frame = new JFrame("Route Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,300);

        JButton btn3 = new JButton("Show Address file");
        JButton btn2 = new JButton("Show Layout file");
        JButton btn1 = new JButton("Choose file");

        btn3.setPreferredSize(new Dimension(200, 200));
        btn2.setPreferredSize(new Dimension(200, 200));
        btn1.setPreferredSize(new Dimension(200, 200));

        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(btn3);
        frame.getContentPane().add(btn2);
        frame.getContentPane().add(btn1);

        btn3.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String fileName = "Address.dat";

                String line = null;

                try
                {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader b = new BufferedReader(fileReader);
                    while((line = b.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                    b.close();
                }
                catch(FileNotFoundException ex)
                {
                    System.out.println("Unable to open file '" + fileName + "'");
                }
                catch(IOException ex)
                {
                    System.out.println("Error reading file '" + fileName + "'");
                }
            }
        });

        btn2.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String fileName = "Layout.dat";
                String line = null;

                try
                {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader b = new BufferedReader(fileReader);
                    while((line = b.readLine()) != null)
                    {
                        System.out.println(line);
                    }
                    b.close();
                }
                catch(FileNotFoundException ex)
                {
                    System.out.println("Unable to open file '" + fileName + "'");
                }
                catch(IOException ex)
                {
                    System.out.println("Error reading file '" + fileName + "'");
                }
            }
        });

        btn1.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "dat", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                    chooser.getSelectedFile();

                }
            }
        });

        frame.setVisible(true);

    }

    //addresses can be parsed in a straightforward way
    private static List<Address> parseAddress(String source) throws IOException {
        FileReader file = new FileReader(source);
        reader = new BufferedReader(file);
        List<Address> addresses = new ArrayList<>();
        String line;

        while((line = reader.readLine()) != null){
            int length = 0;
            String number = line.substring(0, line.indexOf(" "));
            length += number.length() + 1;
            String street = line.substring(length, line.indexOf(","));
            length += street.length() + 2;
            String city = line.substring(length, line.indexOf(",", length));
            length += city.length() + 2;
            String zip = line.substring(length);

            addresses.add(new Address(city, zip, number, street));
        }

        reader.close();
        return addresses;
    }

    //layout is trickier - consider difference of cities, zips, vertical/horizontal
    private static List<Layout> parseLayout(String source) throws IOException{
        FileReader file = new FileReader(source);
        reader = new BufferedReader(file);
        List<Layout> layouts = new ArrayList<>();

        String line;

        while((line = reader.readLine()) != null){
            // first line - city + zip
            List<String> verticalStreets = new ArrayList<>();
            List<String> horizontalStreets = new ArrayList<>();

            String city = line.substring(line.indexOf(":") + 2, line.indexOf(","));
            String zip = line.substring(line.indexOf(",") + 2);

            getStreets(verticalStreets);

            reader.readLine();
            getStreets(horizontalStreets);

            layouts.add(new Layout(city, zip, verticalStreets, horizontalStreets));
        }

        return layouts;
    }

    private static void getStreets(List<String> streets) throws IOException {
        String line;
        reader.readLine();

        while((line = reader.readLine()) != null){
            if(line.equals(""))
                break;
            streets.add(line);
        }
    }

    // returns a string list of the generated meaningful route
    private static List<String> determineRoute(List<Address> addresses, List<Layout> layouts){
        List<String> output = new ArrayList<>();

        for(Layout l : layouts){
            List<String> horizOutput = new ArrayList<>();
            List<String> vertOutput = new ArrayList<>();

            for(String vert : l.getVerticalStreets()){
                vertOutput.addAll(matchStreet(addresses, l, vert));
            }

            for(String horiz : l.getHorizontalStreets()){
                horizOutput.addAll(matchStreet(addresses, l, horiz));
            }

            output.add(l.getTitle());
            output.add("");
            output.addAll(vertOutput);
            output.addAll(horizOutput);
            output.add("");
        }

        System.out.println("Route successfully generated. Street count: " + routeCount);
        return output;
    }

    private static List<String>  matchStreet(List<Address> addresses, Layout layout, String street) {
        List<String> output = new ArrayList<>();

        for(Address a : addresses){
            if(a.getStreet().equals(street) && layout.getCity().equals(a.getCity())
                    && layout.getZip().equals(a.getZip())){
                output.add(a.toString());
                routeCount++;
            }
            Collections.sort(output);
        }

        return output;
    }

    private static void writeResult(List<String> routeList, String outputFile) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (String s : routeList) {
            writer.write(s + "\n");
        }

        writer.close();
        System.out.println("Output successfully wrote to file.");
    }
}
