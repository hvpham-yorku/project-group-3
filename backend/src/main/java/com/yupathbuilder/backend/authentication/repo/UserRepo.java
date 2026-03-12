package com.yupathbuilder.backend.authentication.repo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupathbuilder.backend.authentication.model.User;

import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepo {

    private static final Path USERS_FILE = Path.of("users.json");

    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public UserRepo() {
        load();
    }

    public Optional<User> find(String username) {
        if (username == null) return Optional.empty();
        return Optional.ofNullable(users.get(username.toLowerCase()));
    }

    public boolean exists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    public void save(User user) {
        users.put(user.getUsername(), user);
        persist();
    }

    /*
     * Load users from storage
     */
    private synchronized void load() {

        try {

            if (!Files.exists(USERS_FILE)) return;

            Map<String, User> read =
                    mapper.readValue(
                            Files.readString(USERS_FILE),
                            new TypeReference<>() {}
                    );

            users.clear();
            users.putAll(read);

        } catch (Exception ignored) {
            users.clear();
        }
    }

    /*
     * Save users to storage
     */
    private synchronized void persist() {

        try {

            Map<String, User> sorted = new TreeMap<>(users);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(USERS_FILE.toFile(), sorted);

        } catch (Exception ignored) {}
    }
}