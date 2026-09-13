package com.duncan.ai.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestResponse {
    @JsonProperty(required = true, value = "subject")
    @JsonPropertyDescription("学生学科")
    private String subject;


    @JsonProperty(required = true, value = "score")
    @JsonPropertyDescription("学生的成绩")
    private Float score;
}
