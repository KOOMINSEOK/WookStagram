package Manager;
import java.util.ArrayList;
import java.util.List;

import User.User;

public class LoginManager {
    private static LoginManager instance;
    private User loggedInUser;
    private List<Runnable> observers = new ArrayList<>(); 
    private boolean loggedIn = false;

    private LoginManager() {}

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }
    public void logIn(User user) {
    	setLoggedInUser(user);
        loggedIn = true;
        notifyObservers();
    }
    public void logout() {
        loggedInUser = null;
        loggedIn = false;
        notifyObservers();
    }
    public void updateLoggedInUser(User updatedUser) {
        this.loggedInUser = updatedUser;
        notifyObservers();
    }
    public void addObserver(Runnable observer) {
        observers.add(observer);
    }
    
    public void notifyObservers() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }
}
