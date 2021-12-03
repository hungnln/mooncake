package com.hungnln.mooncake.utils;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author SE140018
 */
public class StaticVariable {
    private static final int ADMIN = 2;
    private static final int USER = 1;
    private static final int GUEST = 0;
    private static final int CAKE_QUANTITY_DEFAULT = 1;
    private static final String LOCAL_PATH_IMG = "D:\\Netbean\\PRJ301\\SE140018_LAB1\\web\\images\\";
    private static final String DEFAULT_GOOGLE_PASSWORD = "1";
    private static final String DEFAULT_GOOGLE_PHONENUMBER = "0000000000";
    private static final String DEFAULT_GOOGLE_ADDRESS = "No Adress";
    public StaticVariable() {
    }
    
    public static int getAdminRole(){
        return ADMIN;
    }
    public static int getUserRole(){
        return USER;
    }
    public static int getGuestRole(){
        return GUEST;
    }
    public static int getDefaultQuantity(){
        return CAKE_QUANTITY_DEFAULT;
    }
    public static String getLocalPathImage(){
         return LOCAL_PATH_IMG;
    }
    public static String getDefaultGooglePassword(){
        return DEFAULT_GOOGLE_PASSWORD;
    }
     public static String getDefaultGoogleAddress(){
        return DEFAULT_GOOGLE_ADDRESS;
    }
      public static String getDefaultGooglePhone(){
        return DEFAULT_GOOGLE_PHONENUMBER;
    }
}
