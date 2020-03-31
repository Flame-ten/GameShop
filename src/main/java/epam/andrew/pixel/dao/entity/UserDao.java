package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends BaseDao<User> {
    private static final String USER = "User";
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET name = ? , surname = ? ," +
            "password= ? , email = ? , login = ? , gender = ? , country = ?, position = ? WHERE id = ?";
    private static final String UPDATE_USERS_AVATAR = "UPDATE user SET avatar_id= ?  WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user VALUES (id,?,?,?,?,?,?,?,?,NULL)";
    private static final String FIND_ALL = "SELECT id * FROM user";
    private static final String GET_BETS_CUSTOMER = "SELECT id, firstName, lastName, password, email FROM customers JOIN customers_bets ON customers.id=customers_bets.customer_id WHERE customers_bets.bets_id=?";
    private static final String USERS_COUNT = "SELECT count(*) FROM bets.customers where active =1";

    @Override
    public User findById(int id) throws DaoException {
        User user = new User();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = getObjectFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return user;
    }

    @Override
    public User getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setPassword(resultSet.getString(4));
            user.setLogin(resultSet.getString(5));
            user.setEmail(resultSet.getString(6));
            user.setPhone(resultSet.getInt(7));
            user.setGender(resultSet.getString(8));
            user.setCountry(resultSet.getString(9));
            user.setPosition((resultSet.getString(10)));
        } catch (SQLException e) {
            throw new DaoException("Cannot create user from resultSet", e);
        }
        return user;
    }

    @Override
    public User create(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_USER,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getPhone());
            statement.setString(7, user.getGender());
            statement.setString(8, user.getCountry());
            statement.setString(9, user.getPosition());
        } catch (SQLException e) {
            throw new DaoException("Cannot create user", e);
        }
        return user;
    }

    @Override
    public void update(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getPhone());
            statement.setString(7, user.getGender());
            statement.setString(8, user.getCountry());
            statement.setString(9, user.getPosition());
            statement.setInt(10, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update user", e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete user", e);
        }
    }


    @Override
    protected String getTableName() {
        return USER;
    }
}
