package bll.validators;

import dao.ProductDAO;
import model.Order;
import model.Product;
/*In clasa QuantityOrderValidator se verifica daca cantitatea ramasa in stoc dupa comanda e negativa. In cazul asta se va arunca o exceptie care e prinsa in OrderBLL si se afiseaza un mesaj.*/
public class QuantityOrderValidator implements Validator<Order>{
    @Override
    public void validate(Order order) throws Exception{
        Product prod;
        ProductDAO pd = new ProductDAO();
        if (order.getQuantity() < 0)
            throw new IllegalArgumentException("Cantitatea nu poate fi negativa!");
        prod = pd.findById(order.getIdProduct());
        if (prod.getQuantity()<order.getQuantity())
            throw new IllegalArgumentException("Cantitatea solicitata e prea mare!");
    }
}
