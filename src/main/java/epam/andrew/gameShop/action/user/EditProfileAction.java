package epam.andrew.gameShop.action.user;

import epam.andrew.gameShop.action.Action;
import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.ActionResult;
import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import epam.andrew.gameShop.util.UserUtil;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProfileAction implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditProfileAction.class);
    private static final String UPDATED_ERROR = "Could not update profile";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        try {
            UserService userService = new UserService();
            User user;
            UserUtil userUtil = new UserUtil();
            Validate validation = new Validate();
            user = userService.getUserById(Integer.parseInt(req.getParameter(Constant.USER_ID)));
            if (validation.validateUser(req)) {
                return new ActionResult(Constant.PROFILE);
            }
            userUtil.fillUserExceptPassword(req, user);
            String path = Constant.SUCCESS_REGISTERED_PAGE;
            req.setAttribute(Constant.AFTER_PROFILE_CHANGE_FLAG, TRUE);
            req.getSession(false).setAttribute(Constant.LOGGED_USER, user);
            return new ActionResult(path);

        } catch (ServiceException e) {
            LOG.info(UPDATED_ERROR, e);
            throw new ActionException(UPDATED_ERROR, e);
        }
    }
}
