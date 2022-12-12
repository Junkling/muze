package hello.muze.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hello.muze.domain.heart.Heart;
import hello.muze.domain.post.Post;
import lombok.Data;
import org.apache.ibatis.annotations.One;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//@Entity

/**
 * 추가해야할 기능: nickName 저장시 중복 방지, 메일 인증 추가, 비밀번호 확인 시스템, 메일로 계정정보 찾기
 */
@Data
@Entity
public class Member {

    public Member(){

    }

    public Member(String loginId, String nickName, String password, String email, String profile) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.profile = profile;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "아이디를 입력하세요")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String loginId;

    @NotEmpty(message = "닉네임을 입력하세요")
    @Length(min = 2, max = 16,message = "2자리 이상 16자리 이하입니다.")
    private String nickName;
    @NotEmpty(message = "비밀번호를 입력하세요")
//    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "비밀번호는 영어와 숫자만 사용하여 4~20자리여야 합니다.")
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

    @NotEmpty(message = "이메일 주소를 입력하세요")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    private String profile;
    @CreationTimestamp
    private Timestamp created;
    @UpdateTimestamp
    private Timestamp updated;


}
