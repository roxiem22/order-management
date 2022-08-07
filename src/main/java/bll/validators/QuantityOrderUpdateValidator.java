package bll.validators;

import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;
/*In clasa QuantityOrderUpdateValidator se verifica daca cantitatea ramasa in stoc inainte de insert e mai mica decat cantitatea comenzii updatate. In caz contrar se va arunca o exceptie care e prinsa in OrderBLL si se afiseaza un mesaj.*/
public class QuantityOrderUpdateValidator implements Validator<Order>{
    @Override
    public void validate(Order order) throws Exception{
        Product prod;
        ProductDAO pd = new ProductDAO();
        Order ord;
        OrderDAO or = new OrderDAO();
        if (order.getQuantity() < 0)
            throw new IllegalArgumentException("Cantitatea nu poate fi negativa!");
        prod = pd.findById(order.getIdProduct());
        ord = or.findById(order.getId());
        if (prod.getQuantity() < order.getQuantity()-ord.getQuantity())
            throw new IllegalArgumentException("Cantitatea solicitata e prea mare!");
    }
}
