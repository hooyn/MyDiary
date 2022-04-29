package diary.diaryApp.controller;

import diary.diaryApp.domain.Diary;
import diary.diaryApp.domain.Member;
import diary.diaryApp.service.DiaryService;
import diary.diaryApp.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/api/diaries/{id}")
    public Result diary(@PathVariable("id") Long id){
        List<Diary> diaries = diaryService.findDiaries(id);
        List<DiaryDto> collect = diaries.stream()
                .map(d -> new DiaryDto(d.getTitle(), d.getContent(), d.getLocalDateTime(), d.getMember()))
                .collect(Collectors.toList());

        return new Result(true, 200, collect, "통신성공");
    }

    //다이어리 작성
    @PostMapping("/api/diaries/{id}")
    public Result saveDiary(
            @PathVariable("id") Long id,
            @RequestBody CreateDiaryRequest request){
        Member member = memberService.findOne(id);
        Diary diary = Diary.createDiary(member, request.getTitle(), request.getContent());
        Long saveDiaryId = diaryService.writeDiary(diary);

        return new Result(true, 200, "DiaryID: " + saveDiaryId, "통신성공");
    }

    //다이어리 수정

    //다이어리 삭제

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
        private Member member;
    }

    @Data
    @AllArgsConstructor
    private class CreateDiaryRequest {
        private String title;
        private String content;
    }
}

