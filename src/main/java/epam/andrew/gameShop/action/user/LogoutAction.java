package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutAction.class);
    private static final String LOGOUT = "{} logged out";

    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        LOG.info(LOGOUT, req.getSession(false).getAttribute(Constant.LOGGED_USER));
        req.getSession(false).removeAttribute(Constant.LOGGED_USER);
        return new ActionResult(Constant.HOME, true);
    }
}
