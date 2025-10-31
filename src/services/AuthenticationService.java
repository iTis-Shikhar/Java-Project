package services;

import models.User;
import java.util.List;
import java.util.Optional;

public class AuthenticationService {

    public static User login(String username, String password, List<User> users) {
        Optional<User> foundUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();

        return foundUser.orElse(null);
    }

    public static User signUp(String username, String password, List<User> users) {
        boolean usernameExists = users.stream().anyMatch(user -> user.getUsername().equals(username));
        if (usernameExists) {
            System.out.println("Username already exists. Please try another one.");
            return null;
        }

        int newUserId = users.stream()
                             .mapToInt(User::getUserId)
                             .max()
                             .orElse(100); 

        User newUser = new User(newUserId + 1, username, password);
        users.add(newUser); 
        return newUser;
    }
}
