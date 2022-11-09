package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	@GetMapping(value = "/tapahtumat")
	public @ResponseBody List<Tapahtuma> findTapahtumatRest() {
		return (List<Tapahtuma>) trepository.findAll();
	}
}
