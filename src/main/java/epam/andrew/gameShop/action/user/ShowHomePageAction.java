package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowHomePageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowHomePageAction.class);
    private static final String ERROR = "Couldn't fill content at home page";
    private static final String GENRE = "genre";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException {
        return new ActionResult(Constant.HOME);
    }
}
