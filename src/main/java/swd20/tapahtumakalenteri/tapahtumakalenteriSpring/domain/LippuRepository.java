package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LippuRepository extends CrudRepository<Lippu, Long>{

	@Check(constraints = "lippujaJaljella >=0")
	@Query("from Lippu where user_id = ?1")
	List<Lippu> findOmatLiputById (Long id);	

}
