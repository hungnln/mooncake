package com.hungnln.mooncake.dtos;

public class ShoppingCartItem {
    private Cake cake;
    private int quantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Cake cake, int quantity) {
        this.cake = cake;
        this.quantity = quantity;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
