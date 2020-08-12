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

public class ChangeTransactionStatusAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeTransactionStatusAction.class);
    private static final String EDIT_ERROR = "Couldn't edit transaction status";
    private static final String UPDATED = "{} order has been updated - status: {}";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        ShopService shopService = new ShopService();
        try {
            String statusId = req.getParameter(Constant.STATUS_ID);
            String orderId = req.getParameter(Constant.TRANSACTION_ID);
            shopService.updateTransactionStatus(orderId, statusId);
            LOG.info(UPDATED, orderId, statusId);
        } catch (ServiceException e) {
            LOG.info(EDIT_ERROR, e);
            throw new ActionException(EDIT_ERROR, e);
        }
        return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
    }


}
