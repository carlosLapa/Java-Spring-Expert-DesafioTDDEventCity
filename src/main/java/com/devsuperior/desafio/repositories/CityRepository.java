package com.devsuperior.desafio.repositories;

import com.devsuperior.desafio.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
