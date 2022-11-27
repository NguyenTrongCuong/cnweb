package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Report;
import com.example.demo.infrastructure.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service("reportService")
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report saveOrUpdate(Report report) {
        return this.reportRepository.save(report);
    }

    public void delete(Report report) {
        this.reportRepository.delete(report);
    }

    public void deleteAll(Collection<Report> reports) {
        this.reportRepository.deleteAll(reports);
    }

    public Optional<Report> findByAccountIdAndPostId(Long accountId, Long postId) {
        return this.reportRepository.findByAccountIdAndPostId(accountId, postId);
    }
}
