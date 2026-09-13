package com.duncan.ai.service;

import com.duncan.ai.model.TestRequest01;
import com.duncan.ai.model.TestRequest02;
import com.duncan.ai.model.TestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {

    @Tool(name = "获取学生成绩的工具")
    public TestResponse getScoreByStudentIdAndUniversity(TestRequest01 request01, TestRequest02 request02) {
       log.info("学生的姓名是: {}, 学生的学校是： {}", request02.getStudentName(), request01.getUniversity() );


       TestResponse testResponse = new TestResponse();
       testResponse.setScore(100.0F);
       testResponse.setSubject("Math");
       return testResponse;
    }
}
