package epam.andrew.gameShop.action.cart;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearCartAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ClearCartAction.class);
    private static final String CART_CLEAR_MESSAGE = "cleared cart by - {}";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        req.getSession(false).removeAttribute(Constant.CART);
        LOG.info(CART_CLEAR_MESSAGE, req.getSession().getAttribute(Constant.LOGGED_USER));
        return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
    }
}
