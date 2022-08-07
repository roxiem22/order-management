package dao;
import model.Product;

import java.util.List;
/*Clasa ProductDAO extinde clasa AbstractDAO si foloseste metodele selectAll, findById, update, insert si delete din AbstractDAO.*/
public class ProductDAO extends AbstractDAO<Product>{
    public List<Product> selectAll() {
        return super.selectAll();
    }
    public Product findById(int id) {
        return super.findById(id);
    }
    public void update(Product p) {
         super.update(p);
    }
    @Override
    public void insert(Product p) {
         super.insert(p);
    }
    public void delete(int id) {
         super.delete(id);
    }
}
