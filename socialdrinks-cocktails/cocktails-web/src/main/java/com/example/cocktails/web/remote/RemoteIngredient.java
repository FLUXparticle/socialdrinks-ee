package com.example.cocktails.web.remote;

public class RemoteIngredient {

    private long id;
    private String name;

    public RemoteIngredient() {
        // JSON-B
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
