package Constants;

public class EndPoints {

    // PET
    public static final String CREATE_PET = "/pet";
    public static final String GET_PET_BY_ID = "/pet/{petId}";
    public static final String UPDATE_PET = "/pet";
    public static final String UPDATE_PET_WITH_FORM_DATA = "/pet/{petId}";
    public static final String UPDATE_PET_PHOTO = "/pet/{petId}/uploadImage";
    public static final String DELETE_PET_BY_ID = "/pet/{petId}";
    public static final String FIND_BY_STATUS = "/pet/findByStatus";

    // STORE
    public static final String PLACE_ORDER = "/store/order";
    public static final String GET_ORDER_BY_ID = "/store/order/{orderId}";
    public static final String DELETE_ORDER_BY_ID = "/store/order/{orderId}";
    public static final String GET_INVENTORY = "/store/inventory";

    // USER
    public static final String CREATE_USER = "/user";
    public static final String CREATE_USERS_WITH_ARRAY = "/user/createWithArray";
    public static final String GET_USER_BY_USERNAME = "/user/{username}";
    public static final String DELETE_USER = "/user/{username}";
    public static final String LOGIN_USER = "/user/login";
    public static final String LOGOUT_USER = "/user/logout";
    public static final String UPDATE_USER = "/user/{username}";

    //AUTHORIZATION
//    public static final String CREATE_TOKEN = "";  // not availble in this swagger

}
