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
import java.util.Arrays;
import java.util.List;

public class RouteGeneration {

    private static BufferedReader reader;
    private BufferedWriter writer;
    private List<Layout> layouts = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String addressSource = "Address.dat";
        String layoutSource = "Layout.dat";

        List<Address> addressList = parseAddress(addressSource);
//        String[] layoutA = parseLayout(layoutSource);
//
//        String output = determineRoute(addressA, layoutA);
//
//        writeResult(output);
    }

    //addresses can be parsed in a straightforward way
    private static List<Address> parseAddress(String source) throws IOException {
        FileReader file = new FileReader(source);
        reader = new BufferedReader(file);
        List<Address> addresses = new ArrayList<>();
        String line;

        while((line = reader.readLine()) != null){
            String address = line.substring(0, line.indexOf(","));
            String city = line.substring(address.length() + 2, line.indexOf(",", address.length() + 1));
            String zip = line.substring(address.length() + city.length() + 4);

            addresses.add(new Address(city, zip, address));
        }

        reader.close();
        return addresses;
    }

//    //TODO: layout is trickier - consider difference of cities, zips, vertical/horizontal
//    private String[] parseLayout(String source) throws IOException{
//    }
//
//    //TODO
//    private static String determineRoute(String[] addresses, String[] layout){
//        return "";
//    }
//
//    //TODO
//    private static void writeResult(String output){
//
//    }
}
