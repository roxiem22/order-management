package model;
/*Clasa Order are ca atribute id (care reprezinta id -ul comenzii) , idClient (care reprezinta id -ul clientului care comanda), idProduct (care reprezinta id -ul produsului comandat) si quantity (care reprezinta cantitatea de produs solicitata de client). De asemenea, s-au generat getter-ele si setter-ele necesare.*/
public class Order {
    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;

    public Order (){};

    public Order(int id, int idClient, int idProduct, int quantity) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }
}
