package diary.diaryApp.service;

import diary.diaryApp.domain.Item;
import diary.diaryApp.domain.Member;
import diary.diaryApp.domain.Purchase;
import diary.diaryApp.repository.ItemRepository;
import diary.diaryApp.repository.MemberRepository;
import diary.diaryApp.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final MemberRepository memberRepository;
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;

    /**
     * 구매
     */
    @Transactional
    public Long purchase(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Purchase purchase = Purchase.createPurchase(member, item, count);
        purchaseRepository.save(purchase);

        return purchase.getId();
    }

    /**
     * 구매 취소
     */
    @Transactional
    public void cancelPurchase(Long purchaseId){
        Purchase purchase = purchaseRepository.findOne(purchaseId);
        int count = purchase.getCount();

        purchase.cancel(count);
        purchaseRepository.delete(purchaseId);
        //삭제되는 거 테스트 해보기
    }

    /**
     * 구매 목록 검색(이메일에 해당하는)
     */
    public List<Purchase> findOrders(String email){
        return purchaseRepository.findAllWithEmail(email);
    }

}
