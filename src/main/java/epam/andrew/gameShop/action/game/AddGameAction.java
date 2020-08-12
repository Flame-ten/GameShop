package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.service.GameService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.GameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddGameAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AddGameAction.class);
    private static final String CANNOT_ADD_GAME = "Couldn't add game";
    private static final String ADDED_GAME = "{} inserted in db and added on central shop by {}";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        try {
            GameUtil gameUtil = new GameUtil();
            GameService gameService = new GameService();
            if (gameUtil.validateMoney(req)) {
                return new ActionResult(Constant.ADD_GAME);
            }
            Game game = gameUtil.getFilledGame(req);
            Image image = new Image();
            Game newGame = gameService.addGame(game, gameUtil.getFilledImage(image, req));
            req.setAttribute(Constant.AFTER_GAME_ADD_FLAG, TRUE);
            LOG.info(ADDED_GAME, newGame, req.getSession(false).getAttribute(Constant.LOGGED_USER));
        } catch (ServiceException | IOException | ServletException e) {
            LOG.info(CANNOT_ADD_GAME, e);
            throw new ActionException(CANNOT_ADD_GAME, e);
        }
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
    }
}
