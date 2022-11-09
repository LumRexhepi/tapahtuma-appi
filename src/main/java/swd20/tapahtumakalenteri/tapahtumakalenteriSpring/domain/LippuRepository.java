package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface LippuRepository extends JpaRepository<Lippu, Long>{

	
	@Query("from Lippu where user_id = ?1")
	List<Lippu> findOmatLiputById (Long id);	

	@Transactional
	@Modifying
	@Query("update Lippu set kaytetty = 'TRUE' where lippuid = ?1")
	void kaytalippu (Long id);
}
