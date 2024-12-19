package org.todo.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.todo.domain.member.dto.req.UpdateStatusMessageRequestDto;
import org.todo.domain.member.dto.res.UserInfoResponseDto;
import org.todo.domain.member.entity.Member;
import org.todo.domain.member.repository.MemberRepository;
import org.todo.domain.post.dto.res.PostResponseDto;
import org.todo.domain.post.repository.PostRepository;
import org.todo.domain.s3.service.S3Service;
import org.todo.global.error.CustomErrorCode;
import org.todo.global.exception.RestApiException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final S3Service s3Service;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public UserInfoResponseDto getUserInfo(Member member){

        List<PostResponseDto> posts = postRepository.findAllByMemberIdOrderByIdDesc(member.getId()).stream()
                .map(post -> {
                    String presignedUrl = s3Service.getImageUrl(post.getImage().getObjectKey());

                    return PostResponseDto.builder()
                            .postId(post.getId())
                            .username(member.getUsername())
                            .todoTitle(post.getTodo().getTitle())
                            .presignedUrl(presignedUrl)
                            .build();
                })
                .toList();

        return UserInfoResponseDto.builder()
                .nickname(member.getNickname())
                .statusMessage(member.getStatusMessage())
                .posts(posts)
                .build();
    }

    public Boolean updateStatusMessage(Member member, UpdateStatusMessageRequestDto req){
        Member nMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.MEMBER_NOT_FOUND));

        nMember.updateStatusMessage(req.getStatusMessage());

        return true;
    }
}
