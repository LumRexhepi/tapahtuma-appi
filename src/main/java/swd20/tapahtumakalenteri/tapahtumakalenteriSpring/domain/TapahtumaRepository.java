package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TapahtumaRepository extends JpaRepository<Tapahtuma, Long> {

	String entity = null;

	List<Tapahtuma> findByName(String name);

	Optional<Tapahtuma> findById(Long id);

	@Query("from Tapahtuma where user_id = ?1")
	List<Tapahtuma> findOmatbyId(Long id);

	@Transactional
	@Modifying
	@Query("update Tapahtuma  set lippujajaljella = lippujajaljella - 1 where tapahtumaId = :id")
	void lippujenVahennys(@Param("id") Long id);

	// suosituksia varten
	// jos tapahtumalla on vain yksi tagi
	@Query("select distinct t from Tapahtuma t join t.tagit ta "
			+ "where ta.tagId = :tagid and t.tapahtumaId != :tapahtumaid")
	List<Tapahtuma> findByTag(@Param("tagid") Long tagid, @Param("tapahtumaid") Long tapahtumaId);

	// suosituksia varten
	// jos tapahtumalla on kaksi tagia, mutta palauttaa tapahtumat jossai on
	// jompikumpi tagi
	@Query("select distinct t from Tapahtuma t join t.tagit ta " + "where (ta.tagId = :id1 or ta.tagId = :id2) "
			+ "and t.tapahtumaId != :id3 ")
	List<Tapahtuma> findByTag2(@Param("id1") Long tagid1, @Param("id2") Long tagid2, @Param("id3") Long tapahtumaid);

	// suosituksia varten
	// jos tapahtumalla on kaksi tagia etsitään tapahtumia jossa on samat 2 tagia
	@Query("select distinct t, count(*) from Tapahtuma t join t.tagit ta "
			+ "where (ta.tagId = :id1 or ta.tagId = :id2) and t.tapahtumaId != :id3 group by t having count(*) = 2")
	List<Tapahtuma> findByTags(@Param("id1") Long tagid1, @Param("id2") Long tagid2, @Param("id3") Long tapahtumaid);

	@Query("from Tapahtuma t where t.kategoria.kategoriaid = :id and t.tapahtumaId != :id2 ")
	List<Tapahtuma> findByKategoria(@Param("id") Long kategoriaid, @Param("id2") Long tapahtumaid);

	@Query("from Tapahtuma t where t.tapahtumaId != :id")
	List<Tapahtuma> findAllBut(@Param("id") Long id);

	
	// filtteröintiä ja sorttausta varten
	@Query("select distinct t from Tapahtuma t join t.tagit ta " + "where lower(ta.nimi)= lower(:keyword) "
			+ "or lower(t.kategoria.nimi)= lower(:keyword) ")
	List<Tapahtuma> findByKeyword(@Param("keyword") String keyword, Sort sort);

	// filtteröintiä ja sorttausta varten
	@Query("select distinct t from Tapahtuma t " + "where lower(t.name) like %:keyword% "
			+ "or lower(t.kuvaus) like %:keyword%")
	List<Tapahtuma> findBySearch(@Param("keyword") String keyword, Sort sort);
	
	// filtteröintiä ja sorttausta varten
	@Query("select t, sum(l.lippuId)  from Tapahtuma t left join t.liput l where lower(t.name) like %:keyword% "
			+ "or lower(t.kuvaus) like %:keyword% group by t order by sum(l.lippuId) desc")
	List<Tapahtuma> findByPopularity(@Param("keyword") String keyword);

}
