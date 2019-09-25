package io.pivotal.data.pizzastoreapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.geode.config.annotation.UseMemberName;

import io.pivotal.data.pizzastoreapi.model.Pizza;
import io.pivotal.data.pizzastoreapi.repo.PizzaRepository;

@Configuration
@EnableEntityDefinedRegions(basePackageClasses = Pizza.class)
@EnableGemfireRepositories(basePackageClasses = PizzaRepository.class)
@UseMemberName("PizzaStore-API")
@Profile("local")
public class CloudCacheLocalConfig {

}
