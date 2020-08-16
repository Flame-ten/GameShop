package epam.andrew.gameShop.service;

import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.dao.DaoFactory;
import epam.andrew.gameShop.dao.imp.ImageDao;
import epam.andrew.gameShop.dao.imp.PublisherDao;
import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.entity.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class PublisherService {
    private static final Logger LOG = LoggerFactory.getLogger(PublisherService.class);
    private static final String CANNOT_GET_PREVIEW_IMAGE = "Cannot get preview image";
    private static final String PUBLISHER_ID = "publisher_id";
    private static final String CANNOT_DELETE_PUBLISHER = "Cannot delete publisher";
    private static final String CANNOT_GET_ALL_PUBLISHERS = "Cannot get all publishers";
    private static final String CANNOT_GET_PUBLISHERS_COUNT = "Cannot get publishers count";
    private static final String CANNOT_UPDATE_PUBLISHER_IMAGE = "Cannot update publisher image";
    private static final String CANNOT_UPDATE_PUBLISHER = "Cannot update publisher";
    private static final String CANNOT_FIND_PUBLISHER_BY_ID = "Cannot find publisher by id";
    private static final String CANNOT_GET_PUBLISHER_BY_ID = "Cannot get publisher by id";
    private static final String CANNOT_ADD_PUBLISHER = "Cannot add publisher";

    public Publisher addPublisher(Publisher publisher, Image image) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                daoFactory.startTransaction();
                addPublisherToDB(publisher, image, daoFactory);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_ADD_PUBLISHER, e);
                throw new ServiceException(CANNOT_ADD_PUBLISHER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_ADD_PUBLISHER, e);
            throw new ServiceException(CANNOT_ADD_PUBLISHER, e);
        }
        return publisher;
    }

    private void addPublisherToDB(Publisher publisher, Image image, DaoFactory daoFactory) throws ServiceException, DaoException {
        Publisher addedPublisher;
        try {
            daoFactory.startTransaction();
            PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
            ImageDao imageDao = daoFactory.getDao(ImageDao.class);
            addedPublisher = publisherDao.create(publisher);
            image.setPublisher(addedPublisher);
            imageDao.create(image);
            daoFactory.commitTransaction();
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_PUBLISHER_BY_ID, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(CANNOT_GET_PUBLISHER_BY_ID, e);
        }
    }

    public Publisher getPublisherById(int id) throws ServiceException {
        Publisher publisher;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
                daoFactory.startTransaction();
                publisher = publisherDao.findById(id);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_FIND_PUBLISHER_BY_ID, e);
                throw new ServiceException(CANNOT_FIND_PUBLISHER_BY_ID, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_FIND_PUBLISHER_BY_ID, e);
            throw new ServiceException(CANNOT_FIND_PUBLISHER_BY_ID, e);
        }
        return publisher;
    }

    public List<Publisher> getAllPublishers() throws ServiceException {
        List<Publisher> publishers;
        try (DaoFactory DaoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = DaoFactory.getDao(PublisherDao.class);
                publishers = publisherDao.getAll();
            } catch (DaoException e) {
                LOG.info(CANNOT_GET_ALL_PUBLISHERS, e);
                throw new ServiceException(CANNOT_GET_ALL_PUBLISHERS, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_ALL_PUBLISHERS, e);
            throw new ServiceException(CANNOT_GET_ALL_PUBLISHERS, e);
        }
        return publishers;
    }

    public void updatePublisher(Publisher publisher) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
                daoFactory.startTransaction();
                publisherDao.update(publisher);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_PUBLISHER, e);
                throw new ServiceException(CANNOT_UPDATE_PUBLISHER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_PUBLISHER, e);
            throw new ServiceException(CANNOT_UPDATE_PUBLISHER, e);
        }
    }

    public void updatePublisherImage(Image image) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ImageDao imageDao = daoFactory.getDao(ImageDao.class);
                daoFactory.startTransaction();
                imageDao.update(image);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_UPDATE_PUBLISHER_IMAGE, e);
                throw new ServiceException(CANNOT_UPDATE_PUBLISHER_IMAGE, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_UPDATE_PUBLISHER_IMAGE, e);
            throw new ServiceException(CANNOT_UPDATE_PUBLISHER_IMAGE, e);
        }
    }

    public int getPublishersCount() throws ServiceException {
        int publishersCount;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
                daoFactory.startTransaction();
                publishersCount = publisherDao.getNotDeletedCount();
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_PUBLISHERS_COUNT, e);
                throw new ServiceException(CANNOT_GET_PUBLISHERS_COUNT, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_PUBLISHERS_COUNT, e);
            throw new ServiceException(CANNOT_GET_PUBLISHERS_COUNT, e);
        }
        return publishersCount;
    }

    public void deletePublisherById(String id) throws ServiceException {
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
                daoFactory.startTransaction();
                publisherDao.findById(Integer.parseInt(id));
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_DELETE_PUBLISHER, e);
                throw new ServiceException(CANNOT_DELETE_PUBLISHER, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_DELETE_PUBLISHER, e);
            throw new ServiceException(CANNOT_DELETE_PUBLISHER, e);
        }
    }

    public List<Publisher> getAlLPublishersOnPage(int pageNumber, int pageSize) throws ServiceException {
        List<Publisher> publishers;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                PublisherDao publisherDao = daoFactory.getDao(PublisherDao.class);
                daoFactory.startTransaction();
                publishers = publisherDao.getAll(pageNumber, pageSize);
                daoFactory.commitTransaction();
            } catch (DaoException e) {
                daoFactory.rollbackTransaction();
                LOG.error(CANNOT_GET_ALL_PUBLISHERS, e);
                throw new ServiceException(CANNOT_GET_ALL_PUBLISHERS, e);
            }
        } catch (DaoException | ConnectionPoolException e) {
            LOG.error(CANNOT_GET_ALL_PUBLISHERS, e);
            throw new ServiceException(CANNOT_GET_ALL_PUBLISHERS, e);
        }
        return publishers;
    }

    public Image getPublisherPreviewImage(String id) throws ServiceException {
        List<Image> images;
        try (DaoFactory daoFactory = new DaoFactory()) {
            try {
                ImageDao imageDao = daoFactory.getDao(ImageDao.class);
                daoFactory.startTransaction();
                images = imageDao.getByParams(Collections.singletonMap(PUBLISHER_ID, id));
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
}
