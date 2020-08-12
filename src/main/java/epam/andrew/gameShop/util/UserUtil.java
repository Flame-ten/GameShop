package epam.andrew.gameShop.util;

import epam.andrew.gameShop.entity.User;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    private static final String CHANGE_PASSWORD_FORM = "changePasswordForm";
    private static final String TRUE = "true";
    private String name;
    private String surname;
    private String login;
    private String email;
    private String password;
    private String phone;
    private String country;
    private String gender;


    private void setUserParamsFromRequest(HttpServletRequest req) {
        String changePasswordFlag = req.getParameter(CHANGE_PASSWORD_FORM);
        name = req.getParameter(Constant.NAME);
        surname = req.getParameter(Constant.SURNAME);
        login = req.getParameter(Constant.LOGIN);
        email = req.getParameter(Constant.EMAIL);
        phone = req.getParameter(Constant.PHONE);
        gender = req.getParameter(Constant.GENDER);
        country = req.getParameter(Constant.COUNTRY);

        if (changePasswordFlag != null && changePasswordFlag.equals(TRUE)) {
            password = req.getParameter(Constant.NEW_PASSWORD);
        }
        password = req.getParameter(Constant.PASSWORD);
    }

    private void setUserParamsFromRequestExceptPassword(HttpServletRequest req) {
        name = req.getParameter(Constant.NAME);
        surname = req.getParameter(Constant.SURNAME);
        login = req.getParameter(Constant.LOGIN);
        email = req.getParameter(Constant.EMAIL);
        phone = req.getParameter(Constant.PHONE);
        gender = req.getParameter(Constant.GENDER);
        country = req.getParameter(Constant.COUNTRY);

    }

    public User fillUser(HttpServletRequest req) throws ServiceException {
        User user = new User();
        setUserParamsFromRequest(req);
        String md5HexPassword;
        DigestUtils.md5Hex(password);
        UserService userService = new UserService();
        if (email != null) {
            user.setEmail(email);
        }
        if (req.getParameter(CHANGE_PASSWORD_FORM).equals(TRUE)) {

            md5HexPassword = DigestUtils.md5Hex(password);
            user.setPassword(md5HexPassword);
        }
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPhone(phone);
        user.getRole().setId(Integer.valueOf(req.getParameter(Constant.ROLE_ID)));
        user.setGender(gender);
        user.setCountry(country);
        userService.updateUser(user);
        req.getSession(false).removeAttribute(Constant.GENDERS);
        return user;
    }

    public void fillUserExceptPassword(HttpServletRequest req, User user) throws ServiceException {
        setUserParamsFromRequestExceptPassword(req);
        UserService userService = new UserService();
        if (email != null) {
            user.setEmail(email);
        }
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPhone(phone);
        user.getRole().setId(Integer.valueOf(req.getParameter(Constant.ROLE_ID)));
        user.setGender(gender);
        user.setCountry(country);
        userService.updateUser(user);
        req.getSession(false).removeAttribute(Constant.GENDERS);

    }

}
