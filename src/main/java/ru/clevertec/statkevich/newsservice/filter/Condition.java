package ru.clevertec.statkevich.newsservice.filter;


public record Condition(

        Type type,

        Comparison comparison,

        Object value,

        String field
) {
}
