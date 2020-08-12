package epam.andrew.gameShop.action.publisher;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Publisher;
import epam.andrew.gameShop.service.PublisherService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowPublisherManagePageAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ShowPublisherManagePageAction.class);
    private static final String ENCODING = "UTF-8";
    private static final String ERROR = "Cannot show manage publishers page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PageUtil pageUtil = new PageUtil();
        PublisherService publisherService = new PublisherService();
        List<Publisher> publishers;
        String pageNumber = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        int publishersCount;
        try {
            req.setCharacterEncoding(ENCODING);
            publishers = publisherService.getAlLPublishersOnPage(Integer.parseInt(pageSize), Integer.parseInt(pageNumber));
            publishersCount = publisherService.getPublishersCount();
        } catch (ServiceException | UnsupportedEncodingException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(publishersCount, pageSize);
        req.setAttribute(Constant.PAGE, pageNumber);
        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.PUBLISHERS, publishers);
        LOG.info(Constant.INFO, pageNumber, pageSize, pageCount);
        return new ActionResult(Constant.MANAGE_PUBLISHERS);
    }
}
