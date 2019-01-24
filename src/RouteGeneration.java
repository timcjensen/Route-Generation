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
    private BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        String addressSource = "Address.dat";
        String layoutSource = "Layout.dat";

        List<Address> addressList = parseAddress(addressSource);
        List<Layout> layoutList = parseLayout(layoutSource);

        String output = determineRoute(addressList, layoutList);

        writeResult(output);
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

    //TODO: layout is trickier - consider difference of cities, zips, vertical/horizontal
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
    private static String determineRoute(List<Address> addresses, List<Layout> layouts){
        return "";
    }

    //TODO
    private static void writeResult(String output){

    }
}
