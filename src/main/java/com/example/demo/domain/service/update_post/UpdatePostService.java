package com.example.demo.domain.service.update_post;

import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.StaticResource;
import com.example.demo.domain.model.CreatePostResponse;
import com.example.demo.domain.model.EditPostRequest;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.domain.service.crud.StaticResourceService;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.FileUtils;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service("updatePostService")
@Transactional
public class UpdatePostService {

    @Autowired
    private PostService postService;

    @Autowired
    private StaticResourceService staticResourceService;

    private static final int MAX_DESCRIPTION_SIZE = 100;

    private static final int MAX_IMAGE_QUANTITY = 4;

    private static final Set<String> ALLOWED_STATUSES = Sets.newLinkedHashSet(Arrays.asList("NORMAL", "ABNORMAL"));

    public CreatePostResponse update(Long accountId, EditPostRequest editPostRequest) throws IOException, NotFoundException, AccessDeniedException, MissingParameterException {
        if(editPostRequest.getId() == null ||
           editPostRequest.getAuto_block() == null ||
           editPostRequest.getAuto_accept() == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> rs = this.postService.findByIdWithAllRelationshipsLoadedEagerly(editPostRequest.getId());

        if(rs.isEmpty()) throw new NotFoundException("Post is not existed.");

        Post post = rs.get();

        if(!post.getAccount().getId().equals(accountId)) throw new AccessDeniedException("Not access.");

        if(editPostRequest.getStatus() != null &&
           !ALLOWED_STATUSES.contains(editPostRequest.getStatus())) throw new IllegalArgumentException("Parameter value is invalid.");

        post.setStatus(editPostRequest.getStatus());

        if(editPostRequest.getDescribed() != null &&
           (editPostRequest.getDescribed().length() > MAX_DESCRIPTION_SIZE ||
            editPostRequest.getDescribed().length() == 0)) throw new IllegalArgumentException("Parameter value is invalid.");

        post.setDescribed(editPostRequest.getDescribed());

        if(editPostRequest.getAuto_accept() != null &&
           !editPostRequest.getAuto_accept().equals("0") &&
           !editPostRequest.getAuto_accept().equals("1")) throw new IllegalArgumentException("Parameter value is invalid.");

        if(editPostRequest.getAuto_block() != null &&
           !editPostRequest.getAuto_block().equals("0") &&
           !editPostRequest.getAuto_block().equals("1")) throw new IllegalArgumentException("Parameter value is invalid.");

        if(editPostRequest.getImages() != null &&
           editPostRequest.getVideo() != null) throw new IllegalArgumentException("Parameter value is invalid.");

        if(editPostRequest.getImages() == null &&
           editPostRequest.getImage_sort() != null) throw new IllegalArgumentException("Parameter value is invalid.");

        post.setModified(LocalDateTime.now());

        if(editPostRequest.getImages() == null &&
           editPostRequest.getVideo() == null &&
            editPostRequest.getImage_del() == null) {
            this.postService.saveOrUpdate(post);
            return CreatePostResponse.builder()
                    .id(post.getId().toString())
                    .build();
        }

        List<StaticResource> staticResources = new ArrayList<>(post.getStaticResources());

        if(staticResources != null) {
            Collections.sort(staticResources, Comparator.comparingInt(StaticResource ::getPosition));
        }

        List<StaticResource> imageResources = staticResources != null ? getImageResources(staticResources) : new ArrayList<>();
        List<StaticResource> videoResources = staticResources != null ? getVideoResources(staticResources) : new ArrayList<>();

        if(editPostRequest.getImages() != null) {
            if(!videoResources.isEmpty()) throw new IllegalArgumentException("Parameter value is invalid.");

            List<Long> deletedImageIds = editPostRequest.getImage_del() == null ? new ArrayList<>() : editPostRequest.getImage_del();
            List<MultipartFile> newImages = editPostRequest.getImages();
            List<Integer> indexes = editPostRequest.getImage_sort() == null ? new ArrayList<>() : editPostRequest.getImage_sort();

            List<StaticResource> deletedImages = imageResources.stream()
                    .filter(imageResource -> deletedImageIds.contains(imageResource.getId()))
                    .collect(Collectors.toList());

            if(deletedImages.size() != deletedImageIds.size()) throw new IllegalArgumentException("Parameter value is invalid.");

            removeAndRearrange(imageResources, deletedImages);

            if(imageResources.size() + newImages.size() > MAX_IMAGE_QUANTITY) throw new IllegalArgumentException("Parameter value is invalid.");

            if(newImages.size() != indexes.size()) throw new IllegalArgumentException("Parameter value is invalid.");

            Optional<Integer> invalidIndex = indexes.stream()
                    .filter(index -> index < 0 || index > 3 || index > (imageResources.size() + newImages.size()) - 1)
                    .findAny();

            if(invalidIndex.isPresent()) throw new IllegalArgumentException("Parameter value is invalid.");

            List<StaticResource> addedImages = new ArrayList<>();

            for(MultipartFile image : newImages) {
                StaticResource addedImage = new StaticResource();
                addedImage.setPost(post);
                addedImage.setLink(FileUtils.saveToDisk(image));
                addedImage.setType("IMAGE");
                addedImages.add(addedImage);
            }

            addAndRearrange(imageResources, addedImages, indexes);

            this.staticResourceService.saveOrUpdateAll(imageResources);
            this.staticResourceService.deleteAll(deletedImages);
            this.postService.saveOrUpdate(post);
            return CreatePostResponse.builder()
                    .id(post.getId().toString())
                    .build();
        }

        if(editPostRequest.getVideo() != null) {
            if(!imageResources.isEmpty()) throw new IllegalArgumentException("Parameter value is invalid.");

            StaticResource addedVideo = new StaticResource();
            addedVideo.setPost(post);
            addedVideo.setLink(FileUtils.saveToDisk(editPostRequest.getVideo()));
            addedVideo.setType("IMAGE");
            addedVideo.setPosition(5);

            this.staticResourceService.saveOrUpdate(addedVideo);
            this.staticResourceService.deleteAll(videoResources);
            this.postService.saveOrUpdate(post);
            return CreatePostResponse.builder()
                    .id(post.getId().toString())
                    .build();
        }

        if(editPostRequest.getImage_del() != null) {
            List<Long> deletedImageIds = editPostRequest.getImage_del();

            List<StaticResource> deletedImages = imageResources.stream()
                    .filter(imageResource -> deletedImageIds.contains(imageResource.getId()))
                    .collect(Collectors.toList());

            if(deletedImages.size() != deletedImageIds.size()) throw new IllegalArgumentException("Parameter value is invalid.");

            removeAndRearrange(imageResources, deletedImages);

            this.staticResourceService.saveOrUpdateAll(imageResources);
            this.staticResourceService.deleteAll(deletedImages);
            this.postService.saveOrUpdate(post);
            return CreatePostResponse.builder()
                    .id(post.getId().toString())
                    .build();
        }

        return CreatePostResponse.builder()
                .id(post.getId().toString())
                .build();
    }

    private List<StaticResource> getImageResources(List<StaticResource> staticResources) {
        return staticResources.stream()
                .filter(staticResource -> staticResource.getType().equals("IMAGE"))
                .collect(Collectors.toList());
    }

    private List<StaticResource> getVideoResources(List<StaticResource> staticResources) {
        return staticResources.stream()
                .filter(staticResource -> staticResource.getType().equals("VIDEO"))
                .collect(Collectors.toList());
    }

    private void removeAndRearrange(List<StaticResource> staticResources, List<StaticResource> deletedOnes) {
        staticResources.removeAll(deletedOnes);
        for(int i = 0; i < staticResources.size(); ++i) {
            staticResources.get(i).setPosition(i);
        }
    }

    private void addAndRearrange(List<StaticResource> staticResources,
                                 List<StaticResource> addedOnes,
                                 List<Integer> indexes) {
        for(int i = 0; i < indexes.size(); ++i) {
            staticResources.add(indexes.get(i), addedOnes.get(i));
        }
        for(int i = 0; i < staticResources.size(); ++i) {
            staticResources.get(i).setPosition(i);
        }
    }

}
