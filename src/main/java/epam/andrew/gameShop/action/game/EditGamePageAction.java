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
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditGamePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditGamePageAction.class);
    private static final String INVALID_MONEY_FORMAT = "Invalid money format - {}";
    private static final String COULDN_T_EDIT_GAME = "Couldn't edit game";
    private static final String AFTER_GAME_EDIT_FLAG = "after game edit flag";
    private static final String TRUE = "true";
    private final GameUtil gameUtil = new GameUtil();
    private String id;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        try {
            GameService gameService = new GameService();
            if (checkPrice(req)) {
                return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
            }
            gameService.updateGame(getFilledGame(req));
            Image image = gameService.getGamePreviewImage(id);
            image = gameUtil.getFilledImage(image, req);
            if (!gameUtil.validateImage(req)) {
                gameService.updateGameImage(image);
            }
            req.setAttribute(AFTER_GAME_EDIT_FLAG, TRUE);
        } catch (IOException | ServiceException | ServletException e) {
            LOG.info(COULDN_T_EDIT_GAME, e);
            throw new ActionException(COULDN_T_EDIT_GAME, e);
        }
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);

    }

    private Game getFilledGame(HttpServletRequest req) {
        id = req.getParameter(Constant.ID);
        Game game = gameUtil.getFilledGame(req);
        game.setId(Integer.parseInt(id));
        return game;
    }

    private boolean checkPrice(HttpServletRequest req) throws ActionException {
        Validate validation = new Validate();
        String price = req.getParameter(Constant.PRICE);
        if (validation.checkMoney(req, price)) {
            LOG.info(INVALID_MONEY_FORMAT, price);
            return true;
        }
        return false;
    }
}
