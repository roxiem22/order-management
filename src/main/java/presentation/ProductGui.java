package presentation;

import bll.ProductBLL;
import dao.ProductDAO;
import model.Product;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.BoxLayout.Y_AXIS;
/*In ProductGui se pot insera, sterge si updata informatii despre un produs, cat si vizualiza ce avem in tabelul product al bazei de date apasand butonul select .*/
public class ProductGui extends JFrame{

    private MainGui view;
    private ProductBLL prodBLL=new ProductBLL();
    private ProductDAO prod=new ProductDAO();
    private JTable tabel;
    JPanel pan1=new JPanel();
    JPanel pan2=new JPanel();
    JPanel pan3=new JPanel();
    JPanel pan5=new JPanel();
    JLabel t = new JLabel("Product");
    JTextField txt1=new JTextField(11);
    JTextField txt2 = new JTextField(11);
    JTextField txt3 = new JTextField(11);
    JTextField txt4 = new JTextField(11);
    JButton btn1 = new JButton("Select");
    JButton btn2 = new JButton("Insert");
    JButton btn3 = new JButton("Update");
    JButton btn4 = new JButton("Delete");
    JLabel lab1=new JLabel("Nume: ");
    JLabel lab2=new JLabel("Id: ");
    JLabel lab3=new JLabel("Cantitate: ");
    JLabel lab4=new JLabel("Pret: ");

    public ProductGui(MainGui v)
    {
        view = v;
        this.setBounds(100,100,700,400);
        this.getContentPane().setBackground(new Color(220,205,255));
        pan1.setBackground(new Color(220,205,255));
        pan2.setBackground(new Color(220,205,255));
        pan3.setBackground(new Color(220,205,255));

        pan1.add(btn1);
        pan1.add(btn2);
        pan1.add(btn3);
        pan1.add(btn4);
        pan2.add(lab2);
        pan2.add(txt1);
        pan2.add(lab1);
        pan2.add(txt2);
        pan2.add(lab4);
        pan2.add(txt3);
        pan2.add(lab3);
        pan2.add(txt4);
        pan3.add(t);
        pan5.add(pan3);
        pan5.add(pan1);
        pan5.add(pan2);

        btn1.setFocusable(false);
        btn2.setFocusable(false);
        btn3.setFocusable(false);
        btn4.setFocusable(false);

        pan5.setLayout(new BoxLayout(pan5, Y_AXIS));
        this.add(pan5);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        JPanel pan6=new JPanel();
        JFrame frs =new JFrame();
        frs.setBounds(100,100,700,500);
        frs.setLocationRelativeTo(null);

        btn1.addActionListener((e)->
        { List<Product> produse= prodBLL.select();
            String[] strHeader = getHeaders(new Product());
            tabel = new JTable();
            tabel.setModel(new DefaultTableModel(new Object[][]{}, strHeader));
            tabel.setBorder(new LineBorder(new Color(220,205,255), 2));
            tabel.setBounds(60, 400, 650, 100);
            DefaultTableModel tableM = (DefaultTableModel) tabel.getModel();
            tableCreate(produse);
            JScrollPane s = new JScrollPane(tabel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            pan6.removeAll();
            pan6.add(s);
            tabel.repaint();
            s.repaint();
            pan6.repaint();
            pan6.revalidate();
            frs.add(pan6);
            frs.setVisible(true);
        });

        btn4.addActionListener(e -> {
            prodBLL.delete(Integer.parseInt(txt1.getText()));
        });
        btn2.addActionListener(e -> {
            Product prd = new Product(Integer.parseInt(txt1.getText()), txt2.getText(), Double.parseDouble(txt3.getText()), Integer.parseInt(txt4.getText()));
            prodBLL.insert(prd);
        });
        btn3.addActionListener(e->
        {
            Product prd = new Product(Integer.parseInt(txt1.getText()), txt2.getText(), Double.parseDouble(txt3.getText()), Integer.parseInt(txt4.getText()));
            prodBLL.update(prd);
        });
    }
    public ArrayList list(ProductDAO prod)
    {
        List<Product> product= prod.selectAll();
        ArrayList list=new ArrayList<>();
        for(Product p: product)
        {
            int id=p.getId();
            String name=p.getName();
            int quan=p.getQuantity();
            double pr=p.getPrice();
            list.add(id);
            list.add(name);
            list.add(pr);
            list.add(quan);
        }
        return list;
    }
    public String[] getHeaders(Object object){
        String []str = new String[4];
        int i = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String strName = field.getName();
                str[i++] = strName;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public void tableCreate(List<Product> objects) {
        int i = 0;
        DefaultTableModel tableM = (DefaultTableModel) tabel.getModel();
        for (Object object : objects) {
            i=0;
            Object[] objTable = new Object[4];
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value;
                try {
                    value = field.get(object);
                    objTable[i++] = value;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            tableM.addRow(objTable);
        }
    }
}