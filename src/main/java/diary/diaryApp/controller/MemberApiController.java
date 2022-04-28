package diary.diaryApp.controller;

import diary.diaryApp.domain.Member;
import diary.diaryApp.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //멤버 정보 Dto로 조회하기
    @GetMapping("/api/members")
    public Result member(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName(), m.getEmail(), m.getPoint()))
                .collect(Collectors.toList());

        return new Result(true, 200, collect, "통신성공");
    }

    //멤버 정보 저장하기
    @PostMapping("/api/members")
    public Result saveMember(@RequestBody CreateMemberRequest request){
        Member member = new Member();
        member.setEmail(request.email);
        member.setName(request.name);
        member.setPassword(request.password);

        Long id = memberService.join(member);
        return new Result(true, 200, id, "통신성공");
    }

    //멤버 정보 업데이트
    @PutMapping("/api/members/{id}")
    public Result updateMember(
            @PathVariable("id") Long id,
            @RequestBody UpdateMemberRequest request){

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new Result(
                true,
                200,
                "Id:" + findMember.getId() +"-> " + findMember.getName(),
                "통신성공");
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
    static class MemberDto{ // 이렇게 DTO(Data Transfer Object)를 만들어서 API를 처리하는 것이 좋다.
        private String name;
        private String email;
        private int point;
    }

    @Data
    static class CreateMemberRequest{ // 이렇게 DTO(Data Transfer Object)를 만들어서 API를 처리하는 것이 좋다.
        private String email;
        private String name;
        private String password;
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }
}
