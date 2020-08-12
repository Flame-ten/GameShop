package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.service.GameService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowGameAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowGameAction.class);
    private static final String ERROR = "Could't show game page action";
    private static final String GAME_AMOUNT_ERROR = "game_amount_error";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        Game game;
        List<Game> allGames;
        List<Game> games = new ArrayList<>();
        try {
            GameService gameService = new GameService();
            ShopService shopService = new ShopService();
            game = gameService.getGameByID(Integer.parseInt(req.getParameter(Constant.ID)));
            allGames = shopService.getAllGames();
            Random random = new Random();
            if (game.getAmount() <= 0) {
                req.setAttribute(GAME_AMOUNT_ERROR, TRUE);
            }
            for (int i = 1; i < 5; i++) {
                games.add(allGames.get(random.nextInt(allGames.size() - 1)));
            }
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        req.setAttribute(Constant.GAME, game);
        req.setAttribute(Constant.GAMES, games);
        return new ActionResult(Constant.GAME);
    }
}
