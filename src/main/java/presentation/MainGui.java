package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/*MainGui e frame-ul prinicpal al proiectului si contine 3 butoane, care deschid frame-uri separate, pentru prelucrarea tabelelor din baza de date (client, order, product).*/
public class MainGui extends JFrame {

    private JButton btn;
    private JButton btn1;
    private JButton btn2;
    JLabel label = new JLabel("Shop");
    JPanel panel = new JPanel();

    public MainGui() {
        final MainGui v = this;
        panel.setBackground(new Color(220,205,255));
        panel.setBounds(260,0,60,40);
        label.setFont(new Font("Serif", Font.ITALIC, 25));
        btn = new JButton("Client");
        btn1 = new JButton("Produs");
        btn2 = new JButton("Comanda");
        btn.setBounds(200, 50, 200, 60);
        btn1.setBounds(200, 150, 200, 60);
        btn2.setBounds(200, 250, 200, 60);
        btn.setFocusable(false);
        btn1.setFocusable(false);
        btn2.setFocusable(false);

        this.setTitle("Management-ul comenzilor");
        this.getContentPane().setBackground(new Color(220,205,255));
        this.setBounds(100, 100, 600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setLayout(null);
        this.add(panel);
        panel.add(label);
        this.add(btn);
        this.add(btn1);
        this.add(btn2);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientGui client = new ClientGui(v);
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductGui product = new ProductGui(v);
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderGui comanda = new OrderGui(v);
            }
        });
    }
    public JFrame getThis() {
        return this;
    }
}