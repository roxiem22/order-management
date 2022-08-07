package bll.validators;

import model.Product;
/*In clasa QuantityProductValidator se verifica daca cantitatea produsului introdus in textfield e negativ. In cazul asta se va arunca o exceptie care e prinsa in ProductBLL.*/
public class QuantityProductValidator implements Validator<Product>{

    @Override
    public void validate(Product product) {
        if (product.getQuantity() < 0)
            throw new IllegalArgumentException("Cantitatea nu poate fi negativa!");
    }
}

