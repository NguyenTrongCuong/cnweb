package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.StaticResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaticResourceRepository extends JpaRepository<StaticResource, Long> {
}
