package com.dh.catalog.client;

import com.dh.catalog.config.LoadBalancerConfiguration;
import com.dh.catalog.dto.MovieDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="api-movie")
@LoadBalancerClient(value="api-movie", configuration= LoadBalancerConfiguration.class)
public interface MovieServiceClient {

	@GetMapping("/api/v1/movies/{genre}")
	List<MovieDto> getMovieByGenre(@PathVariable (value = "genre") String genre);
}
