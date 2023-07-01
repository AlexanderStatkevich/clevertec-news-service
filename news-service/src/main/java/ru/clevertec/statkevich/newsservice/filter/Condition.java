package ru.clevertec.statkevich.newsservice.filter;

/**
 * @param comparison value from enum to compare filtering value with value in storage
 * @param value      comparing filter value
 * @param field      name of entity field
 */
public record Condition(

        Comparison comparison,

        Object value,

        String field
) {
}
