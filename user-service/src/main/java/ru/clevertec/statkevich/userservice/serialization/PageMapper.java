package ru.clevertec.statkevich.userservice.serialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties({"sort", "pageable", "empty"})
@JsonPropertyOrder({
        "number",
        "size",
        "total_pages",
        "total_elements",
        "first",
        "number_of_elements",
        "last",
        "content"
})
public interface PageMapper {
    @JsonProperty("total_pages")
    int getTotalPages();

    @JsonProperty("total_elements")
    long getTotalElements();

    @JsonProperty("number_of_elements")
    int getNumberOfElements();
}
