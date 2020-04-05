package epam.andrew.pixel.web.servlet;

import epam.andrew.pixel.action.Action;
import epam.andrew.pixel.action.ActionException;
import epam.andrew.pixel.action.ActionFactory;
import epam.andrew.pixel.action.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GameShopServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        Action action = ActionFactory.getAction(actionName);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }
        ActionResult result = null;
        try {
            result = action.execute(req, resp);
            if (result == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
                return;
            }
        } catch (ActionException e) {
            throw new ServletException("Cannot execute action", e);
        }
    }

}
