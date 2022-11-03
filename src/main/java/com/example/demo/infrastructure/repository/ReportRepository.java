package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {



}
