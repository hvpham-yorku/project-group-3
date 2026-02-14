package com.yupathbuilder.backend.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Authentication / user domain component: UserService.
 */

@Service
public class UserService {

    private static final Path USERS_FILE = Path.of("users.json"); // stored next to where you run the app
    private final ObjectMapper mapper = new ObjectMapper();
    private final PasswordEncoder encoder;

    private final Map<String, AppUser> usersByName = new ConcurrentHashMap<>();

    public UserService(PasswordEncoder encoder) {
        this.encoder = encoder;
        load();
        // Ensure at least one default user for demos
        usersByName.computeIfAbsent("student", u ->
                new AppUser("student", encoder.encode("password"), Set.of("ROLE_STUDENT")));
        save();
    }

    public Optional<AppUser> find(String username) {
        if (username == null) return Optional.empty();
        return Optional.ofNullable(usersByName.get(username.toLowerCase()));
    }

    public AppUser register(String username, String rawPassword) {
        String key = username.toLowerCase();
        if (usersByName.containsKey(key)) throw new IllegalArgumentException("Username already exists");
        AppUser user = new AppUser(username, encoder.encode(rawPassword), Set.of("ROLE_STUDENT"));
        usersByName.put(key, user);
        save();
        return user;
    }

    private synchronized void load() {
        try {
            if (!Files.exists(USERS_FILE)) return;
            Map<String, AppUser> read = mapper.readValue(Files.readString(USERS_FILE), new TypeReference<>() {});
            usersByName.clear();
            for (var e : read.entrySet()) usersByName.put(e.getKey(), e.getValue());
        } catch (Exception ignored) {
            // If file is corrupted, start fresh.
            usersByName.clear();
        }
    }

    private synchronized void save() {
        try {
            Map<String, AppUser> toWrite = new TreeMap<>(usersByName);
            mapper.writerWithDefaultPrettyPrinter().writeValue(USERS_FILE.toFile(), toWrite);
        } catch (Exception ignored) {}
    }
}
