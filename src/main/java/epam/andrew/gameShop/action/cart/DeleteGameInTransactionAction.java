package epam.andrew.gameShop.action.cart;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteGameInTransactionAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteGameInTransactionAction.class);
    private static final String GAME_IN_TRANSACTION_DELETED = "game in transaction - {} deleted from cart";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        Transaction transaction = (Transaction) req.getSession().getAttribute(Constant.CART);
        String rowNumber = req.getParameter(Constant.GAME);
        int rowNumberInt = Integer.parseInt(rowNumber);
        LOG.info(GAME_IN_TRANSACTION_DELETED, transaction.getGameInTransactionList().get(rowNumberInt));
        transaction.getGameInTransactionList().remove(rowNumberInt);
        req.getSession().setAttribute(Constant.CART, transaction);
        return new ActionResult(Constant.CART);
    }
}
