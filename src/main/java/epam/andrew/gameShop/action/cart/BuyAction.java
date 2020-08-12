package epam.andrew.gameShop.action.cart;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuyAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(BuyAction.class);
    private static final String ERROR_PLACING = "Couldn't place transaction";
    private static final String BOUGHT_TRANSACTION = "{} has been bought by - {}";
    private static final String SUCCESS_BUY = "success_buy";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Transaction transaction = (Transaction) req.getSession().getAttribute(Constant.CART);
        User loggedUser = (User) req.getSession().getAttribute(Constant.LOGGED_USER);
        transaction.setUser(loggedUser);
        if (loggedUser.getCash().isLessThan(transaction.getPrice())) {
            req.setAttribute(Constant.BALANCE_ERROR, TRUE);
            return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
        }
        try {
            ShopService shopService = new ShopService();
            User user = shopService.buyCart(transaction);
            req.getSession().setAttribute(Constant.LOGGED_USER, user);
            req.getSession(false).removeAttribute(Constant.CART);
            req.setAttribute(SUCCESS_BUY, TRUE);
            LOG.info(BOUGHT_TRANSACTION, transaction, loggedUser);
            return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
        } catch (ServiceException e) {
            LOG.info(ERROR_PLACING, e);
            throw new ActionException(ERROR_PLACING, e);
        }
    }
}
