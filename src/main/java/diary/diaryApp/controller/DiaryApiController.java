package diary.diaryApp.controller;

import diary.diaryApp.domain.Diary;
import diary.diaryApp.domain.Member;
import diary.diaryApp.service.DiaryService;
import diary.diaryApp.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.RequestingUserName;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DiaryApiController {
    private final DiaryService diaryService;
    private final MemberService memberService;
    //다이어리 조회
    @GetMapping("/api/diary/{id}")
    public Result diary(@PathVariable("id") Long id){
        List<Diary> diaries = diaryService.findDiaries(id);
        List<DiaryDto> collect = diaries.stream()
                .map(d -> new DiaryDto(d.getTitle(), d.getContent(), d.getLocalDateTime(), d.getMember().getName()))
                .collect(Collectors.toList());

        return new Result(true, 200, collect, "통신성공");
    }

    //다이어리 작성
    @PostMapping("/api/diary")
    public Result saveDiary(
            @RequestBody CreateDiaryRequest request){
        Member member = memberService.findOne(request.getMember_id());
        Diary diary = Diary.createDiary(member, request.getTitle(), request.getContent());
        Long saveDiaryId = diaryService.writeDiary(diary);

        return new Result(true, 200, "DiaryID: " + saveDiaryId, "통신성공");
    }

    //다이어리 수정
    @PutMapping("/api/diary/{id}")
    public Result updateDiary(
            @PathVariable("id") Long id,
            @RequestBody UpdateDiaryRequest request
    ){
        diaryService.updateDiary(id, request.getTitle(), request.getContent());

        return new Result(true, 200, id + "번 다이어리 수정 완료", "통신성공");
    }

    //다이어리 삭제
    @DeleteMapping("/api/diary/{id}")
    public Result deleteDiary(
            @PathVariable("id") Long id
    ){
        diaryService.deleteDiary(id);
        return new Result(true, 200, id + "번 다이어리 삭제 완료", "통신성공");
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
    static class DiaryDto{
        private String title;
        private String content;
        private LocalDateTime localDateTime;
        private String member_name;
    }

    @Data
    @AllArgsConstructor
    static class CreateDiaryRequest {
        private Long member_id;
        private String title;
        private String content;
    }

    @Data
    @AllArgsConstructor
    static class UpdateDiaryRequest {
        private String title;
        private String content;
    }
}

