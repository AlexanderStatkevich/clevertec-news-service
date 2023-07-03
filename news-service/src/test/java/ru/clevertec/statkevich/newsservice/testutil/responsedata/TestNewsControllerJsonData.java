package ru.clevertec.statkevich.newsservice.testutil.responsedata;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TestNewsControllerJsonData {

    public static String readApiFindByIdResponse() throws IOException {
        return load("__files/news_controller/api_find_by_id_response.json");
    }

    public static String readApiFindAllResponse() throws IOException {
        return load("__files/news_controller/api_find_all_response.json");
    }

    public static String readUpdateResponse() throws IOException {
        return load("__files/news_controller/api_update_response.json");
    }

    private static String load(String fileName) throws IOException {
        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(fileName);
        return IOUtils.toString(Objects.requireNonNull(systemResourceAsStream), "UTF-8");
    }

}
