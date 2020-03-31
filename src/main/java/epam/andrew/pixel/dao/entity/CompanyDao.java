package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CompanyDao extends BaseDao<Company> {
    private static final String COMPANY = "Company";
    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = ?";
    private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ?";
    private static final String UPDATE_USERS_AVATAR = "UPDATE company SET avatar_id= ?  WHERE id = ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String INSERT_COMPANY = "INSERT INTO company VALUES (id,?,?,?,?,?,?,?,?,NULL)";

    @Override
    public Company findById(int id) throws DaoException {
        Company company = new Company();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                company = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return company;
    }

    @Override
    public Company getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Company company = new Company();
        try {
            company.setId(resultSet.getInt(1));
            company.setName(resultSet.getString(2));
        } catch (SQLException e) {
            throw new DaoException("Cannot create company from resultSet", e);
        }
        return company;
    }

    @Override
    public Company create(Company company) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_COMPANY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, company.getName());
        } catch (SQLException e) {
            throw new DaoException("Cannot create company", e);
        }
        return company;
    }

    @Override
    public void update(Company company) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_COMPANY)) {
            statement.setString(1, company.getName());
        } catch (SQLException e) {
            throw new DaoException("Cannot update company", e);
        }
    }

    @Override
    public void delete(Company company) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_COMPANY)) {
            statement.setInt(1, company.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete company", e);
        }
    }

    @Override
    protected String getTableName() {
        return COMPANY;
    }
}

