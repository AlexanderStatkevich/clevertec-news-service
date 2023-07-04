package ru.clevertec.statkevich.newsservice.testutil.responsedata;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TestSecurityJsonData {


    public static String readNewsUserAuthorityDto() throws IOException {
        return load("__files/data/news_controller_user_authority_dto.json");
    }

    public static String readCommentUserAuthorityDto() throws IOException {
        return load("__files/data/comment_controller_user_authority_dto.json");
    }

    private static String load(String fileName) throws IOException {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(fileName);
        return IOUtils.toString(Objects.requireNonNull(systemResourceAsStream), "UTF-8");
    }
}
