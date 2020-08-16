package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
    private static final String SERVICE_ERROR = "Couldn't login";
    private static final String LOGIN_ERROR = "loginError";
    private static final String LOGGED = "{} logged";
    private static final String INPUT_ERROR = "wrong email {} or password {}";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String email = req.getParameter(Constant.EMAIL);
        String password = req.getParameter(Constant.PASSWORD);
        String md5HexPassword = DigestUtils.md5Hex(password);
        User user;
        try {
            UserService userService = new UserService();
            user = userService.logIn(email, md5HexPassword);
            if (user != null) {
                req.getSession().setAttribute(Constant.LOGGED_USER, user);
                req.getSession().setAttribute(Constant.PASSWORD, password);
                LOG.info(LOGGED, user);
                return new ActionResult(Constant.PROFILE, true);
            } else {
                LOG.info(INPUT_ERROR, email, password);
                req.setAttribute(LOGIN_ERROR, TRUE);
                return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
            }
        } catch (ServiceException e) {
            LOG.info(SERVICE_ERROR, e);
            throw new ActionException(SERVICE_ERROR, e);
        }
    }
}
