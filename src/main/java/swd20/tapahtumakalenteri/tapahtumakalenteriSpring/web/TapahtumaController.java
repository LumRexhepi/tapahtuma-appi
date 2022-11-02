package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Kategoria;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.KategoriaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tapahtuma;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TapahtumaRepository;

@CrossOrigin
@Controller
public class TapahtumaController {

	@Autowired
	private TapahtumaRepository trepository;
	@Autowired
	private KategoriaRepository krepository;

	@GetMapping("/tapahtumalista")
	public String getBooks(Model model) {
		model.addAttribute("tapahtumat", trepository.findAll());

		return "tapahtumalista";

	}

	@GetMapping("/details/{id}")
	public String showTapahtuma(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("tp", trepository.findById(id) );
	
		return "tapahtumaDetails";
	}
	
	@GetMapping("/lisaa")
	public String lisaaTapahtuma(Model model) {
		model.addAttribute("tp", new Tapahtuma());
		model.addAttribute("kategoriat", krepository.findAll());
		return "lisaaTapahtuma";
}
	@PostMapping("/save")
	public String Save(Tapahtuma tapahtuma) {
		trepository.save(tapahtuma);
		return "redirect:tapahtumalista";
	}
	
	@GetMapping("/lisaa-kategoria")
	public String lisaaKategoria(Model model) {
		model.addAttribute("kategoria", new Kategoria());
		
		return "lisaakategoria";
	
	}
	@PostMapping("/saveK")
	public String SaveK(Kategoria kategoria) {
		krepository.save(kategoria);
		return "redirect:lisaa";
	}
	
    @GetMapping("/login")
	public String login() {
		return "login";
	} 
}