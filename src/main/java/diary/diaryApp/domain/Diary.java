package diary.diaryApp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Diary {

    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime localDateTime;

    //멤버 정보
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
