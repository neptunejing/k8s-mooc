package com.nanqing.pingpongapp.repo;

import com.example.pingpong.model.CountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountRepository extends JpaRepository<CountEntity, Long> {
}
