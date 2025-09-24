package model;

public class SessionModel {
    private static String username;
    private static boolean isAdmin;

    public static void setUser(String user, boolean admin) {
        username = user;
        isAdmin = admin;
    }

    public static String getUsername() {
        return username;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void clear() {
        username = null;
        isAdmin = false;
    }
}
