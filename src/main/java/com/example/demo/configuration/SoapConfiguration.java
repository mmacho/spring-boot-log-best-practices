package com.example.demo.configuration;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadRootSmartSoapEndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import com.example.demo.endpoint.CountryEndpoint;
import com.example.demo.interceptor.soap.CustomEndpointInterceptor;
import com.example.demo.interceptor.soap.GlobalEndpointInterceptor;
import com.example.demo.interceptor.soap.SoapClientInterceptor;

@EnableWs
@Configuration
@ComponentScan(basePackages = { "com.example.demo.endpoint", "com.example.demo.interceptor.soap" })
public class SoapConfiguration extends WsConfigurerAdapter {

	GlobalEndpointInterceptor globalEndpointInterceptor;

	CustomEndpointInterceptor customEndpointInterceptor;

	public SoapConfiguration(GlobalEndpointInterceptor globalEndpointInterceptor,
			CustomEndpointInterceptor customEndpointInterceptor) {
		this.globalEndpointInterceptor = globalEndpointInterceptor;
		this.customEndpointInterceptor = customEndpointInterceptor;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		// register global interceptor
		interceptors.add(globalEndpointInterceptor);

		// register endpoint specific interceptor
		interceptors.add(new PayloadRootSmartSoapEndpointInterceptor(customEndpointInterceptor,
				CountryEndpoint.NAMESPACE, CountryEndpoint.COUNTRY_REQUEST_LOCAL_PART));
	}

	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(Boolean.TRUE);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean(name = "countries")
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace(CountryEndpoint.NAMESPACE);
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}

	@Configuration
	public static class ClientSoapConfiguration {

		private static final String DEFAULT_URI = "http://localhost:8081/ws";

		@Bean
		Jaxb2Marshaller marshaller() {
			Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
			marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
			return marshaller;
		}

		@Bean
		WebServiceTemplate webServiceTemplate(WebServiceTemplateBuilder builder) {
			return builder.setDefaultUri(DEFAULT_URI).setMarshaller(marshaller()).setUnmarshaller(marshaller())
					.interceptors(new SoapClientInterceptor()).messageSenders(new HttpWebServiceMessageSenderBuilder()
							.setConnectTimeout(Duration.ofSeconds(1)).setReadTimeout(Duration.ofSeconds(10)).build())
					.build();
		}

	}

}