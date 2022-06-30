package be.jadoulle.examproject.pojo;

import java.io.Serializable;

public class User implements Serializable {
    //ATTRIBUTES
    private int id;
    private String username;
    private String email;
    private String password;
    private String postalAddress;
    private String street_number;
    private String postalCode;
    private String city;

    public int getId() {
        return id;
    }
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostalAddress() {
        return postalAddress;
    }
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getStreet_number() {
        return street_number;
    }
    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    //CONSTRUCTOR
    public User(){

    }

    public User(int id, String username, String email, String password, String postalAddress,
                String street_number,String postalCode, String city) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.postalAddress = postalAddress;
        this.street_number = street_number;
        this.postalCode = postalCode;
        this.city = city;
    }

    public User(String username, String email, String password, String postalAddress,
                String street_number,String postalCode, String city) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.postalAddress = postalAddress;
        this.street_number = street_number;
        this.postalCode = postalCode;
        this.city = city;
    }

    //METHODS
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", street_number='" + street_number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
