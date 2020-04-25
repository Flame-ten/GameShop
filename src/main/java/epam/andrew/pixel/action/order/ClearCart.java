package epam.andrew.pixel.action.order;

import epam.andrew.pixel.action.Action;
import epam.andrew.pixel.action.ActionException;
import epam.andrew.pixel.action.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearCart implements Action {
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        return null;
    }
}
