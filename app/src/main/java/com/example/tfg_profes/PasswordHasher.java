package com.example.tfg_profes;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    private static final int LOG_ROUNDS = 10; // Número de rondas de hashing

    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        boolean passwordMatches = BCrypt.checkpw(password, hashedPassword);
        return passwordMatches;
    }
}

