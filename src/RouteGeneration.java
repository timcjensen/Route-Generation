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
        - Fill in test data (Address, Layout)
            - Possibly create random data
        - Parsing in data
            - Address, Layout
        - Sorting addresses based on adjacency
            - Grouped by city and zip code
        - Writing result to file
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteGeneration {

    private static BufferedReader reader;

    public static void main(String[] args) throws IOException {
        String addressSource = "Address.dat";
        String layoutSource = "Layout.dat";
        String outputFile = "Route.dat";

        List<Address> addressList = parseAddress(addressSource);
        List<Layout> layoutList = parseLayout(layoutSource);
        List<String> routeList = determineRoute(addressList, layoutList);
        writeResult(routeList, outputFile);
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

            System.out.println(city);
            System.out.println(zip);

            reader.readLine();

            System.out.println("vertical streets");
            while((line = reader.readLine()) != null){
                if(line.equals(""))
                    break;
                verticalStreets.add(line);
                System.out.println(line);
            }

            reader.readLine();
            reader.readLine();

            System.out.println("horizontal streets");
            while((line = reader.readLine()) != null){
                if(line.equals(""))
                    break;
                horizontalStreets.add(line);
                System.out.println(line);
            }

            layouts.add(new Layout(city, zip, verticalStreets, horizontalStreets));
        }

        return layouts;
    }

    //TODO
    private static List<String> determineRoute(List<Address> addresses, List<Layout> layouts){
        List<String> output = new ArrayList<>();
        for(Layout layout : layouts){
            for(Address address : addresses){
                if(layout.getCity().equals(address.getCity())){
                    if(layout.getZip().equals(address.getZip())){

                        List<String> area = new ArrayList<>();
                        for(String street : layout.getVerticalStreets()){
                            if(street.equals(address.getStreet())){
                                area.add(address.toString());
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Route successfully generated.");
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
