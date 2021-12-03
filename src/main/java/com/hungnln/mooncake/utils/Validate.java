package com.hungnln.mooncake.utils;

public class Validate {
    private static final String USER_PASSWORD_VALIDATE = "^[(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%?=*&])]{8,30}$";
    private static final String USER_ID_VALIDATE = "^[[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9])[a-zA-Z0-9]]{8,30}$";
    private static final String USER_NAME_VALIDATE = "^[[([A-Z][a-z]*((\\s)))+[A-Z][a-z]][\\p{L}-]]{1,30}$";
    private static final String USER_PHONE_VALIDATE = "^([0])\\d{9}$";
    private static final String USER_ADDRESS_VALIDATE = "^[(\\w)(\\d)(\\s)(.,@)[\\p{L}-]]{10,90}$";
    private static final String CAKE_NAME_VALIDATE = "^[[([A-Z][a-z]*((\\s)))+[A-Z][a-z]][\\p{L}-]]{1,30}$";
    private static final String CAKE_DESCRIPTION_VALIDATE = "^[(\\w)(\\d)(\\s)(.,@)[\\p{L}-]]{10,200}$";

    public Validate() {
    }

    public static boolean checkUserIDValidate(String ID) {
        return ID.matches(USER_ID_VALIDATE);
    }

    public static boolean checkUserPasswordValidate(String Password) {
        return Password.matches(USER_PASSWORD_VALIDATE);
    }

    public static boolean checkUserPhoneValidate(String phoneNumber) {
        return phoneNumber.matches(USER_PHONE_VALIDATE);
    }

    public static boolean checkUserNameValidate(String userName) {
        return userName.matches(USER_NAME_VALIDATE);
    }

    public static boolean checkUserAddressValidate(String userAddress) {
        return userAddress.matches(USER_ADDRESS_VALIDATE);
    }

    public static boolean checkCakeDescriptionValidate(String cakeDescription) {
        return cakeDescription.matches(CAKE_DESCRIPTION_VALIDATE);
    }

    public static boolean checkCakeNameValidate(String cakeName) {
        return cakeName.matches(CAKE_NAME_VALIDATE);
    }


}
