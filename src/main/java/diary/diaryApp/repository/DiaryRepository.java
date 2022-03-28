package diary.diaryApp.repository;

import diary.diaryApp.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryRepository {

    private final EntityManager em;

    public void save(Diary diary){
        if(diary.getId() == null){
            em.persist(diary);
        } else {
            em.merge(diary);
        }
    }

    public Diary findOne(Long id){
        return em.find(Diary.class, id);
    }

    public List<Diary> findAll(){
        return em.createQuery("select d from Diary d", Diary.class)
                .getResultList();
    }

    public void delete(Long id){
        Diary diary = em.find(Diary.class, id);
        if(diary.getId()!=null){
            em.remove(diary);
        }
    }

    public List<Diary> findAllWithEmail(String email){
        return em.createQuery("select d from Diary d" +
                 " join d.member m where m.email = :email", Diary.class)
                .setParameter("email", email)
                .getResultList();
    }


}
