package Utils;

import java.util.Random;
import java.util.UUID;

public class UserUtil {

    private static Random random = new Random();

    public static int generateRandomId() {
        return random.nextInt(1000) + 1;
    }

    public static String generateRandomUsername() {
        return "user_" + random.nextInt(10000);
    }

    public static String generateRandomFirstName() {
        return "FirstName_" + random.nextInt(1000);
    }

    public static String generateRandomLastName() {
        return "LastName_" + random.nextInt(1000);
    }

    public static String generateRandomEmail() {
        return "user" + random.nextInt(10000) + "@example.com";
    }

    public static String generateRandomPassword() {
        return "Pass" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateRandomPhone() {
        return "9" + (random.nextInt(900000000) + 100000000); // 10-digit number
    }

    public static int generateRandomUserStatus() {
        return random.nextInt(2); // 0 or 1
    }
}
