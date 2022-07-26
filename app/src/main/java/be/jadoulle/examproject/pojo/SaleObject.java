package be.jadoulle.examproject.pojo;

import java.io.Serializable;

public class SaleObject implements Serializable {
    //ATTRIBUTES
    private int id;
    private String name;
    private String type;
    private String description;
    private Double price;
    private User user;
    private double longitude;
    private double latitude;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //CONSTRUCTOR
    public SaleObject(int id, String name, String type, String description, Double price,
                      User user, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.user = user;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //METHODS
    @Override
    public String toString() {
        return "SaleObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", user=" + user +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
