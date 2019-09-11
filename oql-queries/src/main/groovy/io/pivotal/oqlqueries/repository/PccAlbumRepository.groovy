package io.pivotal.oqlqueries.repository

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.oqlqueries.domain.Album


public interface PccAlbumRepository extends GemfireRepository<Album, String> {
	

}
