package Constants;

import java.util.Arrays;
import java.util.List;

public class PetStoreConstants {

    //TO be defined in ENUM later
    public static final String PLACED = "placed";
    public static final String APPROVED = "approved";
    public static final String DELIVERED = "delivered";
    public static final int INVALID_ORDER_ID = 4565;
    public static final List<String> EXPECTED_STATUSES = Arrays
            .asList("available", "pending", "sold","unavailable","invalid","Not Available");
}
