package epam.andrew.gameShop.action.publisher;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.entity.Publisher;
import epam.andrew.gameShop.service.PublisherService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PublisherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPublisherAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AddPublisherAction.class);
    private static final String CANNOT_ADD_PUBLISHER = "Couldn't add publisher";
    private static final String ADDED_PUBLISHER = "{} inserted in db and added on central shop by {}";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        try {
            PublisherUtil publisherUtil = new PublisherUtil();
            PublisherService publisherService = new PublisherService();
            Publisher publisher = publisherUtil.getFilledPublisher(req);
            Image image = new Image();
            Publisher newPublisher = publisherService.addPublisher(publisher, publisherUtil.getFilledImage(image, req));
            req.setAttribute(Constant.AFTER_PUBLISHER_ADD_FLAG, TRUE);
            LOG.info(ADDED_PUBLISHER, newPublisher, req.getSession(false));
        } catch (ServiceException | IOException | ServletException e) {
            LOG.info(CANNOT_ADD_PUBLISHER, e);
            throw new ActionException(CANNOT_ADD_PUBLISHER, e);
        }
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
    }

}
