package diary.diaryApp.service;

import diary.diaryApp.domain.Diary;
import diary.diaryApp.domain.Member;
import diary.diaryApp.repository.DiaryRepository;
import diary.diaryApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    /**
     * 다이어리 작성
     */
    @Transactional
    public Long writeDiary(Long memberId, String title, String content){
        Member member = memberRepository.findOne(memberId);

        Diary diary = Diary.createDiary(member, title, content);
        diaryRepository.save(diary);

        return diary.getId();
    }

    /**
     * 다이어리 수정
     */
    @Transactional
    public void updateDiary(Long id, String title, String content){
        Diary diary = diaryRepository.findOne(id);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setLocalDateTime(LocalDateTime.now());
        //변경감지를 이용한 업데이트 (dirty checking)
    }

    /**
     * 다이어리 전체 조회
     */
    //@Transactional(readOnly = true)
    //public List<Diary> findDiaries(){
    //    return diaryRepository.findAll();
    //}

    /**
     * 이메일에 따른 다이어리 조회
     */
    @Transactional(readOnly = true)
    public List<Diary> findDiaries(String email){
        return diaryRepository.findAllWithEmail(email);
    }

    /**
     * 다이어리 삭제
     */
    @Transactional
    public void deleteDiary(Long id){
        diaryRepository.delete(id);
    }
}
