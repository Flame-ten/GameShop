package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserManagePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowUserManagePageAction.class);
    private static final String ERROR = "Couldn't show manage users page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PageUtil pageUtil = new PageUtil();
        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        ShopService shopService = new ShopService();
        List<User> users;
        int usersCount;
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        try {
            users = shopService.getAllUsersOnPage(pageInt, pageSizeInt);
            usersCount = shopService.getUsersCount();
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(usersCount, pageSize);
        req.setAttribute(Constant.USERS, users);
        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.PAGE, page);
        LOG.info(Constant.INFO, page, pageSize, pageCount);
        String path = Constant.MANAGE_USERS;
        return new ActionResult(path);
    }
}
