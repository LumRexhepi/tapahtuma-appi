package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TapahtumaRepository extends CrudRepository<Tapahtuma , Long>{

	List<Tapahtuma> findByName(String name); 
	Optional<Tapahtuma> findById(Long id);
	
	
	 
}
