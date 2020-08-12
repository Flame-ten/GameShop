package epam.andrew.gameShop.action;

import epam.andrew.gameShop.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {

    ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException, ServiceException;
}
