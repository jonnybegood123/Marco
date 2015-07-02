package com.jonathanrobins.marco;

/**
 * Created by Jonathan on 7/1/2015.
 */
public class CurrentUser {
    private static String username;
    private static String coordinates;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        CurrentUser.username = username;
    }

    public static String getCoordinates() {
        return coordinates;
    }

    public static void setCoordinates(String coordinates) {
        CurrentUser.coordinates = coordinates;
    }
}
