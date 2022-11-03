package com.example.demo.domain.service.create_post;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.StaticResource;
import com.example.demo.domain.model.CreatePostResponse;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.domain.service.crud.StaticResourceService;
import com.example.demo.exception.FileQuantityException;
import com.example.demo.exception.FileSizeException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.utils.FileUtils;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service("createPostService")
public class CreatePostService {

    @Autowired
    private PostService postService;

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private AccountService accountService;

    private static final Set<String> ALLOWED_STATUSES = Sets.newLinkedHashSet(Arrays.asList("NORMAL", "ABNORMAL"));

    private static final int MAX_DESCRIPTION_SIZE = 200;

    private static final int MAX_IMAGE_QUANTITY = 4;

    private static final int MAX_VIDEO_QUANTITY = 1;

    private static final Long MAX_TOTAL_IMAGES_SIZE = 5242880L;

    private static final Long MAX_VIDEO_SIZE = 20971520L;

    public CreatePostResponse create(Long accountId, Post post) throws IOException, MissingParameterException, FileQuantityException, FileSizeException {
        List<String> imageLinks = new ArrayList<>();
        String videoLink = null;
        Account account = this.accountService.findById(accountId).get();
        boolean containImages = false;
        boolean containVideo = false;

        if(post.getDescription() == null) throw new MissingParameterException("Parameter is not enough.");

        if(post.getDescription().length() > MAX_DESCRIPTION_SIZE) throw new IllegalArgumentException("Parameter value is invalid.");

        if(post.getStatus() != null &&
           !ALLOWED_STATUSES.contains(post.getStatus())) throw new IllegalArgumentException("Parameter value is invalid.");

        if(post.getImages() != null &&
           post.getVideo() != null) throw new IllegalArgumentException("Parameter value is invalid.");

        if(post.getImages() != null) {
            if(post.getVideo() != null ||
               !areValidImageFiles(post.getImages())) throw new IllegalArgumentException("Parameter value is invalid.");

            if(post.getImages().size() > MAX_IMAGE_QUANTITY) throw new FileQuantityException("Maximum number of images exceeded.");

            if(FileUtils.calculateFilesSize(post.getImages()) > MAX_TOTAL_IMAGES_SIZE) throw new FileSizeException("File size is too big.");

            for(MultipartFile image : post.getImages()) {
                imageLinks.add(FileUtils.saveToDisk(image));
            }

            containImages = true;
        }

        if(post.getVideo() != null) {
            MultipartFile video = post.getVideo().get(0);

            if(post.getVideo().size() > MAX_VIDEO_QUANTITY) throw new FileQuantityException("Maximum number of videos exceeded.");

            if(post.getImages() != null ||
               !FileUtils.isVideo(video)) throw new IllegalArgumentException("Parameter value is invalid.");

            if(FileUtils.calculateFilesSize(Arrays.asList(video)) > MAX_VIDEO_SIZE) throw new FileSizeException("File size is too big.");

            videoLink = FileUtils.saveToDisk(video);

            containVideo = true;
        }

        post.setAccount(account);

        post = this.postService.saveOrUpdate(post);

        if(containImages) {
            Post finalPost = post;
            AtomicReference<Integer> temp = new AtomicReference<>(0);

            List<StaticResource> staticResources = imageLinks.stream()
                    .map(imageLink -> {
                        StaticResource imageResource = new StaticResource();
                        imageResource.setPost(finalPost);
                        imageResource.setLink(imageLink);
                        imageResource.setPosition(temp.getAndSet(temp.get() + 1));
                        imageResource.setType("IMAGE");
                        return imageResource;
                    })
                    .collect(Collectors.toList());

            this.staticResourceService.saveOrUpdateAll(staticResources);
        }

        if(containVideo) {
            StaticResource videoResource = new StaticResource();
            videoResource.setPost(post);
            videoResource.setLink(videoLink);
            videoResource.setType("VIDEO");
            videoResource.setPosition(5);

            this.staticResourceService.saveOrUpdate(videoResource);
        }

        return CreatePostResponse.builder()
                .id(post.getId())
                .build();
    }

    private boolean areValidImageFiles(List<MultipartFile> files) {
        for(MultipartFile file : files) {
            if(!FileUtils.isImage(file)) return false;
        }
        return true;
    }

}
