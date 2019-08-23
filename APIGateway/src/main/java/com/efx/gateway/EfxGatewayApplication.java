package com.efx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.efx.gateway.config.PreFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy	
public class EfxGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EfxGatewayApplication.class, args);
	}

	@Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
	
	
}
