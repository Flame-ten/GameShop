package epam.andrew.gameShop.action.cart;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.GameInTransaction;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.service.GameService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddGameInCartAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AddGameInCartAction.class);
    private static final String ERROR_ADD = "Cannot add game to cart";
    private static final String INVALID_AMOUNT = "Invalid game amount format - {}";
    private static final String AMOUNT_INCREASED = "Product amount in cart increased by - {}";
    private static final String GAME_ADDED = "game - {} added in cart. Amount - {}";
    private String amount;
    private Transaction cart;


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        if (checkAmount(req)) {
            return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
        }
        cart = getCart(req);
        String gameId = req.getParameter(Constant.GAME);
        Integer amountInt = Integer.parseInt(amount);
        if (!increaseAmountIfAlreadyExists(amountInt, gameId, req)) {
            setGameInTransaction(req, amountInt, gameId);
        }

        return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
    }

    private boolean checkAmount(HttpServletRequest req) throws ActionException {
        amount = req.getParameter(Constant.AMOUNT);
        Validate validation = new Validate();
        if (validation.checkAmount(req, amount)) {
            req.setAttribute(Constant.ERROR_AMOUNT, Constant.TRUE);
            LOG.info(INVALID_AMOUNT, amount);
            return true;
        }
        return false;
    }

    private void setGameInTransaction(HttpServletRequest req, Integer amountInt, String productId) throws ActionException {
        try {
            GameInTransaction gameInTransaction = new GameInTransaction();
            gameInTransaction.setAmount(amountInt);
            GameService gameService = new GameService();
            Game game = gameService.getGameByID(Integer.parseInt(productId));
            gameInTransaction.setGame(game);
            cart.addGame(gameInTransaction);
            req.getSession().setAttribute(Constant.CART, cart);
            LOG.info(GAME_ADDED, game, amount);
        } catch (ServiceException e) {
            LOG.info(ERROR_ADD, e);
            throw new ActionException(ERROR_ADD, e);
        }
    }

    private boolean increaseAmountIfAlreadyExists(Integer amountInt, String productId, HttpServletRequest req) {
        for (GameInTransaction gameInTransaction : cart.getGameInTransactionList()) {                   //if already in cart
            if (gameInTransaction.getGame().getId() == Integer.parseInt(productId)) {
                gameInTransaction.setAmount(gameInTransaction.getAmount() + amountInt);
                req.getSession().setAttribute(Constant.CART, cart);
                LOG.info(AMOUNT_INCREASED, amount);
                return true;
            }
        }
        return false;
    }

    private Transaction getCart(HttpServletRequest req) {
        Transaction cart = (Transaction) req.getSession(false).getAttribute(Constant.CART);
        if (cart == null) {
            cart = new Transaction();
        }
        return cart;
    }
}
