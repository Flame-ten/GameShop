package epam.andrew.gameShop.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionPoolException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPoolException.class);
    private static final String CATCH_CONNECTION_POOL_EXCEPTION = "Catch connection pool exception: ";

    public ConnectionPoolException(Exception e) {
        super(e);
        LOG.error(CATCH_CONNECTION_POOL_EXCEPTION, e);
    }

    public ConnectionPoolException(String message, Exception e) {
        super(message, e);
        LOG.error(message, e);
    }

}
