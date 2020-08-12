package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditBalanceAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditBalanceAction.class);
    private static final String REFILL = "refill";
    private static final String BALANCE = "balance";
    private static final String REFILLED_BALANCE = "{} refilled balance --> +{}";
    private static final String REFILL_ERROR = "Couldn't refill balance";
    private final UserService userService = new UserService();
    private String cash;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        try {
            String id = req.getParameter(Constant.USER_ID);
            cash = req.getParameter(BALANCE);
            Validate validation = new Validate();
            User user = userService.getUserById(Integer.parseInt(id));
            if (validation.checkMoney(req, cash)) {
                req.setAttribute(Constant.USER, user);
                return new ActionResult(REFILL);
            }
            updateUserBalance(req, user);
            return new ActionResult(Constant.MANAGE_USERS_REDIRECT, true);
        } catch (ServiceException e) {
            LOG.info(REFILL_ERROR, e);
            throw new ActionException(REFILL_ERROR, e);
        }
    }

    private void updateUserBalance(HttpServletRequest req, User user) throws ServiceException {
        User updatedUser;
        User loggedUser = (User) req.getSession().getAttribute(Constant.LOGGED_USER);
        if (loggedUser.equals(user)) {
            updatedUser = userService.setUserCash(loggedUser.getId(), cash);
            req.getSession().setAttribute(Constant.LOGGED_USER, updatedUser);
        } else {
            updatedUser = userService.setUserCash(user.getId(), cash);
        }
        LOG.info(REFILLED_BALANCE, updatedUser, cash);
    }
}
