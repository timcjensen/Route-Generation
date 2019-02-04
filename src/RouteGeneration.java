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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteGeneration {

    private static BufferedReader reader;
    private static int routeCount = 0;

    private static List<Address> addressList = new ArrayList<>();
    private static List<Layout> layoutList = new ArrayList<>();
    private static List<String> routeList = new ArrayList<>();

    private static String addressSource;
    private static String layoutSource;
    private static String outputFile = "Route.dat";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Route Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,600);

        JButton btn1 = new JButton("Choose Address file");
        JButton btn2 = new JButton("Choose Layout file");
        JButton btn3 = new JButton("Generate route");

        JTextArea text1 = new JTextArea();
        JTextArea text2 = new JTextArea();
        JTextArea text3 = new JTextArea();

        JScrollPane scroll1 = new JScrollPane(text1);
        scroll1.setPreferredSize(new Dimension(300,500));

        JScrollPane scroll2 = new JScrollPane(text2);
        scroll2.setPreferredSize(new Dimension(300,500));

        JScrollPane scroll3 = new JScrollPane(text3);
        scroll3.setPreferredSize(new Dimension(300,500));

        text1.setEditable(false);
        text2.setEditable(false);
        text3.setEditable(false);

        btn1.setPreferredSize(new Dimension(300, 50));
        btn2.setPreferredSize(new Dimension(300, 50));
        btn3.setPreferredSize(new Dimension(300, 50));

        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(btn1);
        frame.getContentPane().add(btn2);
        frame.getContentPane().add(btn3);
        frame.getContentPane().add(scroll1);
        frame.getContentPane().add(scroll2);
        frame.getContentPane().add(scroll3);

        btn1.addActionListener(e -> {
            addressSource = getFileName();
            if(addressSource != null) {
                try {
                    addressList = parseAddress(addressSource);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                for (Address a : addressList) {
                    text1.append(a.toString() + "\n");
                }
                text1.setCaretPosition(0);
            }
        });

        btn2.addActionListener(e -> {
            layoutSource = getFileName();
            if (layoutSource != null) {
                try {
                    layoutList = parseLayout(layoutSource);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                for (Layout b : layoutList) {
                    text2.append(b.toString() + "\n");
                }
                text2.setCaretPosition(0);
            }
        });

        btn3.addActionListener(e -> {
            if(addressList != null && layoutList != null) {
                routeList = determineRoute(addressList, layoutList);

                for (String r : routeList) {
                    text3.append(r + "\n");
                }

                text3.setCaretPosition(0);

                try {
                    writeResult(routeList, outputFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                System.out.println("Error! Address list or layout list not initialized");
            }
        }
        );

        frame.setVisible(true);

    }

    private static String getFileName() {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "dat", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile().getName();
        }

        return null;
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
