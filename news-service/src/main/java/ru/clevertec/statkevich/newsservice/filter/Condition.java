package ru.clevertec.statkevich.newsservice.filter;


public record Condition(

        Comparison comparison,

        Object value,

        String field
) {
}
