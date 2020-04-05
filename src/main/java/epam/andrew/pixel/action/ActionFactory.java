package epam.andrew.pixel.action;

import epam.andrew.pixel.action.user.DeleteUserAction;
import epam.andrew.pixel.action.user.LoginAction;
import epam.andrew.pixel.action.user.LogoutAction;
import epam.andrew.pixel.action.user.UserRegistrationAction;

import java.util.HashMap;
import java.util.Map;

public final class ActionFactory {
    private static Map<String, Action> actions;

    static {
        actions = new HashMap<>();
        actions.put("/registration", new UserRegistrationAction());
        actions.put("/Login", new LoginAction());
        actions.put("/Logout", new LogoutAction());
        actions.put("/delete user", new DeleteUserAction());
    }

    public static Action getAction(String actionName) {
        return actions.get(actionName);
    }
}
