package com.example.cocktails.web.remote;

import java.util.*;

public class RemoteCocktailDetails {

    private long id;
    private String name;
    private List<RemoteInstruction> instructions;

    public RemoteCocktailDetails() {
        // JSON-B
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RemoteInstruction> getInstructions() {
        return instructions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructions(List<RemoteInstruction> instructions) {
        this.instructions = instructions;
    }
}
