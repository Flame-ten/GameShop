package epam.andrew.gameShop.action.language;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.util.Locale;

public class SetLanguageAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(SetLanguageAction.class);
    private static final String LANGUAGE = "language";
    private static final int MAX_AGE = 24 * 60 * 60;
    private static final String CHANGED = "{} changed language to {}";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        String language = req.getParameter(LANGUAGE);
        req.getSession().setAttribute(LANGUAGE, language);
        Config.set(req.getSession(), Config.FMT_LOCALE, new Locale(language));
        Cookie cookie = new Cookie(LANGUAGE, language);
        cookie.setMaxAge(MAX_AGE);
        resp.addCookie(cookie);
        LOG.info(CHANGED, req.getSession(false).getAttribute(Constant.LOGGED_USER), language);
        return new ActionResult(req.getHeader(Constant.REFERER_PAGE), true);
    }
}
