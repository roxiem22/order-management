package dao;
import model.Order;

import java.util.List;
/*Clasa OrderDAO extinde clasa AbstractDAO si foloseste metodele selectAll, findById, update, insert si delete din AbstractDAO.*/
public class OrderDAO extends AbstractDAO<Order>{
    public List<Order> selectAll() {
        return super.selectAll();
    }
    public Order findById(int id) {
        return super.findById(id);
    }
    public void update(Order O) {
         super.update(O);
    }
    @Override
    public void insert(Order O) {
         super.insert(O);
    }
    public void delete(int id) {
         super.delete(id);
    }
}
