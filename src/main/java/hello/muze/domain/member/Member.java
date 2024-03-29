package hello.muze.domain.member;

import hello.muze.domain.comment.Comment;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.post.Post;
import hello.muze.web.repository.member.MemberRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"comment"})
@Getter
@Setter
public class Member {

    @Autowired
    MemberRepository memberRepository;


    public Member() {

    }

    public Member(String loginId, String nickName, String password, String email, String profile) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.profile = profile;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "아이디를 입력하세요")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String loginId;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16, message = "2자리 이상 16자리 이하입니다.")
    private String nickName;
    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

    private String passwordConfirm;

    private String Role;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @OrderBy("id desc")
    private List<Heart> hearts;


    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @OrderBy("id desc")
    private List<Comment> comments;


    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @OrderBy("id desc")
    private List<Post> posts;


    @NotEmpty(message = "이메일 주소를 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private String profile;
    @CreationTimestamp
    private Timestamp created;
    @UpdateTimestamp
    private Timestamp updated;


    public void changeEmail(String email) {
        this.email = email;
        save();
    }

    private void save() {
        memberRepository.save(this);
    }
}
