import java.util.Arrays;
import java.util.List;

public class Layout {
    private String city;
    private String zip;
    private List<String> verticalStreets;
    private List<String> horizontalStreets;

    Layout(String city, String zip, List<String> verticalStreets, List<String> horizontalStreets) {
        this.city = city;
        this.zip = zip;
        this.verticalStreets = verticalStreets;
        this.horizontalStreets = horizontalStreets;
    }

    String getCity() {
        return city;
    }

    String getZip() {
        return zip;
    }

    public List<String> getVerticalStreets() {
        return verticalStreets;
    }

    public List<String> getHorizontalStreets() {
        return horizontalStreets;
    }

    public String toString() {
        return "\n" +
                city +
                "\n" +
                zip +
                "\n" +
                "Vertical streets: " +
                Arrays.toString(verticalStreets.toArray()) +
                "\n" +
                "Horizontal streets: " +
                Arrays.toString(horizontalStreets.toArray());
    }
}
