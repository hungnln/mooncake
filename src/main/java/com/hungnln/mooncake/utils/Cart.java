package com.hungnln.mooncake.utils;

import com.hungnln.mooncake.dtos.ShoppingCartItem;

import java.util.List;

public class Cart {

    public static int isExistCake(String cakeID, List<ShoppingCartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getCake().getCakeID().equalsIgnoreCase(cakeID)) {
                return i;
            }
        }
        return -1;
    }
}
