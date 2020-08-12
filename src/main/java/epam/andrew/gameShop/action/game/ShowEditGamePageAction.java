package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.Genre;
import epam.andrew.gameShop.service.GameService;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowEditGamePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowEditGamePageAction.class);
    private static final String CANNOT_SHOW_EDIT_GAME_PAGE = "Couldn't show edit game page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        ShopService shopService = new ShopService();
        GameService gameService = new GameService();

        try {
            Game game = gameService.getFilledGame(req.getParameter(Constant.ID));
            req.setAttribute(Constant.GAME, game);
            List<Genre> genres = shopService.getAllGenres();
            req.setAttribute(Constant.GENRE, genres);
            String imagePath = game.getImages().get(0).getPath() + "/" +
                    game.getImages().get(0).getName();
            req.setAttribute("imagePath", imagePath);
            return new ActionResult(Constant.EDIT_GAME);
        } catch (ServiceException e) {
            LOG.info(CANNOT_SHOW_EDIT_GAME_PAGE, e);
            throw new ActionException(CANNOT_SHOW_EDIT_GAME_PAGE, e);
        }
    }
}
