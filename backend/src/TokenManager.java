import java.util.HashMap;
import java.util.UUID;

public class TokenManager {
    private static final HashMap<String, String> tokenStore = new HashMap<>();

    // Generate token using a unique identifier (e.g., email)
    public static String generateToken(String uniqueIdentifier) {
        String token = UUID.randomUUID().toString(); // Generate a unique token
        tokenStore.put(token, uniqueIdentifier);    // Map the token to the unique identifier
        return token;
    }

    public static boolean isValidToken(String token) {
        return tokenStore.containsKey(token);
    }

    public static String getUniqueIdentifierFromToken(String token) {
        return tokenStore.get(token); // Retrieve the unique identifier (e.g., email) from the token
    }

    public static String getTokenFromCookies(String cookies) {
        if (cookies == null) return null;
        for (String cookie : cookies.split(";")) {
            if (cookie.trim().startsWith("sessionToken=")) {
                return cookie.split("=")[1];
            }
        }
        return null;
    }
}
