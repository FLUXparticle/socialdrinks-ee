package com.example.cocktails.web.remote;

import java.io.*;
import java.util.*;

public class RemoteMilkAnalysisResult implements Serializable {

    private String mode;
    private String query;
    private int hitCount;
    private int milkAmountCL;
    private long durationMillis;
    private List<String> cocktailNames;

    public RemoteMilkAnalysisResult(String mode, String query, int hitCount, int milkAmountCL, long durationMillis, List<String> cocktailNames) {
        this.mode = mode;
        this.query = query;
        this.hitCount = hitCount;
        this.milkAmountCL = milkAmountCL;
        this.durationMillis = durationMillis;
        this.cocktailNames = cocktailNames;
    }

    public String getMode() {
        return mode;
    }

    public String getQuery() {
        return query;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMilkAmountCL() {
        return milkAmountCL;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public List<String> getCocktailNames() {
        return cocktailNames;
    }
}
