package com.example.cocktails.web.controller;

import com.example.cocktails.web.remote.*;
import jakarta.annotation.*;
import jakarta.enterprise.concurrent.*;
import jakarta.faces.view.*;
import jakarta.inject.*;
import org.eclipse.microprofile.rest.client.inject.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static java.util.stream.Collectors.*;

@Named
@ViewScoped
public class RemoteMilkBean implements Serializable {

    private static final String QUERY = "Milk";
    private static final String MILK_NAME = "Milch";

    @Inject
    @RestClient
    private RemoteCocktailsClient remoteClient;

    @Resource
    private ManagedExecutorService executor;

    private RemoteMilkAnalysisResult synchronousResult;
    private RemoteMilkAnalysisResult asynchronousResult;

    public void loadSynchronously() {
        long start = System.currentTimeMillis();

        List<RemoteCocktailSummary> hits = findMilkCocktails();
        List<String> cocktailNames = hits.stream()
                .map(RemoteCocktailSummary::getName)
                .collect(toList());

        int milkAmount = 0;
        for (RemoteCocktailSummary hit : hits) {
            milkAmount += sumMilkAmount(remoteClient.getCocktail(hit.getId()));
        }

        long durationMillis = System.currentTimeMillis() - start;
        synchronousResult = new RemoteMilkAnalysisResult("Synchron", QUERY, hits.size(), milkAmount, durationMillis, cocktailNames);
    }

    public void loadAsynchronously() {
        long start = System.currentTimeMillis();

        List<RemoteCocktailSummary> hits = findMilkCocktails();
        List<String> cocktailNames = hits.stream()
                .map(RemoteCocktailSummary::getName)
                .collect(toList());

        List<CompletableFuture<RemoteCocktailDetails>> detailFutures = hits.stream()
                .map(hit -> CompletableFuture.supplyAsync(() -> remoteClient.getCocktail(hit.getId()), executor))
                .collect(toList());

        CompletableFuture.allOf(detailFutures.toArray(new CompletableFuture[0])).join();

        int milkAmount = detailFutures.stream()
                .map(CompletableFuture::join)
                .mapToInt(this::sumMilkAmount)
                .sum();

        long durationMillis = System.currentTimeMillis() - start;
        asynchronousResult = new RemoteMilkAnalysisResult("Asynchron", QUERY, hits.size(), milkAmount, durationMillis, cocktailNames);
    }

    public RemoteMilkAnalysisResult getSynchronousResult() {
        return synchronousResult;
    }

    public RemoteMilkAnalysisResult getAsynchronousResult() {
        return asynchronousResult;
    }

    private List<RemoteCocktailSummary> findMilkCocktails() {
        return remoteClient.getAllCocktails().stream()
                .filter(Objects::nonNull)
                .filter(cocktail -> cocktail.getName() != null)
                .filter(cocktail -> cocktail.getName().contains(QUERY))
                .collect(toList());
    }

    private int sumMilkAmount(RemoteCocktailDetails details) {
        if (details == null || details.getInstructions() == null) {
            return 0;
        }

        return details.getInstructions().stream()
                .filter(Objects::nonNull)
                .filter(instruction -> instruction.getIngredient() != null)
                .filter(instruction -> MILK_NAME.equals(instruction.getIngredient().getName()))
                .mapToInt(RemoteInstruction::getAmount)
                .sum();
    }
}
