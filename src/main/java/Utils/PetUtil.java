package Utils;

import java.util.Random;
import java.util.UUID;

public class PetUtil {

    private static Random random = new Random();
    // Generate random pet name
    public static String generateRandomPetName() {
        return "Pet_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static int generateRandomId() {
        return random.nextInt(1000) + 1;
    }

}
