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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //아이템
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private LocalDateTime localDateTime;

    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member){
        this.member = member;
        member.getPurchases().add(this);
    }

    /**
     * 생성 메서드
     */
    public static Purchase createPurchase(Member member, Item item, int count){
        Purchase purchase = new Purchase();
        purchase.setMember(member);
        purchase.setItem(item);
        purchase.setLocalDateTime(LocalDateTime.now());
        purchase.setOrderPrice(item.getPrice()*count);
        purchase.setCount(count);

        item.removeStock(count);
        return purchase;
    }
    /**
     * 비즈니스 로직
     */
    public void cancel(int count){
        getItem().addStock(count);
    }
}
