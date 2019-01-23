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
        - Parsing in data
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

        String[] addressA = parseAddress(addressSource);
        String[] layoutA = parseLayout(layoutSource);

        String output = determineRoute(addressA, layoutA);

        writeResult(output);
    }

    //TODO: addresses can be parsed in a straightforward way
    private static String[] parseAddress(String source) throws IOException {
        FileReader file = new FileReader(source);
        reader = new BufferedReader(file);

        List<String> lines = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null){
            lines.add(line);
        }
        reader.close();
        return lines.toArray(new String[0]);
    }

    //TODO: layout is trickier - consider difference of cities, zips, vertical/horizontal
    private String[] parseLayout(String source){
        String[] result;
        return result;
    }

    //TODO
    private static String determineRoute(String[] addresses, String[] layout){
        return "";
    }

    //TODO
    private static void writeResult(String output){

    }
}
