package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.KategoriaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tapahtuma;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TapahtumaRepository;
@CrossOrigin
@Controller
public class TapahtumaRESTController {
	@Autowired
	private TapahtumaRepository trepository;
	
	@Autowired
	private KategoriaRepository krepository;

	
	
	
	@GetMapping("/tapahtumat" )
	public @ResponseBody List<Tapahtuma> findTapahtumatRest() {
		return (List<Tapahtuma>) trepository.findAll();
	}
	
	@GetMapping(value ="/tapahtumat/filter/{keyword}" , produces = {MimeTypeUtils.TEXT_PLAIN_VALUE})
	public @ResponseBody String filterCount (@PathVariable("keyword") String kw){
		int i = trepository.findByKeyword(kw,Sort.by(Sort.Direction.DESC, "paiva")).size();
		return Integer.toString(i);
	}


}
