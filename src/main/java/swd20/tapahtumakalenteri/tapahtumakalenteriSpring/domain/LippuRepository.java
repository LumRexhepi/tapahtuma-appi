package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LippuRepository extends CrudRepository<Lippu, Long>{

	@Query("from Lippu where user_id = ?1")
	List<Lippu> findOmatLiputById (Long id);	

}
