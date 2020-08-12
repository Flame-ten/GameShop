package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ShowGameManagePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowGameManagePageAction.class);
    private static final String ENCODING = "UTF-8";
    private static final String ERROR = "Cannot show manage games page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PageUtil pageUtil = new PageUtil();
        ShopService shopService = new ShopService();
        List<Game> games;
        String pageNumber = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        int gamesCount;
        try {
            req.setCharacterEncoding(ENCODING);
            games = shopService.getAllGamesOnPage(Integer.parseInt(pageSize), Integer.parseInt(pageNumber));
            gamesCount = shopService.getGamesCount();
        } catch (ServiceException | UnsupportedEncodingException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(gamesCount, pageSize);
        req.setAttribute(Constant.PAGE, pageNumber);
        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.GAMES, games);
        LOG.info(Constant.INFO, pageNumber, pageSize, pageCount);
        return new ActionResult(Constant.MANAGE_GAMES);
    }
}
