package org.todo.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.todo.domain.global.BaseEntity;
import org.todo.domain.member.dto.req.SignupRequestDto;
import org.todo.domain.todo.entity.Todo;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String statusMessage;

    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String personalColor;

    @OneToMany(mappedBy = "member")
    private List<Todo> todos = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return this.username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Builder
    public Member(SignupRequestDto req, String encodedPwd){
        this.email = req.getEmail();
        this.password = encodedPwd;
        this.username = req.getUsername();
        this.nickname = req.getNickname();
        this.statusMessage = req.getStatusMessage();
        this.birth = req.getBirth();
        this.gender = Gender.valueOf(req.getGender());
        this.personalColor = req.getPersonalColor();
    }

    public String getEncodedPW(){
        return this.password;
    }

    public void updatePersonalColor(String personalColor){
        this.personalColor = personalColor;
    }

    public void updateStatusMessage(String statusMessage){
        this.statusMessage = statusMessage;
    }
}