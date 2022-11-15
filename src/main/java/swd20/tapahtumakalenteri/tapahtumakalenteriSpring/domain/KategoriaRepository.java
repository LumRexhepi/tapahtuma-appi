package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface KategoriaRepository extends JpaRepository <Kategoria, Long>{
	List<Kategoria> findAll(); 

	@Query("select k, count(*) from Tapahtuma t inner join t.kategoria k group by k.kategoriaid order by count(*) desc")
	List<Kategoria> findAll2(); 
	
	

}
