package epam.andrew.gameShop.action;

import epam.andrew.gameShop.action.cart.*;
import epam.andrew.gameShop.action.game.*;
import epam.andrew.gameShop.action.language.SetLanguageAction;
import epam.andrew.gameShop.action.publisher.*;
import epam.andrew.gameShop.action.transaction.*;
import epam.andrew.gameShop.action.user.*;
import epam.andrew.gameShop.util.Constant;

import java.util.HashMap;
import java.util.Map;

public final class ActionFactory {
    public static final String USER_ACTION_REGISTRATION = "POST/register";
    public static final String USER_ACTION_LOGIN = "POST/login";
    public static final String USER_ACTION_LOGOUT = "GET/logout";
    public static final String USER_ACTION_SHOW_REGISTRATION_PAGE = "GET/register";
    public static final String USER_ACTION_SHOW_EDIT_BALANCE_PAGE = "GET/editBalance";
    public static final String USER_ACTION_EDIT_PROFILE = "POST/editProfile";
    public static final String USER_ACTION_EDIT_BALANCE = "POST/editBalance";
    public static final String USER_ACTION_DELETE_USER = "GET/deleteUser";
    public static final String USER_ACTION_CHANGE_PASSWORD = "POST/password";
    public static final String USER_ACTION_SHOW_HOME_PAGE = "GET/home";
    public static final String USER_ACTION_SHOW_CHANGE_PASSWORD = "GET/password";
    public static final String USER_ACTION_SHOW_PROFILE_PAGE = "GET/profile";
    public static final String USER_ACTION_SHOW_LIST_PAGE = "GET/users";

    public static final String TRANSACTION_ACTION_CHANGE_STATUS = "POST/status";
    public static final String TRANSACTION_ACTION_SHOW_PAGE = "GET/transaction";
    public static final String TRANSACTION_ACTION_DELETE = "GET/deleteTransaction";
    public static final String TRANSACTION_ACTION_SHOW_MANAGE_PAGE = "GET/transactionManage";
    public static final String TRANSACTION_ACTION_SHOW_USER_TRANSACTION_PAGE = "GET/userTransaction";

    public static final String CART_ACTION_ADD_GAME = "GET/addToCart";
    public static final String CART_ACTION_RECOUNT = "POST/recountCart";
    public static final String CART_ACTION_BUY = "GET/buyCart";
    public static final String CART_ACTION_DELETE_GAME = "GET/gameInTransaction/delete";
    public static final String CART_ACTION_CLEAR = "GET/clearCart";
    public static final String CART_ACTION_SHOW_CART = "GET/cart";
    public static final String CART_ACTION_ADD = "POST/addToCart";

    public static final String GAME_ACTION_SHOW = "GET/game";
    public static final String GAME_ACTION_EDIT_PAGE = "POST/editGame";
    public static final String GAME_ACTION_DELETE = "GET/deleteGame";
    public static final String GAME_ACTION_ADD = "POST/addGame";
    public static final String GAME_ACTION_SHOW_CATALOG = "GET/catalog";
    public static final String GAME_ACTION_SHOW_MANAGE_PAGE = "GET/gameManage";
    public static final String GAME_ACTION_SHOW_ADD_GAME_PAGE = "GET/addGame";
    public static final String GAME_ACTION_SHOW_EDIT_GAME_PAGE = "GET/editGame";

    public static final String PUBLISHER_ACTION_EDIT = "POST/editPublisher";
    public static final String PUBLISHER_ACTION_DELETE = "GET/deletePublisher";
    public static final String PUBLISHER_ACTION_ADD = "POST/addPublisher";
    public static final String PUBLISHER_ACTION_SHOW = "GET/publisher";
    public static final String PUBLISHER_ACTION_SHOW_EDIT_PAGE = "GET/editPublisher";
    public static final String PUBLISHER_ACTION_SHOW_MANAGE_PAGE = "GET/publishers";

    public static final String LANGUAGE_ACTION_SET = "GET/locale";

    private static final Map<String, Action> actions;

    static {
        actions = new HashMap<>();
        actions.put(USER_ACTION_REGISTRATION, new UserRegistrationAction());
        actions.put(USER_ACTION_LOGIN, new LoginAction());
        actions.put(USER_ACTION_LOGOUT, new LogoutAction());
        actions.put(USER_ACTION_SHOW_REGISTRATION_PAGE, new ShowRegisterPageAction());
        actions.put(USER_ACTION_EDIT_PROFILE, new EditProfileAction());
        actions.put(USER_ACTION_EDIT_BALANCE, new EditBalanceAction());
        actions.put(USER_ACTION_DELETE_USER, new DeleteUserAction());
        actions.put(USER_ACTION_CHANGE_PASSWORD, new ChangeTransactionStatusAction());
        actions.put(USER_ACTION_SHOW_CHANGE_PASSWORD, new ShowPageAction(Constant.CHANGE_PASSWORD));
        actions.put(USER_ACTION_SHOW_EDIT_BALANCE_PAGE, new ShowEditBalancePageAction());
        actions.put(USER_ACTION_SHOW_HOME_PAGE, new ShowHomePageAction());
        actions.put(USER_ACTION_SHOW_PROFILE_PAGE, new ShowPageAction(Constant.USER_PROFILE));
        actions.put(USER_ACTION_SHOW_LIST_PAGE, new ShowUsersListPageAction());

        actions.put(TRANSACTION_ACTION_CHANGE_STATUS, new ChangeTransactionStatusAction());
        actions.put(TRANSACTION_ACTION_DELETE, new DeleteTransactionAction());
        actions.put(TRANSACTION_ACTION_SHOW_PAGE, new ShowTransactionAction());
        actions.put(TRANSACTION_ACTION_SHOW_MANAGE_PAGE, new ShowTransactionManagePageAction());
        actions.put(TRANSACTION_ACTION_SHOW_USER_TRANSACTION_PAGE, new ShowUserTransactionPageAction());

        actions.put(CART_ACTION_ADD_GAME, new AddGameInCartAction());
        actions.put(CART_ACTION_RECOUNT, new RecountCartAction());
        actions.put(CART_ACTION_BUY, new BuyAction());
        actions.put(CART_ACTION_DELETE_GAME, new DeleteGameInTransactionAction());
        actions.put(CART_ACTION_CLEAR, new ClearCartAction());
        actions.put(CART_ACTION_SHOW_CART, new ShowPageAction(Constant.CART));
        actions.put(CART_ACTION_ADD, new AddGameInCartAction());

        actions.put(GAME_ACTION_SHOW, new ShowGameAction());
        actions.put(GAME_ACTION_ADD, new AddGameAction());
        actions.put(GAME_ACTION_DELETE, new DeleteGameAction());
        actions.put(GAME_ACTION_EDIT_PAGE, new EditGamePageAction());
        actions.put(GAME_ACTION_SHOW_CATALOG, new ShowGameCatalogAction());
        actions.put(GAME_ACTION_SHOW_MANAGE_PAGE, new ShowGameManagePageAction());
        actions.put(GAME_ACTION_SHOW_ADD_GAME_PAGE, new ShowAddGamePageAction());
        actions.put(GAME_ACTION_SHOW_EDIT_GAME_PAGE, new ShowEditGamePageAction());

        actions.put(PUBLISHER_ACTION_ADD, new AddPublisherAction());
        actions.put(PUBLISHER_ACTION_DELETE, new DeletePublisherAction());
        actions.put(PUBLISHER_ACTION_EDIT, new EditPublisherAction());
        actions.put(PUBLISHER_ACTION_SHOW, new ShowPublisherAction());
        actions.put(PUBLISHER_ACTION_SHOW_EDIT_PAGE, new ShowEditPublisherPageAction());
        actions.put(PUBLISHER_ACTION_SHOW_MANAGE_PAGE, new ShowPublisherManagePageAction());

        actions.put(LANGUAGE_ACTION_SET, new SetLanguageAction());

    }

    public static Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
