package bll.validators;

import model.Product;
/*In clasa PriceValidator se verifica daca pretul produsului introdus in textfield e negativ. In cazul asta se va arunca o exceptie care e prinsa in ProductBLL.*/
public class PriceValidator implements Validator<Product> {
    @Override
    public void validate(Product product) {
            if (product.getPrice() < 0)
                throw new IllegalArgumentException("Pretul nu poate fi negativa!");

    }
}
