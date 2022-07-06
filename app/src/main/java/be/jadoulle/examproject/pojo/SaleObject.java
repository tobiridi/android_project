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

    //CONSTRUCTOR
    public SaleObject(int id, String name, String type, String description, Double price, User user) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.user = user;
    }
    
    public SaleObject() {

    }
}
