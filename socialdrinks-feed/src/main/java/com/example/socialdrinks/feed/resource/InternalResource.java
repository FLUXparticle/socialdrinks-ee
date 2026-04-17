package com.example.socialdrinks.feed.resource;

import com.example.socialdrinks.common.*;

import java.util.*;

public class InternalResource implements RatingInterface {

    public Map<String, String> greeting() {
        return Map.of("greeting", "Hallo");
    }

    @Override
    public Integer getRate(Long id) {
        return (int) (Math.random() * 5 + 1);
    }

}
