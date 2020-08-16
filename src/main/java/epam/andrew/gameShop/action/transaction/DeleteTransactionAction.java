package epam.andrew.gameShop.action.transaction;

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

public class DeleteTransactionAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteTransactionAction.class);
    private static final String ERROR_DELETE = "Cannot delete transaction by id";
    private static final String TRANSACTION_DELETED = "transaction - {} has been deleted";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String id = req.getParameter(Constant.USER_ID);
        try {
            ShopService shopService = new ShopService();
            shopService.deleteTransactionById(id);
        } catch (ServiceException e) {
            LOG.info(ERROR_DELETE, e);
            throw new ActionException(ERROR_DELETE, e);
        }
        LOG.info(TRANSACTION_DELETED, id);
        return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
    }
}
