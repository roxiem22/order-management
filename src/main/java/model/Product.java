package model;
/*Clasa Product are ca atribute id (care reprezinta id -ul produsului) , name(numele produsului) , price (pretul produsului) si quantity (care reprezinta cantitatea disponibila in shop a produsului). De asemenea, s-au generat getter-ele si setter-ele necesare.*/
public class Product {
    private int id;
    private  String name;
    private double price;
    private int quantity;

    public Product (){};

    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getId() {
        return id;
    }

    public void setQuantity(int i) {
        this.quantity = i;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
