package diary.diaryApp.controller;

import diary.diaryApp.domain.Purchase;
import diary.diaryApp.service.PurchaseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PurchaseApiController {
    private final PurchaseService purchaseService;

    //아이템 구매
    @PostMapping("/api/purchase")
    public Result savePurchase(@RequestBody CreatePurchaseRequest request){
        Long purchaseId = purchaseService.purchase(request.getMemberId(), request.getItemId(), request.getCount());

        return new Result(true, 200, "Purchase ID: " + purchaseId, "통신성공");
    }

    //아이템 구매 취소
    @DeleteMapping("/api/purchase/{id}")
    public Result deletePurchase(@PathVariable("id") Long id){
        purchaseService.cancelPurchase(id);

        return new Result(true, 200, "Delete ID: " + id, "통신성공");
    }

    //not stockQuantity 일때 예외 처리
    @RestControllerAdvice
    public class IllegalStateExceptionHandler {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(IllegalStateException.class)
        public Result handleAlreadyExistEmail(IllegalStateException ex) {
            return new Result(false, 400, "BAD_REQUEST", "not stock quantity");
        }
    }

    //사용자 ID에 따른 구매 내역 조회
    @GetMapping("/api/purchase/{id}")
    public Result purchaseList(@PathVariable("id") Long id){
        List<Purchase> purchases = purchaseService.findPurchasesId(id);
        List<PurchaseDto> collect = purchases.stream()
                .map(p -> new PurchaseDto(p))
                .collect(Collectors.toList());

        return new Result(true, 200, collect, "통신성공");
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
    static class CreatePurchaseRequest {
        private Long memberId;
        private Long itemId;
        private int count;
    }

    @Getter
    public static class PurchaseDto{
        private String name;
        private LocalDateTime purchaseDate;

        private String itemName;
        private int count;
        private int orderPrice;

        public PurchaseDto(Purchase p) {
            this.name = p.getMember().getName();
            this.purchaseDate = p.getLocalDateTime();
            this.itemName = p.getItem().getName();
            this.count = p.getCount();
            this.orderPrice = p.getOrderPrice();
        }
    }
}
