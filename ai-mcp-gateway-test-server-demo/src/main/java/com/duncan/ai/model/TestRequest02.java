package com.duncan.ai.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestRequest02 {

    @JsonProperty(required = true, value = "name")
    @JsonPropertyDescription("学生的姓名")
    private String studentName;


    @JsonProperty(required = true, value = "student_id")
    @JsonPropertyDescription("学生的学号")
    private String stNo;
}
