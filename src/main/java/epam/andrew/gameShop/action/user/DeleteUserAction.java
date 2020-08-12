package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteUserAction.class);
    private static final String DELETE_ERROR = "Couldn't delete user by id";
    private static final String DELETE_LOG_ERROR = "{} tried to delete himself when logged in";
    private static final String LOG_DELETED = "{} - has been deleted";
    private static final String AFTER_USER_DELETE_FLAG = "afterUserDeleteFlag";
    private static final String TRUE = "true";
    private static final String USER_DELETE_ERROR = "userDeleteError";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        User user = (User) req.getSession().getAttribute(Constant.LOGGED_USER);
        String id = req.getParameter(Constant.ID);
        if (id.equals(String.valueOf(user.getId()))) {
            req.setAttribute(USER_DELETE_ERROR, Constant.TRUE);
            LOG.info(DELETE_LOG_ERROR, user);
            return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
        }
        try {
            ShopService shopService = new ShopService();
            shopService.deleteUserById(id);
            LOG.info(LOG_DELETED, user);
            req.setAttribute(AFTER_USER_DELETE_FLAG, TRUE);
            return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
        } catch (ServiceException e) {
            LOG.info(DELETE_ERROR, e);
            throw new ActionException(DELETE_ERROR, e);
        }
    }
}
