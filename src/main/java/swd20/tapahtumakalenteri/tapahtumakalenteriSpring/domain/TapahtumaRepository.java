package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TapahtumaRepository extends JpaRepository<Tapahtuma ,   Long>{

	List<Tapahtuma> findByName(String name); 
	Optional<Tapahtuma> findById(Long id);


	@Query("from Tapahtuma where user_id = ?1")
	List<Tapahtuma> findOmatbyId (Long id);	
	
	@Transactional
	@Modifying
	@Query("update Tapahtuma  set lippujajaljella = lippujajaljella - 1 where tapahtumaId = :id")
	void lippujenVahennys(@Param("id") Long id);
	
 
	@Query("select distinct t from Tapahtuma t join t.tagit ta "
			+ "where ta.tagId = :tagid and t.tapahtumaId != :tapahtumaid")
	List<Tapahtuma> findByTag (@Param("tagid") Long tagid, @Param("tapahtumaid") Long tapahtumaId);

	@Query("select distinct t from Tapahtuma t join t.tagit ta "
			+ "where (ta.tagId = :id1 and ta.tagId = :id2) "
			+ "or (ta.tagId = :id1 or ta.tagId = :id2) "
			+ "and t.tapahtumaId != :id3")
	List<Tapahtuma> findByTags (@Param("id1") Long  tagid1, @Param("id2") Long tagid2, @Param("id3") Long tapahtumaid);

	@Query("from Tapahtuma t where t.kategoria.kategoriaid = :id and t.tapahtumaId != :id2")
	List<Tapahtuma> findByKategoria ( @Param("id") Long kategoriaid, @Param("id2") Long tapahtumaid);
	
	@Query("from Tapahtuma t where t.tapahtumaId != :id")
	List<Tapahtuma> findAllBut(@Param("id") Long id);

}
	
	




