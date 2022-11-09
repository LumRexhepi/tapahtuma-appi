package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TapahtumaRepository extends CrudRepository<Tapahtuma , Long>{

	List<Tapahtuma> findByName(String name); 
	Optional<Tapahtuma> findById(Long id);


	@Query("select t from Tapahtuma t where user_id = ?1")
	List<Tapahtuma> findOmatbyId (Long id);	
	
	
	
//	@Query("select t FROM Tapahtuma t join t.name u on t.user_id = u.id where u.username = ?1")
//	List<Tapahtuma> findOmat (String username);
	 
}
