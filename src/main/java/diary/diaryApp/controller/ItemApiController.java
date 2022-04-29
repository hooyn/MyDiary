package diary.diaryApp.controller;

import diary.diaryApp.domain.Item;
import diary.diaryApp.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {
    private final ItemService itemService;

    //아이템 정보 조회
    @GetMapping("/api/items")
    public Result item(){
        List<Item> findItems = itemService.findItems();
        List<Object> collect = findItems.stream()
                .map(i -> new ItemDto(i.getName(), i.getDescription(), i.getPrice(), i.getStockQuantity()))
                .collect(Collectors.toList());

        return new Result(true, 200, collect, "통신성공");
    }

    //아이템 정보 등록
    @PostMapping("/api/items")
    public Result saveItem(@RequestBody CreateItemRequest request){
        Item item = new Item();
        item.setName(request.getName());

        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setStockQuantity(request.getStockQuantity());

        Long id = itemService.saveItem(item);
        return new Result(true, 200, "saved Item Id: " + id,"통신성공");
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private boolean isSuccess;
        private int code;
        private T data;
        private String message;
    }

    @Data
    @AllArgsConstructor
    static class ItemDto {
        private String name;
        private String description;
        private int price;
        private int stockQuantity;
    }

    @Data
    static class CreateItemRequest {
        private String name;
        private String description;
        private int price;
        private int stockQuantity;
    }
}
