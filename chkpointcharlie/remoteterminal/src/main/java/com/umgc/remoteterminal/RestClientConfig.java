package com.umgc.remoteterminal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

//        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
//        if (CollectionUtils.isEmpty(interceptors)) {
//            interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//        }
//        interceptors.add(new RestTemplateHeaderModifierInterceptor());
//        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}