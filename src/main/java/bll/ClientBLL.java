package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
/*Clasa ClientBLL apeleaza metodele de insert, delete si update din ClientDAO si le adauga o verificare suplimentara raportata la validatori. Pe langa operatiile principale am mai creat o metoda select care, folosind tehnica de reflexie ia datele din baza de date si le pune intr-un tabel pentru ca acesta sa poata fi afisat in interfata . Aici am si o lista de validatori in care am bagat un obiect validator pentru email . Pentru metodele de insert si update se parcurge lista si se verifica validitatea mail -ului prin metoda validate care se afla in fiecare clasa de validator. De asemenea, in aceste metode se prind exceptiile aruncate de metoda validate.*/
public class ClientBLL {
    private List<Validator<Client>> val;
    public ClientBLL(){
        val = new ArrayList<Validator<Client>>();
        val.add(new EmailValidator());
    }
    public void delete(int id) {
        ClientDAO c = new ClientDAO();
        c.delete(id);
    }

    public void insert(Client client){
        for(Validator v: val){
            try {
                v.validate(client);
            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
        ClientDAO c = new ClientDAO();
        c.insert(client);
    }

    public void update(Client client) {
        for(Validator v: val){
            try {
                v.validate(client);
            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }
        ClientDAO c = new ClientDAO();
        c.update(client);
    }

    public List<Client> select(){
        ClientDAO c = new ClientDAO();
        return c.selectAll();
    }
}
