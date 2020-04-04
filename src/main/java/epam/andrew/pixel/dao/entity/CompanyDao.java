package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDao extends Dao implements EntityDao<Company> {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = ?";
    private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ?";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String INSERT_COMPANY = "INSERT INTO company VALUES (id,?,?)";
    private static final String ALL_COMPANY = "SELECT id, name, `language id` from company";

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
    public List<Company> getAll() throws DaoException {
        List<Company> company = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(ALL_COMPANY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                company.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all company list", e);
            throw new DaoException("cannot get all company list", e);
        }
        return company;
    }

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
}

