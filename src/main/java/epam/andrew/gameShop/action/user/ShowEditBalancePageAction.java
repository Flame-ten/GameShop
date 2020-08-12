package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.action.game.ShowEditGamePageAction;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEditBalancePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowEditGamePageAction.class);
    private static final String ERROR = "Couldn't show user profile page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        try {
            String id = req.getParameter(Constant.ID);
            UserService userService = new UserService();
            User user = userService.getUserById(Integer.parseInt(id));
            req.setAttribute(Constant.USER, user);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        return new ActionResult(Constant.REFILL);
    }
}
