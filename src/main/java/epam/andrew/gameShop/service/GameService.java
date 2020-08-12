package epam.andrew.gameShop.service;

import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.dao.DaoFactory;
import epam.andrew.gameShop.dao.imp.GameDao;
import epam.andrew.gameShop.dao.imp.GenreDao;
import epam.andrew.gameShop.dao.imp.ImageDao;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.Genre;
import epam.andrew.gameShop.entity.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameService {
    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);
    private static final String CANNOT_UPDATE_GAME_IMAGE = "Cannot update game image";
    private static final String CANNOT_UPDATE_GAME = "Cannot update game";
    private static final String CANNOT_GET_FILLED_GAME = "Cannot get filled game";
    private static final String GAME_ID = "game_id";
    private static final String CANNOT_GET_PREVIEW_IMAGE = "Cannot get preview image";
    private static final String CANNOT_GET_GAME_BY_ID = "Cannot get game by id";
    private static final String CANNOT_ADD_GAME = "Cannot add game";
    private static final String CANNOT_FIND_GAME_BY_ID = "Cannot find game by id";

    public Game getGameByID(int id) throws ServiceException {
        Game game;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                game = gameDao.findById(id);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_FIND_GAME_BY_ID, e);
                throw new ServiceException(CANNOT_FIND_GAME_BY_ID, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_FIND_GAME_BY_ID, e);
            throw new ServiceException(CANNOT_FIND_GAME_BY_ID, e);
        }
        return game;
    }

    public Game addGame(Game game, Image image) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                daoFactory.startTransaction();
                addGameToDB(game, image, daoFactory);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_ADD_GAME, e);
                throw new ServiceException(CANNOT_ADD_GAME, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_ADD_GAME, e);
            throw new ServiceException(CANNOT_ADD_GAME, e);
        }
        return game;
    }

    private void addGameToDB(Game game, Image image, DaoFactory daoFactory) throws ServiceException, DaoException, ConnectionPoolException {
        Game addedGame;
        try {
            daoFactory.startTransaction();
            GameDao gameDao = daoFactory.getDao(GameDao.class);
            ImageDao imageDao = daoFactory.getDao(ImageDao.class);
            addedGame = gameDao.create(game);
            image.setGame(addedGame);
            imageDao.create(image);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            daoFactory.rollbackTransaction();
            LOG.error(CANNOT_GET_GAME_BY_ID, e);
            throw new ServiceException(CANNOT_GET_GAME_BY_ID, e);
        }
    }

    public Image getGamePreviewImage(String id) throws ServiceException {
        List<Image> images;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ImageDao imageDao = daoFactory.getDao(ImageDao.class);
                daoFactory.startTransaction();
                images = imageDao.getByParams(Collections.singletonMap(GAME_ID, id));
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_PREVIEW_IMAGE, e);
                throw new ServiceException(CANNOT_GET_PREVIEW_IMAGE, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_PREVIEW_IMAGE, e);
            throw new ServiceException(CANNOT_GET_PREVIEW_IMAGE, e);
        }
        return images.get(0);
    }

    public Game getFilledGame(String id) throws ServiceException {
        Game game;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                daoFactory.startTransaction();
                game = fillGame(id, daoFactory);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_FILLED_GAME, e);
                throw new ServiceException(CANNOT_GET_FILLED_GAME, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_FILLED_GAME, e);
            throw new ServiceException(CANNOT_GET_FILLED_GAME, e);
        }
        return game;
    }

    private Game fillGame(String id, DaoFactory daoFactory) throws ServiceException, DaoException, ConnectionPoolException {
        Game game;
        try {
            daoFactory.startTransaction();
            GameDao gameDao = daoFactory.getDao(GameDao.class);
            ImageDao imageDao = daoFactory.getDao(ImageDao.class);
            GenreDao genreDao = daoFactory.getDao(GenreDao.class);
            game = gameDao.findById(Integer.parseInt(id));
            Genre genre = genreDao.findById(game.getGenre().getId());
            game.setGenre(genre);
            Map<String, String> gameParam = Collections.singletonMap(GAME_ID, id);
            List<Image> images = imageDao.getByParams(gameParam);
            game.setImages(images);
            daoFactory.commitTransaction();
        } catch (DaoException e) {
            LOG.error(CANNOT_GET_FILLED_GAME, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(CANNOT_GET_FILLED_GAME, e);
        }
        return game;
    }

    public void updateGame(Game game) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                GameDao gameDao = daoFactory.getDao(GameDao.class);
                daoFactory.startTransaction();
                gameDao.update(game);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_GAME, e);
                throw new ServiceException(CANNOT_UPDATE_GAME, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_GAME, e);
            throw new ServiceException(CANNOT_UPDATE_GAME, e);
        }
    }

    public void updateGameImage(Image image) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ImageDao imageDao = daoFactory.getDao(ImageDao.class);
                daoFactory.startTransaction();
                imageDao.update(image);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_GAME_IMAGE, e);
                throw new ServiceException(CANNOT_UPDATE_GAME_IMAGE, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_GAME_IMAGE, e);
            throw new ServiceException(CANNOT_UPDATE_GAME_IMAGE, e);
        }
    }
}
