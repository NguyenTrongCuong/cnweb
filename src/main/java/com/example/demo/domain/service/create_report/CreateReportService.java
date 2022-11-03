package com.example.demo.domain.service.create_report;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.Report;
import com.example.demo.domain.model.CreateReportRequest;
import com.example.demo.domain.model.CreateReportResponse;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.domain.service.crud.ReportService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Service("createReportService")
public class CreateReportService {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    private static final Set<String> ALLOWED_SUBJECTS = Sets.newLinkedHashSet(Arrays.asList(
            "VIOLENCE"
    ));

    private static final Long DETAILS_MAX_LENGTH = 200L;

    public CreateReportResponse create(Long accountId, CreateReportRequest createReportRequest) throws MissingParameterException, NotFoundException {
        Long id = createReportRequest.getId();
        String subject = createReportRequest.getSubject();
        String details = createReportRequest.getDetails();
        Account account = this.accountService.findById(accountId).get();

        if(id == null ||
           subject == null ||
           details == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> postRs = this.postService.findById(id);

        if(postRs.isEmpty()) throw new NotFoundException("Post is not existed.");

        if(!ALLOWED_SUBJECTS.contains(subject) ||
           details.length() > DETAILS_MAX_LENGTH.longValue()) throw new IllegalArgumentException("Parameter value is invalid.");

        Report report = Report.builder()
                                .account(account)
                                .subject(subject)
                                .details(details)
                                .post(postRs.get())
                                .build();

        report = this.reportService.saveOrUpdate(report);

        return CreateReportResponse.builder()
                .id(report.getId())
                .subject(report.getSubject())
                .details(report.getDetails())
                .build();
    }

}
