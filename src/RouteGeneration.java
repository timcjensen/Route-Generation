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
import java.util.List;

public class RouteGeneration {

    private static BufferedReader reader;

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

    //TODO
    private static List<String> determineRoute(List<Address> addresses, List<Layout> layouts){
        List<String> output = new ArrayList<>();

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
