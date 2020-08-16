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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowPublisherAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowPublisherAction.class);
    private static final String ERROR = "Cannot show publisher page action";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        Publisher publisher;
        List<Publisher> publishers;
        List<Publisher> publisherList = new ArrayList<>();
        try {
            PublisherService publisherService = new PublisherService();
            publisher = publisherService.getPublisherById(Integer.parseInt(req.getParameter(Constant.PUBLISHER_ID)));
            publishers = publisherService.getAllPublishers();
            Random random = new Random();
            for (int i = 1; i < 5; i++) {
                publisherList.add(publishers.get(random.nextInt(publishers.size() - 1)));
            }
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        req.setAttribute(Constant.PUBLISHER, publisher);
        req.setAttribute(Constant.PUBLISHERS, publisherList);
        return new ActionResult(Constant.PUBLISHER);
    }
}
