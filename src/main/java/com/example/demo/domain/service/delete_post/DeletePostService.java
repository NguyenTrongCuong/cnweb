package com.example.demo.domain.service.delete_post;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.model.DeletePostRequest;
import com.example.demo.domain.service.crud.*;
import com.example.demo.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service("deletePostService")
@Transactional
public class DeletePostService {

    @Autowired
    private PostService postService;

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReportService reportService;

    public String delete(DeletePostRequest deletePostRequest) throws MissingParameterException {
        Long id = deletePostRequest.getId();

        if(id == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> rs = this.postService.findByIdWithAllRelationshipsLoadedEagerly(id);

        if(rs.isEmpty()) throw new IllegalArgumentException("Post is not existed.");

        this.staticResourceService.deleteAll(rs.get().getStaticResources());

        this.commentService.deleteAll(rs.get().getComments());

        this.reportService.deleteAll(rs.get().getReports());

        Set<Account> supporters = rs.get().getSupporters();

        supporters.forEach(supporter -> supporter.getLikedPosts().remove(rs.get()));

        this.accountService.saveOrUpdateAll(supporters);

        this.postService.delete(rs.get());

        return "Post with ID " + id + " has been deleted successfully.";
    }

}
