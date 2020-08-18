package epam.andrew.gameShop.action.cart;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.GameInTransaction;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecountCartAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(RecountCartAction.class);
    private static final String INVALID_GAME_AMOUNT_FORMAT = "Invalid game amount format - {}";
    private static final String AMOUNT_SET_TO = "{} amount set to {}";
    private static final String GAME_AMOUNT_ERROR = "gameAmountError";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        Game game = new Game();
        Transaction cart = (Transaction) req.getSession(false).getAttribute(Constant.CART);
        List<GameInTransaction> gamesInTransaction = cart.getGameInTransactionList();
        Map<Integer, String> errorMap = new HashMap<>();
        for (int i = 0; i < gamesInTransaction.size(); i++) {
            String amount = req.getParameter(Constant.GAME + i);
            Validate validation = new Validate();
            if (validation.checkAmount(req, amount)) {
                errorMap.put(i, Constant.TRUE);
                LOG.info(INVALID_GAME_AMOUNT_FORMAT, amount);
            } else {
                int gameAmount = game.getAmount();

                if (validation.checkGameAmount(Integer.parseInt(amount), gameAmount)) {
                    gamesInTransaction.get(i).setAmount(gameAmount);
                    errorMap.put(i, GAME_AMOUNT_ERROR);
                } else {
                    gamesInTransaction.get(i).setAmount(Integer.parseInt(amount));
                }
                LOG.info(AMOUNT_SET_TO, gamesInTransaction.get(i), amount);
            }
        }
        req.setAttribute(Constant.ERROR_MAP, errorMap);
        req.getSession().setAttribute(Constant.CART, cart);
        return new ActionResult(Constant.CART);
    }
}
