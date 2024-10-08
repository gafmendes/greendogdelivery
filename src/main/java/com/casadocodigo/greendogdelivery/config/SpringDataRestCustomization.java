package com.casadocodigo.greendogdelivery.config;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.casadocodigo.greendogdelivery.domain.Item;
import com.casadocodigo.greendogdelivery.repository.ClienteRepository;

@Component
public class SpringDataRestCustomization 
implements RepositoryRestConfigurer 
{

	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Item.class,ClienteRepository.class);
	}
}
