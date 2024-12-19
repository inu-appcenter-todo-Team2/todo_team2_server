package org.todo.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.todo.domain.image.entity.Image;
import org.todo.domain.image.repository.ImageRepository;
import org.todo.domain.member.entity.Member;
import org.todo.domain.post.dto.req.UploadPostRequestDto;
import org.todo.domain.post.dto.res.ExplorePostResponseDto;
import org.todo.domain.post.dto.res.PostResponseDto;
import org.todo.domain.post.dto.res.UploadPostResponseDto;
import org.todo.domain.post.entity.Post;
import org.todo.domain.post.repository.PostRepository;
import org.todo.domain.s3.service.S3Service;
import org.todo.domain.todo.entity.Todo;
import org.todo.domain.todo.repository.TodoRepository;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.RestApiException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final S3Service s3Service;

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public UploadPostResponseDto createPost(Member member, UploadPostRequestDto req){
        Image image = Image.builder()
                .objectKey(req.getObjectKey())
                .build();

        imageRepository.save(image);

        Todo todo = todoRepository.findById(req.getTodoId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.TODO_NOT_FOUND));

        if(!Objects.equals(todo.getMember().getId(), member.getId()))
            throw new RestApiException(CustomErrorCode.TODO_PERMISSION_DENIED);

        Post post = Post.builder()
                .todo(todo)
                .image(image)
                .build();

        postRepository.save(post);

        return UploadPostResponseDto.builder()
                .todoTitle(todo.getTitle())
                .postId(post.getId())
                .build();
    }

    @Transactional
    public Boolean deletePost(Member member, Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.POST_NOT_FOUND));

        if(!Objects.equals(post.getTodo().getMember().getId(), member.getId()))
            throw new RestApiException(CustomErrorCode.TODO_PERMISSION_DENIED);

        postRepository.deleteById(postId);

        return true;
    }

    @Transactional(readOnly = true)
    public ExplorePostResponseDto getAllPost(Long cursor){

        List<PostResponseDto> posts;
        Long newCursor;

        if(cursor == null){
            posts = postRepository.findTop3ByOrderByIdDesc().stream()
                    .map(post -> {
                        String presignedUrl = s3Service.getImageUrl(post.getImage().getObjectKey());

                        return PostResponseDto.builder()
                                .postId(post.getId())
                                .username(post.getTodo().getMember().getUsername())
                                .todoTitle(post.getTodo().getTitle())
                                .presignedUrl(presignedUrl)
                                .build();
                    })
                    .toList();
            newCursor = posts.isEmpty() ? null : posts.get(posts.size() - 1).getPostId() - 1;

        }else{
            posts = postRepository.findTop3ByIdLessThanOrderByIdDesc(cursor).stream()
                    .map(post -> {
                        String presignedUrl = s3Service.getImageUrl(post.getImage().getObjectKey());

                        return PostResponseDto.builder()
                                .postId(post.getId())
                                .username(post.getTodo().getMember().getUsername())
                                .todoTitle(post.getTodo().getTitle())
                                .presignedUrl(presignedUrl)
                                .build();
                    })
                    .toList();
            newCursor = posts.isEmpty() ? null : posts.get(posts.size() - 1).getPostId() - 1;
        }

        return ExplorePostResponseDto.builder()
                .posts(posts)
                .cursor(newCursor)
                .build();
    }
}
