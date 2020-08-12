package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Genre;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAddGamePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowAddGamePageAction.class);
    private static final String ERROR = "Cannot show game add page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        List<Genre> genres;
        try {
            ShopService shopService = new ShopService();
            genres = shopService.getAllGenres();
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        req.getSession(false).setAttribute(Constant.GENRES, genres);
        return new ActionResult(Constant.ADD_GAME);
    }
}
