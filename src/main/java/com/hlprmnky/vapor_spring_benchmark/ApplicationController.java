package com.hlprmnky.vapor_spring_benchmark;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    private final AtomicLong counter = new AtomicLong();
    private final Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/json")
    public Json json() {
        return new Json(counter.incrementAndGet(), Arrays.asList(1, 2, 3),
                ImmutableMap.of("one", 1, "two", 2, "three", 3),
                "test", 42, 3.14);
    }

    @RequestMapping("/plaintext")
    public String plaintext() {
        return "Hello, World!";
    }

    @RequestMapping("/sqlite-fetch")
    public User sqliteFetch() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.get(random.nextInt(allUsers.size()));
    }
}
