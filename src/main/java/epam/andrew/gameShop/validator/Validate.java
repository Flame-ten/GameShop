package epam.andrew.gameShop.validator;

import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.action.user.UserRegistrationAction;
import epam.andrew.gameShop.service.ServiceException;
import epam.andrew.gameShop.service.UserService;
import epam.andrew.gameShop.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
    private static final Logger LOG = LoggerFactory.getLogger(Validate.class);
    private static final String CHECK_PARAMETER = "Check parameter '{}' with value '{}' by regex '{}'";
    private static final String WRONG_PARAMETER = "Parameter '{}' with value '{}' is unsuitable.";
    private static final String EMAIL_TAKEN = "email {} has already been taken!";
    private static final String NOT_EMPTY_NUMBER = "not_empty_phone_number.regex";
    private static final String MONEY_REGEX = "money.regex";
    private static final String CORRECT_PHONE_NUMBER = "phone_number.regex.regexp";
    private static final String NOT_EMPTY_TEXT = "not_empty_string.regex";
    private static final String NOT_EMPTY_PASSWORD = "not_empty_password.regex";
    private static final String CORRECT_PASSWORD = "password.regex.regexp";
    private static final String CORRECT_EMAIL = "email.regex.regexp";
    private static final String CORRECT_NAME = "name.regex";
    private static final String CORRECT_LOGIN = "login.regex.regexp";
    private static final String CORRECT_COUNTRY = "country.regex";
    private static final String PROPERTY_GAME_AMOUNT = "game.amount";
    private static final String EMPTY_NAME = "empty_name";
    private static final String EMPTY_LOGIN = "empty_login";
    private static final String EMPTY_SURNAME = "empty_surname";
    private static final String EMPTY_PHONE_NUMBER = "empty_phone_number";
    private static final String EMPTY_PASSWORD = "empty_password";
    private static final String EMPTY_GENDER_ERROR = "empty_gender";
    private static final String EMPTY_COUNTRY = "empty_country";
    private static final String EMAIL_USED_ERROR = "email_used";
    private static final String WRONG_EMAIL_ERROR = "wrong_email";
    private static final String WRONG_NAME_ERROR = "wrong_name";
    private static final String WRONG_LOGIN_ERROR = "wrong_login";
    private static final String WRONG_COUNTRY_ERROR = "wrong_country";
    private static final String WRONG_SURNAME_ERROR = "wrong_surname";
    private static final String WRONG_PHONE_ERROR = "wrong_phone_number";
    private static final String WRONG_PASSWORD_ERROR = "wrong_password";
    private static final String MONEY = "money";
    private static final String TRUE = "true";
    private static final String USED = "used";
    private static final String DIFFERENT_PASSWORD_ERROR = "different_password_error";
    private final Properties properties = new Properties();
    private String email;
    private boolean invalid;
    private String name;

    public boolean checkByRegex(String parameter, String parameterName, String regex, HttpServletRequest req) {
        LOG.debug(CHECK_PARAMETER, parameterName, parameter, regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        if (!matcher.matches()) {
            LOG.debug(WRONG_PARAMETER, parameterName, parameter);
            req.setAttribute(parameterName, TRUE);
            invalid = true;
            return true;
        }
        return false;
    }

    public boolean checkAmount(HttpServletRequest req, String amount) throws ActionException {
        loadProperties();
        return checkByRegex(amount, Constant.AMOUNT,
                properties.getProperty(PROPERTY_GAME_AMOUNT), req);
    }

    public boolean checkGameAmount(int enteredAmount, int StorageItemAmount) {

        if (enteredAmount > StorageItemAmount) {
            invalid = true;
        }
        return invalid;
    }

    public boolean checkGameAmount(int amount) {
        if (amount <= 0) {
            invalid = true;
        }
        return invalid;
    }

    public boolean fullCheck(HttpServletRequest req) throws ServiceException, ActionException {
        loadProperties();
        emailCheck(req);
        emailExistsCheck(req);
        validateUser(req);
        loginCheck(req);
        return invalid;
    }

    private void loadProperties() throws ActionException {
        try {
            properties.load(UserRegistrationAction.class.getClassLoader().getResourceAsStream(Constant.VALIDATION_PROPERTIES));
        } catch (IOException e) {
            LOG.info(Constant.PROPERTIES_ERROR, e);
            throw new ActionException(Constant.PROPERTIES_ERROR, e);
        }
    }

    public boolean validateUser(HttpServletRequest req) throws ActionException {
        loadProperties();
        nameCheck(req);
        surnameCheck(req);
        phoneNumberCheck(req);
        genderCheck(req);
        return invalid;
    }

    private boolean loginCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String login = req.getParameter(Constant.LOGIN);

        if (checkByRegex(login, Constant.LOGIN,
                properties.getProperty(NOT_EMPTY_TEXT), req)) {
            req.setAttribute(EMPTY_LOGIN, TRUE);
        }
        if (checkByRegex(login, Constant.LOGIN,
                properties.getProperty(CORRECT_LOGIN), req)) {
            req.setAttribute(WRONG_LOGIN_ERROR, TRUE);
        }

        req.setAttribute(Constant.LOGIN, login);
        return invalid;
    }

    private boolean emailCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        email = req.getParameter(Constant.EMAIL);

        if (checkByRegex(email, Constant.EMAIL,
                properties.getProperty(CORRECT_EMAIL), req)) {
            req.setAttribute(WRONG_EMAIL_ERROR, TRUE);
        }

        req.setAttribute(Constant.EMAIL, email);
        return invalid;
    }

    private boolean emailExistsCheck(HttpServletRequest req) throws ServiceException {

        UserService userService = new UserService();
        if (!userService.checkEmail(email)) {
            req.setAttribute(EMAIL_USED_ERROR, USED);
            LOG.error(EMAIL_TAKEN, email);
            invalid = true;
        }
        return invalid;
    }


    private boolean genderCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String gender = req.getParameter(Constant.GENDER);


        if (gender.equals(Constant.GENDER)) {
            req.setAttribute(EMPTY_GENDER_ERROR, TRUE);
            invalid = true;
        }

        req.setAttribute(Constant.NAME, name);
        return invalid;
    }

    private boolean nameCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        name = req.getParameter(Constant.NAME);

        if (checkByRegex(name, Constant.NAME,
                properties.getProperty(NOT_EMPTY_TEXT), req)) {
            req.setAttribute(EMPTY_NAME, TRUE);
        }
        if (checkByRegex(name, Constant.NAME,
                properties.getProperty(CORRECT_NAME), req)) {
            req.setAttribute(WRONG_NAME_ERROR, TRUE);
        }

        req.setAttribute(Constant.NAME, name);
        return invalid;
    }

    private boolean surnameCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String surname = req.getParameter(Constant.SURNAME);
        if (checkByRegex(surname, Constant.SURNAME,
                properties.getProperty(NOT_EMPTY_TEXT), req)) {
            req.setAttribute(EMPTY_SURNAME, TRUE);
        }
        if (checkByRegex(surname, Constant.SURNAME,
                properties.getProperty(CORRECT_NAME), req)) {
            req.setAttribute(WRONG_SURNAME_ERROR, TRUE);
        }

        req.setAttribute(Constant.SURNAME, surname);
        return invalid;
    }

    public boolean passwordCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String password = req.getParameter(Constant.PASSWORD);
        if (checkByRegex(password, Constant.PASSWORD,
                properties.getProperty(NOT_EMPTY_PASSWORD), req)) {
            req.setAttribute(EMPTY_PASSWORD, TRUE);
        }


        if (checkByRegex(password, Constant.PASSWORD,
                properties.getProperty(CORRECT_PASSWORD), req)) {
            req.setAttribute(WRONG_PASSWORD_ERROR, TRUE);
        }
        req.setAttribute(Constant.PASSWORD, password);
        return invalid;
    }

    public boolean newPasswordCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String newPassword = req.getParameter(Constant.NEW_PASSWORD);
        String repeatedPassword = req.getParameter(Constant.REPEATED_PASSWORD);
        if (!newPassword.equals(repeatedPassword)) {
            req.setAttribute(DIFFERENT_PASSWORD_ERROR, TRUE);
            invalid = true;
        }
        return invalid;

    }

    private boolean phoneNumberCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String phone = req.getParameter(Constant.PHONE);

        if (checkByRegex(phone, Constant.PHONE,
                properties.getProperty(NOT_EMPTY_NUMBER), req)) {
            req.setAttribute(EMPTY_PHONE_NUMBER, TRUE);
        }


        if (checkByRegex(phone, Constant.PHONE,
                properties.getProperty(CORRECT_PHONE_NUMBER), req)) {
            req.setAttribute(WRONG_PHONE_ERROR, TRUE);
        }

        req.setAttribute(Constant.PHONE, phone);
        return invalid;
    }

    private boolean countryCheck(HttpServletRequest req) throws ActionException {
        loadProperties();
        String country = req.getParameter(Constant.COUNTRY);

        if (checkByRegex(country, Constant.COUNTRY,
                properties.getProperty(NOT_EMPTY_TEXT), req)) {
            req.setAttribute(EMPTY_COUNTRY, TRUE);
        }
        if (checkByRegex(country, Constant.COUNTRY,
                properties.getProperty(CORRECT_COUNTRY), req)) {
            req.setAttribute(WRONG_COUNTRY_ERROR, TRUE);
        }

        req.setAttribute(Constant.COUNTRY, country);
        return invalid;
    }

    public boolean checkMoney(HttpServletRequest req, String price) throws ActionException {
        loadProperties();
        return checkByRegex(price, MONEY, properties.getProperty(MONEY_REGEX), req);
    }
}
