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

public class EditPublisherAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditPublisherAction.class);
    private static final String CANNOT_EDIT_PUBLISHER = "Cannot edit publisher";
    private static final String AFTER_PUBLISHER_EDIT_FLAG = "after publisher edit flag";
    private static final String TRUE = "true";
    private final PublisherUtil publisherUtil = new PublisherUtil();
    private String id;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        try {
            PublisherService publisherService = new PublisherService();
            publisherService.updatePublisher(getFilledPublisher(req));
            Image image = publisherService.getPublisherPreviewImage(id);
            image = publisherUtil.getFilledImage(image, req);
            if (!publisherUtil.validateImage(req)) {
                publisherService.updatePublisherImage(image);
            }
            req.setAttribute(AFTER_PUBLISHER_EDIT_FLAG, TRUE);
        } catch (IOException | ServiceException | ServletException e) {
            LOG.info(CANNOT_EDIT_PUBLISHER, e);
            throw new ActionException(CANNOT_EDIT_PUBLISHER, e);
        }
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);

    }

    private Publisher getFilledPublisher(HttpServletRequest req) {
        id = req.getParameter(Constant.PUBLISHER_ID);
        Publisher publisher = publisherUtil.getFilledPublisher(req);
        publisher.setId(Integer.parseInt(id));
        return publisher;
    }
}
