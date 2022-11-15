package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagiRepository extends CrudRepository<Tagi, Long>{

	@Query("select tg, count(*) from Tapahtuma t inner join t.tagit tg group by tg.tagId order by count(*) desc ")
	List<Tagi> findAllTagit();
}
