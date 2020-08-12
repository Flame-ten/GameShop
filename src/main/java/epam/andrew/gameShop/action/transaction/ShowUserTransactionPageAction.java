package epam.andrew.gameShop.action.transaction;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserTransactionPageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowUserTransactionPageAction.class);
    private static final String ERROR = "Couldn't get transactions";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        PageUtil<Transaction> pageUtil = new PageUtil<>();
        User user = (User) req.getSession().getAttribute(Constant.LOGGED_USER);
        List<Transaction> transactions;
        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        List<Transaction> transactionsOnPage;
        try {
            UserService userService = new UserService();
            transactions = userService.getUserTransactions(user.getId());
            transactionsOnPage = pageUtil.getEntitiesOnPage(transactions, pageInt, pageSizeInt);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(transactions.size(), pageSize);
        req.setAttribute(Constant.PAGE, page);
        req.setAttribute(Constant.PAGE_SIZE, pageSize);
        req.setAttribute(Constant.PAGES_COUNT, pageCount);
        req.setAttribute(Constant.TRANSACTIONS, transactionsOnPage);
        LOG.info(Constant.INFO, page, pageSize, pageCount);
        String path = Constant.USER_TRANSACTIONS;
        return new ActionResult(path);
    }
}
