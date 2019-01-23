public class Address {
    String city;
    String zip;
    String address;

    public Address(String city, String zip, String address) {
        this.city = city;
        this.zip = zip;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String toString(){
        return address + ", " + city + ", " + zip;
    }
}
