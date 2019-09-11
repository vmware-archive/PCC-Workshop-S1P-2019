package io.pivotal.oqlqueries.config

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import io.pivotal.oqlqueries.domain.Album
import io.pivotal.oqlqueries.repository.PccAlbumRepository

@EnableClusterConfiguration(useHttp = true, requireHttps = false)
@EnableEntityDefinedRegions(basePackageClasses = Album.class)
@EnableGemfireRepositories(basePackageClasses = PccAlbumRepository.class)
@Configuration
public class PccConfig {


}
