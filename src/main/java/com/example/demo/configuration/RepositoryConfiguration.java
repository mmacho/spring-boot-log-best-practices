package com.example.demo.configuration;

import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.ClientHttpRequestFactorySupplier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.example.demo.interceptor.rest.RequestResponseLoggingInterceptor;
import com.example.demo.interceptor.rest.RestTemplateHeaderModifierInterceptor;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;

/**
 * Revisar
 * https://medium.com/@cizek.jy/spring-resttemplate-why-the-set-timeout-does-not-work-b75aaee076a3
 * https://tech.asimio.net/2016/12/27/Troubleshooting-Spring-RestTemplate-Requests-Timeout.html
 */
@Configuration
@EnableFeignClients(basePackages = { "com.example.demo.client" })
public class RepositoryConfiguration {

	@Value("${app.timeout:5000}")
	private Integer timeout;

	@Bean
	// https://github.com/swagger-api/swagger-codegen/issues/6375
	RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) throws GeneralSecurityException {
		TrustStrategy trust = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trust).build();
		SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", sslSf).register("http", new PlainConnectionSocketFactory()).build();
		BasicHttpClientConnectionManager manager = new BasicHttpClientConnectionManager(registry);
		CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslSf).setConnectionManager(manager)
				.setDefaultRequestConfig(config()).build();
		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(
				new HttpComponentsClientHttpRequestFactory(client));

		// con interceptor
		// return restTemplateBuilder.interceptors(new
		// RequestResponseLoggingInterceptor()).requestFactory(() -> factory)
		// .build();

		return restTemplateBuilder.requestFactory(() -> factory).build();
	}

	private RequestConfig config() {
		return RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout)
				.setSocketTimeout(timeout).build();
	}

	// @Bean
	RestTemplate restTemplate3(RestTemplateBuilder builder) {
		return builder.interceptors(new RestTemplateHeaderModifierInterceptor()).build();
	}

	// Da problemas al pasar por el interceptor
	// @Bean
	ClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
		poolingConnectionManager.setMaxTotal(50);
		poolingConnectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(poolingConnectionManager).build();

		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				client);
		clientHttpRequestFactory.setConnectionRequestTimeout(1000);
		clientHttpRequestFactory.setConnectTimeout(1000);
		clientHttpRequestFactory.setReadTimeout(5000);

		return new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
	}

	// @Bean
	RestTemplate restTemplate2(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.messageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()))
				.interceptors(new RequestResponseLoggingInterceptor()).setConnectTimeout(Duration.ofSeconds(1))
				.setReadTimeout(Duration.ofSeconds(5)).requestFactory(new ClientHttpRequestFactorySupplier()).build();
	}

	// Da problemas al pasar por el interceptor

	@Configuration
	@EnableCaching
	@EnableJpaRepositories(basePackages = "com.example.demo.repository",  repositoryBaseClass = BaseJpaRepositoryImpl.class)
	@ComponentScan(basePackages = { "com.example.demo.repository" })
	@EntityScan("com.example.demo.domain")
	@EnableTransactionManagement
	public static class DataBaseConfiguration {

	}
}
