package epam.andrew.gameShop.action.game;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteGameAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteGameAction.class);
    private static final String ERROR_DELETE = "Couldn't delete game by id";
    private static final String DELETED_GAME = "game - {} has been deleted";
    private static final String AFTER_GAME_DELETE_FLAG = "after_game_delete_flag";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter(Constant.ID);
        try {
            ShopService shopService = new ShopService();
            shopService.deleteGameById(id);
        } catch (ServiceException e) {
            LOG.info(ERROR_DELETE, e);
            throw new ActionException(ERROR_DELETE, e);
        }
        LOG.info(DELETED_GAME, id);
        req.setAttribute(AFTER_GAME_DELETE_FLAG, TRUE);
        return new ActionResult(Constant.SUCCESS_REGISTERED_PAGE);
    }


}
