package epam.andrew.gameShop.service;

import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.dao.DaoFactory;
import epam.andrew.gameShop.dao.imp.*;
import epam.andrew.gameShop.entity.GameInTransaction;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.User;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String CANNOT_GET_USER_BY_ID = "Cannot get user by id";
    private static final String CANNOT_CHECK_EMAIL = "Cannot check email";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String KZT = "KZT";
    private static final String CANNOT_GET_USER = "Cannot get user";
    private static final String CANNOT_REGISTER_USER = "Cannot register user";
    private static final String USER_UPDATED = "User updated";
    private static final String CANNOT_UPDATE_USER = "Cannot update user";
    private static final String CANNOT_SET_CASH = "Cannot set user cash";
    private static final String CASH_UPDATED_SUCCESSFULLY = "Cash updated successfully";
    private static final String CANNOT_UPDATE_CASH = "Couldn't update cash";
    private static final String CANNOT_GET_USER_TRANSACTIONS = "Cannot get user transactions";
    private static final String USER_ID = "user_id";
    private static final String TRANSACTION_ID = "transac   tion_id";

    public User registerUser(User user) throws ServiceException {
        User registeredUser;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                registeredUser = userDao.create(user);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_REGISTER_USER, e);
                throw new ServiceException(CANNOT_REGISTER_USER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_REGISTER_USER, e);
            throw new ServiceException(CANNOT_REGISTER_USER, e);
        }
        return registeredUser;
    }

    public User logIn(String email, String password) throws ServiceException {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, email);
        params.put(PASSWORD, password);
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                List<User> users = userDao.getByParams(params);
                if (users.isEmpty() && users.get(0).isDeleted()) {
                    return getUserById(users.get(0).getId());
                }
            } catch (DaoException e) {
                LOG.error(CANNOT_GET_USER, e);
                throw new ServiceException(CANNOT_GET_USER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_USER, e);
            throw new ServiceException(CANNOT_GET_USER, e);
        }
        return null;
    }

    public boolean checkEmail(String email) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                List<User> usersWithEnteredEmail = userDao.getByParams(Collections.singletonMap(EMAIL, email));
                return usersWithEnteredEmail.isEmpty();
            } catch (DaoException | ConnectionPoolException e) {
                LOG.error(CANNOT_CHECK_EMAIL, e);
                throw new ServiceException(CANNOT_CHECK_EMAIL, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_CHECK_EMAIL, e);
            throw new ServiceException(CANNOT_CHECK_EMAIL, e);
        }
    }

    public User getUserById(int id) throws ServiceException {
        User user;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                user = userDao.findById(id);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_USER_BY_ID, e);
                throw new ServiceException(CANNOT_GET_USER_BY_ID, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_USER_BY_ID, e);
            throw new ServiceException(CANNOT_GET_USER_BY_ID, e);
        }
        return user;
    }

    public void updateUser(User user) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDAO = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                userDAO.update(user);
                daoFactory.commitTransaction();
                LOG.debug(USER_UPDATED);
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_USER, e);
                throw new ServiceException(CANNOT_UPDATE_USER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_USER, e);
            throw new ServiceException(CANNOT_UPDATE_USER, e);
        }
    }

    public User setUserCash(int id, String cash) throws ServiceException {
        User user;
        try (DaoFactory daoFactory = new DaoFactory()) {
            user = updateCash(id, cash, daoFactory);
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_SET_CASH, e);
            throw new ServiceException(CANNOT_SET_CASH, e);
        }
        return user;
    }

    private User updateCash(int id, String cash, DaoFactory daoFactory) throws ServiceException, DaoException {
        User user;
        Money totalCash;

        try {
            daoFactory.startTransaction();
            UserDao userDao = daoFactory.getDao(UserDao.class);
            user = userDao.findById(id);
            totalCash = Money.parse(KZT + cash);
            user.setCash(totalCash);
            userDao.update(user);
            daoFactory.commitTransaction();
            LOG.debug(CASH_UPDATED_SUCCESSFULLY);
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_CASH, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(CANNOT_UPDATE_CASH, e);
        }
        return user;
    }

    public List<Transaction> getUserTransactions(int id) throws ServiceException {
        List<Transaction> transactions;
        List<GameInTransaction> gamesInTransaction;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                TransactionStatusDao transactionStatusDao = daoFactory.getDao(TransactionStatusDao.class);
                GameInTransactionDao gameInTransactionDao = daoFactory.getDao(GameInTransactionDao.class);
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                transactions = transactionDao.getByParams(Collections.singletonMap(USER_ID, String.valueOf(id)));
                for (Transaction transaction : transactions) {
                    transaction.setStatus(transactionStatusDao.findById(transaction.getStatus().getId()));
                    gamesInTransaction = gameInTransactionDao.getByParams(Collections.singletonMap(TRANSACTION_ID, String
                            .valueOf(transaction.getId())));
                    for (GameInTransaction gameInTransaction : gamesInTransaction) {
                        gameInTransaction.setGame(gameDao.findById(gameInTransaction.getGame().getId()));
                    }
                    transaction.setGameInTransactionList(gamesInTransaction);
                }
            } catch (DaoException e) {
                LOG.error(CANNOT_GET_USER_TRANSACTIONS, e);
                throw new ServiceException(CANNOT_GET_USER_TRANSACTIONS, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_USER_TRANSACTIONS, e);
            throw new ServiceException(CANNOT_GET_USER_TRANSACTIONS, e);
        }
        return transactions;
    }
}