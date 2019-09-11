package io.pivotal.oqlqueries.scripts

import org.springframework.context.annotation.Configuration
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories

import io.pivotal.oqlqueries.domain.Album
import io.pivotal.oqlqueries.repository.PccAlbumRepository

println "hello world"