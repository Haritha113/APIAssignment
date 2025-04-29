package Utils;

import java.util.UUID;

public class PetUtil {

    // Generate random pet name
    public static String generateRandomPetName() {
        return "Pet_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
