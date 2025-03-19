package com.app.ecommerce.config;

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppRestTemplate {
	  @Bean
	    RestTemplate sslRestTemplate(RestTemplateBuilder builder, SslBundles sslBundles) {
	        SslBundle sslBundle = sslBundles.getBundle("mybundle");
	 
	        return builder.sslBundle(sslBundle).build();
	  }

}
