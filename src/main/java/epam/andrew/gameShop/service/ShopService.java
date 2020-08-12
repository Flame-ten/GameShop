package epam.andrew.gameShop.service;

import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.dao.DaoFactory;
import epam.andrew.gameShop.dao.imp.*;
import epam.andrew.gameShop.entity.*;
import epam.andrew.gameShop.enums.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShopService {
    private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);
    private static final String CANNOT_GET_TRANSACTIONS_ON_PAGE = "Cannot get transactions on page";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String CANNOT_GET_ALL_USERS = "Cannot get all users";
    private static final String CANNOT_GET_GAMES_ON_PAGE = "Cannot get games on page";
    private static final String CANNOT_DELETE_USER = "Cannot delete user";
    private static final String CANNOT_DELETE_GAME = "Cannot delete game";
    private static final String CANNOT_DELETE_TRANSACTION = "Cannot delete transaction";
    private static final String CANNOT_UPDATE_TRANSACTION_STATUS = "Cannot update transaction status";
    private static final String CANNOT_GET_TRANSACTION_STATUSES = "Cannot get transaction statuses";
    private static final String CANNOT_GET_TRANSACTIONS_COUNT = "Cannot get transactions count";
    private static final String CANNOT_GET_USERS_COUNT = "Cannot get users count";
    private static final String CANNOT_BUY_GAME = "Cannot buy game";
    private static final String GAME_ID = "game_id";
    private static final String CANNOT_GET_GAMES_COUNT = "Cannot get games count";
    private static final String CANNOT_GET_TRANSACTION = "Cannot get transaction";
    private static final String CANNOT_GET_ALL_GAMES = "Cannot get all games";
    private static final String CANNOT_GET_ALL_GENRES = "Cannot get all genres";

    public Transaction getTransaction(int id) throws ServiceException {
        Transaction transaction;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                UserDao userDao = daoFactory.getDao(UserDao.class);
                GameInTransactionDao gameInTransactionDao = daoFactory.getDao(GameInTransactionDao.class);
                TransactionStatusDao orderStatusDao = daoFactory.getDao(TransactionStatusDao.class);
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                transaction = transactionDao.findById(id);
                transaction.setUser(userDao.findById(transaction.getUser().getId()));
                transaction.setStatus(orderStatusDao.findById(transaction.getStatus().getId()));
                List<GameInTransaction> gameInTransactions = gameInTransactionDao.getByParams(Collections.
                        singletonMap(TRANSACTION_ID, String.valueOf(transaction.getId())));
                for (GameInTransaction game : gameInTransactions) {
                    game.setGame(gameDao.findById(game.getGame().getId()));
                }
                transaction.setGameInTransactionList(gameInTransactions);
                daoFactory.commitTransaction();
            } catch (ConnectionPoolException | DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_TRANSACTION, e);
                throw new ServiceException(CANNOT_GET_TRANSACTION, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_TRANSACTION, e);
            throw new ServiceException(CANNOT_GET_TRANSACTION, e);
        }
        return transaction;
    }

    public int getGamesCount() throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                return gameDao.getNotDeletedCount();
            } catch (DaoException e) {
                LOG.error(CANNOT_GET_GAMES_COUNT, e);
                throw new ServiceException(CANNOT_GET_GAMES_COUNT, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_GAMES_COUNT, e);
            throw new ServiceException(CANNOT_GET_GAMES_COUNT, e);
        }
    }

    public List<Genre> getAllGenres() throws ServiceException {
        List<Genre> genres;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GenreDao genreDao = daoFactory.getDao(GenreDao.class);
                genres = genreDao.getAll();
                genres = genres.stream().filter(genre ->
                        !genre.isDeleted()).collect(Collectors.toList());
            } catch (ConnectionPoolException | DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_ALL_GENRES, e);
                throw new ServiceException(CANNOT_GET_ALL_GENRES, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.info(CANNOT_GET_ALL_GENRES, e);
            throw new ServiceException(CANNOT_GET_ALL_GENRES, e);
        }
        return genres;
    }

    public User buyCart(Transaction transaction) throws ServiceException {
        User user;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                daoFactory.startTransaction();
                user = buyTransaction(daoFactory, transaction);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_BUY_GAME, e);
                throw new ServiceException(CANNOT_BUY_GAME, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_BUY_GAME, e);
            throw new ServiceException(CANNOT_BUY_GAME, e);
        }
        return user;
    }

    private User buyTransaction(DaoFactory daoFactory, Transaction transaction) throws ServiceException, ConnectionPoolException, DaoException {
        User user;
        try {
            daoFactory.startTransaction();
            TransactionStatusDao transactionStatusDao = daoFactory.getDao(TransactionStatusDao.class);
            UserDao userDao = daoFactory.getDao(UserDao.class);
            GameInTransactionDao gameInTransactionDao = daoFactory.getDao(GameInTransactionDao.class);
            TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
            GameDao gameDao = daoFactory.getDao(GameDao.class);
            user = userDao.findById(transaction.getUser().getId());
            user.spendCash(transaction.getPrice());
            userDao.update(user);
            transaction.setStatus(transactionStatusDao.findById(1));
            Transaction newTransaction = transactionDao.create(transaction);
            for (GameInTransaction gameInTransaction : transaction.getGameInTransactionList()) {
                Game game = gameDao.getByParams(Collections.singletonMap(GAME_ID,
                        String.valueOf(gameInTransaction.getId()))).get(0);
                game.setAmount(game.getAmount() - gameInTransaction.getAmount());
                gameDao.update(game);
                gameInTransaction.setTransaction(newTransaction);
                gameInTransactionDao.create(gameInTransaction);
                gameDao.addGameToLibrary(game, user);
            }
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            LOG.error(CANNOT_BUY_GAME, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(CANNOT_BUY_GAME, e);
        }
        return user;
    }

    public int getUsersCount() throws ServiceException {
        int usersCount;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                usersCount = userDao.getNotDeletedCount();
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_USERS_COUNT, e);
                throw new ServiceException(CANNOT_GET_USERS_COUNT, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_USERS_COUNT, e);
            throw new ServiceException(CANNOT_GET_USERS_COUNT, e);
        }
        return usersCount;
    }

    public int getTransactionsCount() throws ServiceException {
        int transactionsCount;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                daoFactory.startTransaction();
                transactionsCount = transactionDao.getNotDeletedCount();
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_TRANSACTIONS_COUNT, e);
                throw new ServiceException(CANNOT_GET_TRANSACTIONS_COUNT, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_TRANSACTIONS_COUNT, e);
            throw new ServiceException(CANNOT_GET_TRANSACTIONS_COUNT, e);
        }
        return transactionsCount;
    }

    public List<TransactionStatus> getAllTransactionStatuses() throws ServiceException {
        List<TransactionStatus> transactionStatusList;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionStatusDao transactionStatusDao = daoFactory.getDao(TransactionStatusDao.class);
                daoFactory.startTransaction();
                transactionStatusList = transactionStatusDao.getAll();
                transactionStatusList = transactionStatusList.stream().filter(status ->
                        !status.isDeleted()).collect(Collectors.toList());
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_TRANSACTION_STATUSES, e);
                throw new ServiceException(CANNOT_GET_TRANSACTION_STATUSES, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_TRANSACTION_STATUSES, e);
            throw new ServiceException(CANNOT_GET_TRANSACTION_STATUSES, e);
        }
        return transactionStatusList;
    }

    public void updateTransactionStatus(String transactionId, String statusId) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                daoFactory.startTransaction();
                Transaction transaction = transactionDao.findById(Integer.parseInt(transactionId));
                transaction.getStatus().setId(Integer.parseInt(statusId));
                transactionDao.update(transaction);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_TRANSACTION_STATUS, e);
                throw new ServiceException(CANNOT_UPDATE_TRANSACTION_STATUS, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_TRANSACTION_STATUS, e);
            throw new ServiceException(CANNOT_UPDATE_TRANSACTION_STATUS, e);
        }
    }

    public void deleteTransactionById(String id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                daoFactory.startTransaction();
                transactionDao.findById(Integer.parseInt(id));
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_DELETE_TRANSACTION, e);
                throw new ServiceException(CANNOT_DELETE_TRANSACTION, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_DELETE_TRANSACTION, e);
            throw new ServiceException(CANNOT_DELETE_TRANSACTION, e);
        }
    }

    public void deleteGameById(String id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                gameDao.findById(Integer.parseInt(id));
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_DELETE_GAME, e);
                throw new ServiceException(CANNOT_DELETE_GAME, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_DELETE_GAME, e);
            throw new ServiceException(CANNOT_DELETE_GAME, e);
        }
    }

    public void deleteUserById(String id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                userDao.findById(Integer.parseInt(id));
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_DELETE_USER, e);
                throw new ServiceException(CANNOT_DELETE_USER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_DELETE_USER, e);
            throw new ServiceException(CANNOT_DELETE_USER, e);
        }
    }

    public List<Game> getAllGamesOnPage(int pageSize, int pageNumber) throws ServiceException {
        List<Game> games;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                GenreDao genreDao = daoFactory.getDao(GenreDao.class);
                daoFactory.startTransaction();
                games = gameDao.getAll(pageNumber, pageSize);
                for (Game game : games) {
                    int idCheck = game.getGenre().getId();
                    game.setGenre(genreDao.findById(game.getGenre().getId()));
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_GAMES_ON_PAGE, e);
                throw new ServiceException(CANNOT_GET_GAMES_ON_PAGE, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_GAMES_ON_PAGE, e);
            throw new ServiceException(CANNOT_GET_GAMES_ON_PAGE, e);
        }
        return games;
    }

    public List<Game> getAllGames() throws ServiceException {
        List<Game> games;
        try (DaoFactory DaoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = DaoFactory.getDao(GameDao.class);
                games = gameDao.getAll();
            } catch (DaoException e) {
                LOG.info(CANNOT_GET_ALL_GAMES, e);
                throw new ServiceException(CANNOT_GET_ALL_GAMES, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_ALL_GAMES, e);
            throw new ServiceException(CANNOT_GET_ALL_GAMES, e);
        }
        return games;
    }

    public List<Gender> getAllGenders() {
        List<Gender> genders;
        genders = Arrays.asList(Gender.values());
        return genders;
    }

    public List<User> getAllUsersOnPage(int pageNumber, int pageSize) throws ServiceException {
        List<User> users;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                UserDao userDao = daoFactory.getDao(UserDao.class);
                daoFactory.startTransaction();
                users = userDao.getAll(pageNumber, pageSize);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_ALL_USERS, e);
                throw new ServiceException(CANNOT_GET_ALL_USERS, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_ALL_USERS, e);
            throw new ServiceException(CANNOT_GET_ALL_USERS, e);
        }
        return users;
    }

    public List<Transaction> getAllTransactionsOnPage(int pageSize, int pageNumber) throws ServiceException {
        List<Transaction> transactions;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                TransactionDao transactionDao = daoFactory.getDao(TransactionDao.class);
                TransactionStatusDao transactionStatusDao = daoFactory.getDao(TransactionStatusDao.class);
                GameInTransactionDao gameInTransactionDao = daoFactory.getDao(GameInTransactionDao.class);

                UserDao userDao = daoFactory.getDao(UserDao.class);
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                transactions = transactionDao.getAll(pageNumber, pageSize);
                for (Transaction transaction : transactions) {
                    transaction.setUser(userDao.findById(transaction.getUser().getId()));
                    transaction.setStatus(transactionStatusDao.findById(transaction.getStatus().getId()));
                    List<GameInTransaction> gameInTransactionList = gameInTransactionDao.getByParams(Collections.singletonMap
                            (TRANSACTION_ID, String.valueOf(transaction.getId())));
                    for (GameInTransaction gameInTransaction : gameInTransactionList) {
                        gameInTransaction.setGame(gameDao.findById(gameInTransaction.getGame().getId()));
                    }
                    transaction.setGameInTransactionList(gameInTransactionList);
                }
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_TRANSACTIONS_ON_PAGE, e);
                throw new ServiceException(CANNOT_GET_TRANSACTIONS_ON_PAGE, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_TRANSACTIONS_ON_PAGE, e);
            throw new ServiceException(CANNOT_GET_TRANSACTIONS_ON_PAGE, e);
        }
        return transactions;
    }
}
