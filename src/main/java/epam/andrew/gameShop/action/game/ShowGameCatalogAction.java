package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowGameCatalogAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowGameCatalogAction.class);
    private static final String ERROR = "Couldn't show catalog page";
    private static final String GAME_AMOUNT_ERROR = "game amount error";
    private static final String ERROR_MAP = "error map";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        List<Game> gamesByGenre = new ArrayList<>();
        PageUtil<Game> pageUtil = new PageUtil<>();
        Map<String, Integer> errorMap = new HashMap<>();

        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        String genre = req.getParameter(Constant.GENRE);
        if (genre == null) {
            genre = (String) req.getSession().getAttribute(Constant.GENRE);
        }
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        List<Game> games;
        try {
            ShopService shopService = new ShopService();
            games = shopService.getAllGames();
            for (Game game : games) {
                if (game.getGenre().getId().toString().equals(genre) &&
                        !game.isDeleted()) {
                    gamesByGenre.add(game);
                }
            }
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(gamesByGenre.size(), pageSize);
        List<Game> gamesOnPage = pageUtil.getEntitiesOnPage(gamesByGenre, pageInt, pageSizeInt);

        for (int i = 0; i < gamesOnPage.size(); i++) {
            Validate validate = new Validate();
            if (validate.checkGameAmount(gamesOnPage.get(i).getAmount())) {
                errorMap.put(GAME_AMOUNT_ERROR, i);
            }
        }

        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.PAGE, page);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.SHOP_ITEMS, gamesOnPage);
        req.setAttribute(ERROR_MAP, errorMap);
        if ((genre != null)) {
            req.getSession().setAttribute(Constant.GENRE, genre);
        }

        LOG.info(Constant.INFO, page, pageSize, pageCount);
        return new ActionResult(Constant.CATALOG_PAGE);
    }


}
