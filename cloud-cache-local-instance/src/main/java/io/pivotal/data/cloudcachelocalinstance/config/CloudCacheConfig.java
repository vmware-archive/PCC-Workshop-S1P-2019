package io.pivotal.data.cloudcachelocalinstance.config;

import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.config.annotation.EnablePdx;

@CacheServerApplication(name = "CloudCacheServerApp")
@EnableLocator
@EnableManager
@EnablePdx(readSerialized = true)
@Configuration
public class CloudCacheConfig {
	
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
