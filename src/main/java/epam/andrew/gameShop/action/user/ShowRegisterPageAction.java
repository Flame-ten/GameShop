package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.enums.Gender;
import epam.andrew.gameShop.service.ShopService;
import epam.andrew.gameShop.util.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRegisterPageAction implements Action {

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        List<Gender> genders;
        ShopService shopService = new ShopService();
        genders = shopService.getAllGenders();
        req.getSession(false).setAttribute(Constant.GENDERS, genders);
        return new ActionResult(Constant.REGISTER);
    }
}
