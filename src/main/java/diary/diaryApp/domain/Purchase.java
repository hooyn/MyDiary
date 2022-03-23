package diary.diaryApp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Purchase {

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    private int orderPrice;
    private int count;

    //회원
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //아이템
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime localDateTime;

}
