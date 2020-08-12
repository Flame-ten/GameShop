package epam.andrew.gameShop.action.publisher;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Publisher;
import epam.andrew.gameShop.service.PublisherService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEditPublisherPageAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ShowEditPublisherPageAction.class);
    private static final String CANNOT_SHOW_EDIT_PUBLISHER_PAGE = "Couldn't show edit publisher page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PublisherService publisherService = new PublisherService();

        try {
            Publisher publisher = publisherService.getFilledPublisher(req.getParameter(Constant.ID));
            req.setAttribute(Constant.PUBLISHER, publisher);
            String imagePath = publisher.getImages().get(0).getPath() + "/" +
                    publisher.getImages().get(0).getName();
            req.setAttribute("imagePath", imagePath);
            return new ActionResult(Constant.EDIT_PUBLISHER);
        } catch (ServiceException e) {
            LOG.info(CANNOT_SHOW_EDIT_PUBLISHER_PAGE, e);
            throw new ActionException(CANNOT_SHOW_EDIT_PUBLISHER_PAGE, e);
        }
    }
}
