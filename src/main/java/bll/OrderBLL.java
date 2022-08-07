package bll;

import bll.validators.QuantityOrderUpdateValidator;
import bll.validators.QuantityOrderValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/*Clasa OrderBLL apeleaza metodele de insert, delete si update din OrderDAO si le adauga o verificare suplimentara raportata la validatori. Pe langa operatiile principale am mai creat o metoda select care, folosind tehnica de reflexie ia datele din baza de date si le pune intr-un tabel pentru ca acesta sa poata fi afisat in interfata . Aici am si o lista de validatori in care am bagat un obiect validator pentru cantitatea din stoc de dupa update respectiv cea de dupa insert . Pentru metodele de insert si update se parcurge lista si se verifica validitatea, prin metoda validate care se afla in fiecare clasa de validator. De asemenea, in aceste metode se prind exceptiile aruncate de metoda validate si se afiseaza intr-un MessageDialog.*/
public class OrderBLL {
    private List<Validator<Order>> val;

    public OrderBLL() {
        val = new ArrayList<Validator<Order>>();
        val.add(new QuantityOrderUpdateValidator());
        val.add(new QuantityOrderValidator());
    }

    public void delete(int id) {
        OrderDAO o = new OrderDAO();
        Order order = o.findById(id);
        o.delete(order.getId());
        ProductDAO pd = new ProductDAO();
        Product product = pd.findById(order.getIdProduct());
        product.setQuantity(product.getQuantity() + order.getQuantity());
        pd.update(product);
    }

    public void insert(Order order) {
            try {
                val.get(1).validate(order);
            } catch(Exception e){
                showErr(e.getMessage());
                return;
            }
        ProductDAO pd = new ProductDAO();
        Product product = pd.findById(order.getIdProduct());
        product.setQuantity(product.getQuantity() - order.getQuantity());
        pd.update(product);
        OrderDAO o = new OrderDAO();
        o.insert(order);
    }

    public void update(Order order) {
            try {
                val.get(0).validate(order);
            } catch(Exception e){
                showErr(e.getMessage());
                return;
            }
        ProductDAO pd = new ProductDAO();
        Product product = pd.findById(order.getIdProduct());
        OrderDAO od = new OrderDAO();
        Order ord = od.findById(order.getId());
        product.setQuantity(ord.getQuantity() + product.getQuantity() - order.getQuantity()); 
        pd.update(product);
        OrderDAO o = new OrderDAO();
        o.update(order);
    }
    public List<Order> select(){
        OrderDAO o = new OrderDAO();
        return o.selectAll();
    }
    public void showErr(String s){
        JOptionPane.showMessageDialog(null, s,"Alert",JOptionPane.WARNING_MESSAGE);
    }
}
