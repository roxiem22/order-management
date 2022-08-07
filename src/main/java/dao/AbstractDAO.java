package dao;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
/*Clasa AbstractDAO implementeaza metode generale pentru clasele din model si corespondentele acestora prin intermediul tehnicii de reflexie. Se folosește tehnica de reflexie in Java pentru a nu fi nevoie sa scriem mai multe metode similare, particularizate pentru fiecare clasa ce modeleaza obiectele aplicației. In acest sens, a fost definit un tip generic asociat clasei AbstractDAO. Fiecare operatie pe tabel este alcatuita din doua metode, una care selecteaza folosind SQL datele din tabel si una care gaseste in ce tabel trebuie introduse, sterse sau updatate. In acest pachet se folosește tehnica reflexiei, asa incat introducerea, stergerea si updatarea in baza de date se fac folosind aceleași metode, indiferent de tipul obiectului instanța al unei clase din pachetul model. Operațiile care sunt specifice fiecarei clase din model sunt implementate prin metode in afara clasei abstracte AbstractDAO.*/
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    public void insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createUpdateQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

    }

    public List<T> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:selectAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectByIdQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String createInsertQuery(T t) {
        boolean ok = false;
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append("shop.");
        sb.append(type.getSimpleName());
        sb.append("(");
        for(Field f: t.getClass().getDeclaredFields()){
            f.setAccessible(true);

            sb.append(f.getName() + ", ");

        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(")");
        sb.append("VALUES('");
        for(Field f: t.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value;

            try {
                value = f.get(t);
                sb.append(value + "', '");

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.delete(sb.length() - 3, sb.length());
        sb.append(")");
        return sb.toString();
    }

    private String createUpdateQuery(T t) {
        boolean ok = false;
        int id = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append("shop.");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(Field f: t.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value;

            try {
                value = f.get(t);
                sb.append(f.getName() + " = '"+value + "', ");

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" WHERE id=");

        Field f = t.getClass().getDeclaredFields()[0];
        f.setAccessible(true);
        try {
            sb.append(f.get(t));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private String createDeleteQuery(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append(" FROM shop.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + id + " =?");

        return sb.toString();
    }

    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM shop.");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    private String createSelectByIdQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT *");
        sb.append(" FROM shop.");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");

        return sb.toString();
    }
}
