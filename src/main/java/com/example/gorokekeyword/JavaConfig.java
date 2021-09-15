package com.example.gorokekeyword;

import com.example.gorokekeyword.model.calculatorModel;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JavaConfig {

    @Bean
    public APIController apicontroller()
    {
        return new APIController(restTemplate());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public List<calculatorModel> calculatorModel() { return new ArrayList<>();}

    @Bean
    public InsightSearch insightSearch() {return new InsightSearch();}

    @Bean
    public ExceptWord exceptWord() {return new ExceptWord();}

    @Bean
    public Category category() {return new Category();}

}
