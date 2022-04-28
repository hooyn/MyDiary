package diary.diaryApp.service;

import diary.diaryApp.domain.Item;
import diary.diaryApp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 아이템 저장
     */
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 아이템 수정
     */
    @Transactional
    public void updateItem(Long id, String name, String description, int price, int stockQuantity){
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        //변경감지를 이용한 업데이트
    }

    /**
     * 아이템 전체 조회
     */
    @Transactional(readOnly = true)
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    /**
     * 아이템 하나 조회
     */
    @Transactional(readOnly = true)
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    /**
     * 아이템 삭제
     */
    @Transactional
    public void deleteItem(Long id){
        itemRepository.delete(id);
    }


}
