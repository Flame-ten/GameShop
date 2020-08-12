package epam.andrew.gameShop.action.transaction;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.TransactionStatus;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTransactionManagePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowTransactionManagePageAction.class);
    private static final String ERROR = "Cannot show manage transactions page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PageUtil pageUtil = new PageUtil();
        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        List<Transaction> transactions;
        List<TransactionStatus> transactionStatuses;
        int transactionsCount;
        try {
            ShopService shopService = new ShopService();
            transactions = shopService.getAllTransactionsOnPage(Integer.parseInt(pageSize), Integer.parseInt(page));
            transactionsCount = shopService.getTransactionsCount();
            transactionStatuses = shopService.getAllTransactionStatuses();
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(transactionsCount, pageSize);
        req.setAttribute(Constant.TRANSACTIONS, transactions);
        req.setAttribute(Constant.STATUSES, transactionStatuses);
        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.PAGE, page);
        LOG.info(Constant.INFO, page, pageSize, pageCount);
        return new ActionResult(Constant.MANAGE_TRANSACTIONS);
    }
}
