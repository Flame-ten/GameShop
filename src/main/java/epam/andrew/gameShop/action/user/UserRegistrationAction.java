package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.UserUtil;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserRegistrationAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(UserRegistrationAction.class);
    private static final String CANNOT_REGISTER_USER = "Cannot RegisterUser user";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        Validate validation = new Validate();
        try {
            if (validation.fullCheck(req) && validation.passwordCheck(req)) {
                return new ActionResult(Constant.REGISTER);
            }
            UserUtil userUtil = new UserUtil();
            User user = userUtil.fillUser(req);
            UserService userService = new UserService();
            User registeredUser = userService.registerUser(user);
            req.getSession(false).setAttribute(Constant.LOGGED_USER, registeredUser);
            req.setAttribute(Constant.AFTER_REGISTER_FLAG, TRUE);
        } catch (ServiceException e) {
            LOG.error(CANNOT_REGISTER_USER);
            throw new ActionException(CANNOT_REGISTER_USER, e);
        }
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
    }
}
