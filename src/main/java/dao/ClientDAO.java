package dao;
import model.Client;

import java.util.List;
/*Clasa ClientDAO extinde clasa AbstractDAO si foloseste metodele selectAll, findById, update, insert si delete din AbstractDAO.*/
public class ClientDAO extends AbstractDAO<Client>{

    public List<Client> selectAll() {
        return super.selectAll();
    }
    public Client findById(int id) {
        return super.findById(id);
    }
    public void update(Client c) {
      super.update(c);
    }
    @Override
    public void insert(Client c) {
       super.insert(c);
    }
    public void delete(int id) {
       super.delete(id);
    }
}
