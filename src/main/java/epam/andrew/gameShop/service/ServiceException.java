package epam.andrew.gameShop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceException.class);
    private static final String CATCH_SERVICE_EXCEPTION = "Catch service exception";

    public ServiceException(Exception e) {
        super(e);
        LOG.error(CATCH_SERVICE_EXCEPTION, e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
        LOG.error(message, e);
    }
}
