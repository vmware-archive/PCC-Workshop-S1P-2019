package io.pivotal.data.pizzastoreapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.apache.geode.internal.cache.GemFireCacheImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects;
import org.springframework.data.gemfire.util.RegionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.pivotal.data.pizzastoreapi.model.Pizza;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration
@SuppressWarnings("all")
class PizzaStoreApiApplicationTests {
	
	@Autowired
	private ClientCache clientCache;

	@Resource(name = "Pizza")
	private Region<Object, Object> pizzaRegion; 
	Pizza pizza = Pizza.named("plain").with(Pizza.Topping.CHEESE);

	@Test
	void contextLoads() {
	}
	
	@Test
	public void clientCacheIsMocked() {

		assertThat(this.clientCache).isNotNull();
		assertThat(this.clientCache).isInstanceOf(ClientCache.class);
		assertThat(this.clientCache).isNotInstanceOf(GemFireCacheImpl.class);
		assertThat(this.clientCache.isClosed()).isFalse();

		Set<Region<?, ?>> rootRegions = this.clientCache.rootRegions();

		assertThat(rootRegions).isNotNull();
		assertThat(rootRegions).hasSize(1);
		assertThat(rootRegions).containsExactly(this.pizzaRegion);
	}

	@Test
	public void pizzaRegionIsMocked() {

		assertThat(this.pizzaRegion).isNotNull();
		assertThat(this.pizzaRegion.getFullPath()).isEqualTo(RegionUtils.toRegionPath("Pizza"));
		assertThat(this.pizzaRegion.getName()).isEqualTo("Pizza");
		
		assertThat(this.pizzaRegion.put("plain", pizza)).isNull();
		assertThat(this.pizzaRegion.get("plain")).isEqualTo(pizza);
		assertThat(this.pizzaRegion.containsKey("plain")).isTrue();

		this.pizzaRegion.invalidate("plain");

		assertThat(this.pizzaRegion.containsKey("plain")).isTrue();
		assertThat(this.pizzaRegion.get("plain")).isNull();
		assertThat(this.pizzaRegion.remove("plain")).isNull();
		assertThat(this.pizzaRegion.containsKey("plain")).isFalse();
	}
	
	@ClientCacheApplication
	@EnableGemFireMockObjects
	static class TestConfiguration {

		@Bean("Pizza")
		public ClientRegionFactoryBean<Object, Object> pizzaRegion(GemFireCache gemfireCache) {

			ClientRegionFactoryBean<Object, Object> pizzaRegion = new ClientRegionFactoryBean<>();

			pizzaRegion.setCache(gemfireCache);
			pizzaRegion.setClose(false);
			pizzaRegion.setShortcut(ClientRegionShortcut.LOCAL);

			return pizzaRegion;
		}
	}
}
