package ru.clevertec.statkevich.emailservice.service;

import java.util.Random;

public final class CodeGenerationUtil {

    public static final int LEFT_LIMIT = 48;
    public static final int RIGHT_LIMIT = 122;
    public static final int TARGET_STRING_LENGTH = 10;
    public static final int FIRST_EXCLUDE_RANGE_LEST_LIMIT = 57;
    public static final int FIRST_EXCLUDE_RANGE_RIGHT_LIMIT = 65;
    public static final int SECOND_EXCLUDE_RANGE_LEST_LIMIT = 90;
    public static final int SECOND_EXCLUDE_RANGE_RIGHT_LIMIT = 97;

    private CodeGenerationUtil() {
        throw new IllegalStateException("Util class");
    }

    public static String generateCode() {
        Random random = new Random();
        return random.ints(LEFT_LIMIT, RIGHT_LIMIT + 1)
                .filter(i -> (i <= FIRST_EXCLUDE_RANGE_LEST_LIMIT || i >= FIRST_EXCLUDE_RANGE_RIGHT_LIMIT)
                        && (i <= SECOND_EXCLUDE_RANGE_LEST_LIMIT || i >= SECOND_EXCLUDE_RANGE_RIGHT_LIMIT))
                .limit(TARGET_STRING_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
