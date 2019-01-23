public class Layout {
    String city;
    String zip;
    String[] verticalStreets;
    String[] horizontalStreets;

    public Layout(String city, String zip, String[] verticalStreets, String[] horizontalStreets) {
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

    public String[] getVerticalStreets() {
        return verticalStreets;
    }

    public String[] getHorizontalStreets() {
        return horizontalStreets;
    }
}
