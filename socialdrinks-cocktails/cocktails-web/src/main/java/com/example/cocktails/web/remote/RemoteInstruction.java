package com.example.cocktails.web.remote;

public class RemoteInstruction {

    private int amount;
    private RemoteIngredient ingredient;

    public RemoteInstruction() {
        // JSON-B
    }

    public int getAmount() {
        return amount;
    }

    public RemoteIngredient getIngredient() {
        return ingredient;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIngredient(RemoteIngredient ingredient) {
        this.ingredient = ingredient;
    }
}
