public class Address {
    private String city;
    private String zip;
    private String number;
    private String street;

    Address(String city, String zip, String number, String street) {
        this.city = city;
        this.zip = zip;
        this.number = number;
        this.street = street;
    }

    String getCity() {
        return city;
    }

    String getZip() {
        return zip;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String toString(){
        return number + " " + street + ", " + city + ", " + zip;
    }
}
