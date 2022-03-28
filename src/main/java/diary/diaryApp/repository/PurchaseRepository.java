package diary.diaryApp.repository;

import diary.diaryApp.domain.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseRepository {

    private final EntityManager em;

    public void save(Purchase purchase){
        em.persist(purchase);
    }

    public void delete(Long id){
        Purchase purchase = em.find(Purchase.class, id);
        em.remove(purchase);
    }

    public Purchase findOne(Long id){
        return em.find(Purchase.class, id);
    }


    public List<Purchase> findAll(){
        return em.createQuery("select p from Purchase p" +
                        " join fetch p.member m" +
                        " join fetch p.item i", Purchase.class)
                .getResultList();
    }

    public List<Purchase> findAllWithEmail(String email){
        return em.createQuery("select p from Purchase  p" +
                 " join p.member m where m.email = :email", Purchase.class)
                .setParameter("email", email)
                .getResultList();

    }

}
