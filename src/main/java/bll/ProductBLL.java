package bll;

import bll.validators.PriceValidator;
import bll.validators.QuantityProductValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import java.util.ArrayList;
import java.util.List;
/*Clasa ProductBLL apeleaza metodele de insert, delete si update din ProductDAO si le adauga o verificare suplimentara raportata la validatori. Pe langa operatiile principale am mai creat o metoda select care, folosind tehnica de reflexie ia datele din baza de date si le pune intr-un tabel pentru ca acesta sa poata fi afisat in interfata . Aici am si o lista de validatori in care am bagat un obiect validator pret si cantitate . Pentru metodele de insert si update se parcurge lista si se verifica validitatea , prin metoda validate care se afla in fiecare clasa de validator. De asemenea, in aceste metode se prind exceptiile aruncate de metoda validate.*/
public class ProductBLL {
    private List<Validator<Product>> val;
    public ProductBLL() {
        val = new ArrayList<Validator<Product>>();
        val.add(new QuantityProductValidator());
        val.add(new PriceValidator());
    }

    public void delete(int id) {
        ProductDAO p = new ProductDAO();
        p.delete(id);
    }

    public void insert(Product product) {
        for(Validator v: val){
            try {
                v.validate(product);
            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
        ProductDAO p = new ProductDAO();
        p.insert(product);
    }

    public void update(Product product) {
        for(Validator v: val){
            try {
                v.validate(product);
            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
        ProductDAO p = new ProductDAO();
        p.update(product);
    }
    public List<Product> select(){
        ProductDAO p = new ProductDAO();
        return p.selectAll();
    }

}
