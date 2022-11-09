package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TapahtumaRepository extends JpaRepository<Tapahtuma ,   Long>{

	List<Tapahtuma> findByName(String name); 
	Optional<Tapahtuma> findById(Long id);


	@Query("from Tapahtuma where user_id = ?1")
	List<Tapahtuma> findOmatbyId (Long id);	
	
	@Transactional
	@Modifying
	@Query("update Tapahtuma  set lippujajaljella = lippujajaljella - 1 where tapahtumaId = ?1")
	void lippujenVahennys(Long id);
	
//	@Query("select t FROM Tapahtuma t join t.name u on t.user_id = u.id where u.username = ?1")
//	List<Tapahtuma> findOmat (String username);
//	 
}
