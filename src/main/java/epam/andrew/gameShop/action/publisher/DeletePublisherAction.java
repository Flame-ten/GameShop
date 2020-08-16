package epam.andrew.gameShop.action.publisher;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.service.PublisherService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePublisherAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeletePublisherAction.class);
    private static final String ERROR_PUBLISHER = "Couldn't delete publisher by id";
    private static final String DELETED_PUBLISHER = "publisher - {} has been deleted";
    private static final String AFTER_PUBLISHER_DELETE_FLAG = "after_publisher_delete_flag";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter(Constant.PUBLISHER_ID);
        try {
            PublisherService publisherService = new PublisherService();
            publisherService.deletePublisherById(id);
        } catch (ServiceException e) {
            LOG.info(ERROR_PUBLISHER, e);
            throw new ActionException(ERROR_PUBLISHER, e);
        }
        LOG.info(DELETED_PUBLISHER, id);
        req.setAttribute(AFTER_PUBLISHER_DELETE_FLAG, TRUE);
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
    }
}
