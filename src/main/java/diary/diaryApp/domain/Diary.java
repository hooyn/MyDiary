package diary.diaryApp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member){
        this.member = member;
        member.getDiaries().add(this);
    }

    /**
     * 생성 메서드
     */
    public static Diary createDiary(Member member, String title, String content){
        Diary diary = new Diary();
        diary.setMember(member);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setLocalDateTime(LocalDateTime.now());

        return diary;
    }
}
