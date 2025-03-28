package com.app.ecommerce.config;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.TlsSocketStrategy;
import org.apache.hc.core5.http.nio.ssl.TlsStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class AppRestClient {
	
	private final String base64Certificate;
	
	AppRestClient(@Value("${BASE_64_CERTIFICATE}") String base64Certificate){
		this.base64Certificate = base64Certificate;
	}
	
	@Bean
	RestClient restClient() {
		try {
			TlsStrategy tlsStrategy = ClientTlsStrategyBuilder.create()
				.setSslContext(createHttpClientFromBase64(base64Certificate, ""))
				.setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.build();
			CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager
					(PoolingHttpClientConnectionManagerBuilder.create()
						.setTlsSocketStrategy((TlsSocketStrategy) tlsStrategy)
						.build()
				).build();
			return RestClient.builder().requestFactory(new HttpComponentsClientHttpRequestFactory(httpClient))
			.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static SSLContext createHttpClientFromBase64(String base64P12, String password) throws Exception {
		byte[] p12Bytes = Base64.getDecoder().decode(base64P12);
		    
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		try (ByteArrayInputStream bis = new ByteArrayInputStream(p12Bytes)) {
			keyStore.load(bis, password.toCharArray());
		}
		    
		return SSLContextBuilder.create()
			.loadKeyMaterial(keyStore, password.toCharArray())
			.build();
	}
}




