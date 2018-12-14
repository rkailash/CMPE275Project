package com.movie.central.MovieCentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EntityScan(basePackages = {"com/movie/central/MovieCentral/model", "com/movie/central/MovieCentral/util"})
public class MovieCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCentralApplication.class, args);
	}
}
