package services;

import models.User;
import java.util.List;
import java.util.Optional;

public class AuthenticationService {

    /**
     * Attempts to log in a user.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @param users The list of all registered users.
     * @return The User object if login is successful, otherwise null.
     */
    public static User login(String username, String password, List<User> users) {
        // This is a modern Java way to find an item in a list.
        Optional<User> foundUser = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();

        return foundUser.orElse(null); // Return the user if found, otherwise return null
    }

    /**
     * Signs up a new user.
     * @param username The desired new username.
     * @param password The desired new password.
     * @param users The list of all registered users (will be modified).
     * @return The newly created User object, or null if the username already exists.
     */
    public static User signUp(String username, String password, List<User> users) {
        // Check if the username is already taken.
        boolean usernameExists = users.stream().anyMatch(user -> user.getUsername().equals(username));

        if (usernameExists) {
            System.out.println("Username already exists. Please try another one.");
            return null;
        }

        // Find the highest existing user ID to generate a new one.
        int newUserId = users.stream()
                             .mapToInt(User::getUserId)
                             .max()
                             .orElse(100); // Start from 101 if no users exist

        User newUser = new User(newUserId + 1, username, password);
        users.add(newUser); // Add the new user to our list
        return newUser;
    }
}