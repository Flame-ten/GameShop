package epam.andrew.gameShop.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(DaoException.class);
    private static final String CATCH_DAO_EXCEPTION = "Catch dao exception";

    public DaoException(Exception e) {
        super(e);
        LOG.error(CATCH_DAO_EXCEPTION, e);
    }

    public DaoException(String message, Exception e) {
        super(message, e);
        LOG.error(message, e);
    }
}

