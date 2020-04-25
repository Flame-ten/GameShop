package epam.andrew.pixel.action;

import epam.andrew.pixel.action.common.*;
import epam.andrew.pixel.action.game.*;
import epam.andrew.pixel.action.order.ClearCart;
import epam.andrew.pixel.action.order.DeleteOrder;
import epam.andrew.pixel.action.order.ShowCart;
import epam.andrew.pixel.action.order.ShowOrderPage;
import epam.andrew.pixel.action.user.*;

import java.util.HashMap;
import java.util.Map;

public final class ActionFactory {
    private static Map<String, Action> actions;

    static {
        actions = new HashMap<>();
        actions.put("POST/registration", new UserRegistration());
        actions.put("POST/login", new Login());
        actions.put("GET/logout", new Logout());
        actions.put("GET/deleteUser", new DeleteUser());
        actions.put("POST/editProfile", new EditUser());
        actions.put("GET/showProfile", new ShowProfile());
        actions.put("POST/changePassword", new ChangePassword());
        actions.put("POST/editUserAsAdmin", new EditUserAsAdmin());
        actions.put("GET/balance", new UserCash());
        actions.put("POST/refillBalance", new RefillBalance());
        actions.put("GET/userLibrary", new ShowUserLibrary());
        actions.put("GET/friends", new ShowFriendsPage());
        actions.put("POST/editFriends", new EditFriendList());
        actions.put("POST/addFriend", new AddFriend());

        actions.put("GET/home", new ShowHomePage());
        actions.put("GET/registration", new ShowRegisterPage());
        actions.put("GET/market", new ShowMarket());
        actions.put("GET/publisher", new ShowPublisherInfo());
        actions.put("GET/info", new ShowFaqPage());
        actions.put("/searchGame", new SearchGame());
        actions.put("GET/wishList", new ShowWishList());
        actions.put("POST/editWishList", new EditWishList());
        actions.put("GET/locale", new SetLocale());

        actions.put("GET/orders", new ShowOrderPage());
        actions.put("GET/deleteOrder", new DeleteOrder());
        actions.put("GET/cart", new ShowCart());
        actions.put("GET/clearCart", new ClearCart());

        actions.put("POST/addGame", new AddGame());
        actions.put("GET/deleteGame", new DeleteGame());
        actions.put("POST/editGame", new EditGame());
        actions.put("GET/gamePage", new ShowGamePage());
        actions.put("GET/catalog", new ShowCatalog());

    }

    public static Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
