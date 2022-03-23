package diary.diaryApp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private int point;

    //구매목록 리스트
    @OneToMany(mappedBy = "member")
    private List<Purchase> purchases = new ArrayList<>();

    //다이어리 리스트
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();
}
