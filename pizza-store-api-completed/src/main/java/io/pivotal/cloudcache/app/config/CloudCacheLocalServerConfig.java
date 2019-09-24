package io.pivotal.cloudcache.app.config;

import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.config.annotation.EnablePdx;

@CacheServerApplication(name = "BootGemFireServerApplication")
@EnableLocator
@EnableManager
@EnablePdx(readSerialized=true)
@Profile("server")
@Configuration
public class CloudCacheLocalServerConfig {
	
	@Bean("Name")
	public PartitionedRegionFactoryBean<Object, Object> nameRegion(GemFireCache gemfireCache) {

		PartitionedRegionFactoryBean<Object, Object> nameRegion = new PartitionedRegionFactoryBean<>();

		nameRegion.setCache(gemfireCache);
		nameRegion.setClose(false);
		nameRegion.setPersistent(false);

		return nameRegion;
	}

	@Bean("Pizza")
	public PartitionedRegionFactoryBean<Object, Object> pizzaRegion(GemFireCache gemfireCache) {

		PartitionedRegionFactoryBean<Object, Object> pizzaRegion = new PartitionedRegionFactoryBean<>();

		pizzaRegion.setCache(gemfireCache);
		pizzaRegion.setClose(false);
		pizzaRegion.setPersistent(false);

		return pizzaRegion;
	}
}
