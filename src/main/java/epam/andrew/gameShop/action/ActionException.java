package epam.andrew.gameShop.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(ActionException.class);
    private static final String CATCH_ACTION_EXCEPTION = "Catch action exception";

    public ActionException(Exception e) {
        super(e);
        LOG.error(CATCH_ACTION_EXCEPTION, e);
    }

    public ActionException(String message, Exception e) {
        super(message, e);
        LOG.error(message, e);
    }
}
