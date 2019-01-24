import java.util.ArrayList;
import java.util.List;

public class Layout {
    String city;
    String zip;
    List<String> verticalStreets;
    List<String> horizontalStreets;

    public Layout(String city, String zip, List<String> verticalStreets, List<String> horizontalStreets) {
        this.city = city;
        this.zip = zip;
        this.verticalStreets = verticalStreets;
        this.horizontalStreets = horizontalStreets;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public List<String> getVerticalStreets() {
        return verticalStreets;
    }

    public List<String> getHorizontalStreets() {
        return horizontalStreets;
    }
}
