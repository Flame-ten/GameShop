package epam.andrew.gameShop.action.transaction;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowTransactionAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowTransactionAction.class);
    private static final String ERROR = "Cannot show transaction page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String orderId = req.getParameter(Constant.TRANSACTION_ID);
        Transaction transaction;
        try {
            ShopService shopService = new ShopService();
            transaction = shopService.getTransaction(Integer.parseInt(orderId));
            UserService userService = new UserService();
            User user = userService.getUserById(transaction.getUser().getId());
            req.setAttribute(Constant.TRANSACTION, transaction);
            req.setAttribute(Constant.USER, user);
            return new ActionResult(Constant.TRANSACTION);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }

    }


}
