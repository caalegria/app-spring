package com.segurosbolivar.finita.aplicacion;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

public class ServletInitializer extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
		
		return app.sources(AplicaionApplication.class);
	}
	

}
