package com.duncan.ai.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestRequest01 {

    @JsonProperty(required = true, value = "city")
    @JsonPropertyDescription("城市的名称,如果是中文汉字请先转化为汉语拼音，例如北京： beijing")
    private String city;

    @JsonProperty(required = true, value = "university")
    @JsonPropertyDescription("大学的信息")
    private University university;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class University {


        @JsonProperty(required = true, value = "name")
        @JsonPropertyDescription("大学的名称")
        private String name;


        @JsonProperty(required = true, value = "size")
        @JsonPropertyDescription("大学的规模")
        private String size;

    }
}
