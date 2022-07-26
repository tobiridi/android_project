package be.jadoulle.examproject.pojo;

import java.io.Serializable;

public class User implements Serializable {
    //ATTRIBUTES
    private int id;
    private String username;
    private String email;
    private String password;
    private String postal_address;
    private int street_number;
    private String postal_code;
    private String city;

    public int getId() {
        return id;
    }

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

    public String getPostal_address() {
        return postal_address;
    }
    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public int getStreet_number() {
        return street_number;
    }
    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public String getPostal_code() {
        return postal_code;
    }
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    //CONSTRUCTOR
    public User(int id, String username, String email, String password, String postal_address,
                int street_number, String postal_code, String city) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.postal_address = postal_address;
        this.street_number = street_number;
        this.postal_code = postal_code;
        this.city = city;
    }

    public User (int id) {
        this.id = id;
    }

    //METHODS
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", postal_address='" + postal_address + '\'' +
                ", street_number='" + street_number + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
