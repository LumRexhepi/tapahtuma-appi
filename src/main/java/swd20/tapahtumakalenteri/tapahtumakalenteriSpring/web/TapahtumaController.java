package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Kategoria;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.KategoriaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Lippu;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.LippuRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TagiRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tapahtuma;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TapahtumaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.User;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.UserRepository;

@CrossOrigin
@Controller
public class TapahtumaController {

	@Autowired
	private TapahtumaRepository trepository;
	@Autowired
	private KategoriaRepository krepository;
	@Autowired
	private TagiRepository tgrepository;
	@Autowired
	private UserRepository urepository;
	@Autowired
	private LippuRepository lrepository;
	
	@GetMapping("/")
	public String etusivu() {
		return "redirect:tapahtumalista";
	}

	@GetMapping("/tapahtumalista")
	public String getBooks(Model model) {
		model.addAttribute("tapahtumat", trepository.findAll());

		return "tapahtumalista";

	}

	@GetMapping("/details/{id}")
	public String showTapahtuma(@PathVariable("id") Long id, Model model, Principal principal) {
		model.addAttribute("tp", trepository.findById(id));
		model.addAttribute("tapahtumat", trepository.findAll());
		model.addAttribute("user", urepository.findByUsername(principal.getName()));
		model.addAttribute("lippu", new Lippu());
		return "tapahtumaDetails";
	}

	@GetMapping("/lisaa")
	public String lisaaTapahtuma(Model model, Principal pr) {
		model.addAttribute("tp", new Tapahtuma());
		model.addAttribute("kategoriat", krepository.findAll());
		model.addAttribute("tagit", tgrepository.findAll());
		User user = urepository.findByUsername(pr.getName());
		model.addAttribute("user", user);

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

	@GetMapping("/omat-tapahtumat")
	public String getOmatTapahtumat(Model model, Principal principal) {
		String username = principal.getName();
		User user = urepository.findByUsername(username);
		model.addAttribute("tapahtumat", trepository.findOmatbyId(user.getUserId()));
		return "usertapahtumat";

	}

	@GetMapping("/omat-liput")
	public String getOmatLiput(Model model, Principal principal) {
		String username = principal.getName();
		User user = urepository.findByUsername(username);
		model.addAttribute("liput", lrepository.findOmatLiputById(user.getUserId()));
		return "userliput";

	}

	@PostMapping("/savelippu")
	public String saveLippu(Lippu lippu) {
		lrepository.save(lippu);
		trepository.lippujenVahennys(lippu.getTapahtuma().getTapahtumaId());
		return "redirect:omat-liput";

	}

	@GetMapping("/delete/{id}")
//	@PreAuthorize("hasAuthority('ADMIN')")
//	@PreAuthorize("#id == authentication.trepository.findById(id)).get().getUser().getUserId()")
	public String deleteTapahtuma(@PathVariable("id") Long id, Model model, Principal pr) {
		Long taphtuma_userId = trepository.findById(id).get().getUser().getUserId();
		User käyttäjä = urepository.findByUsername(pr.getName());
		if (taphtuma_userId == käyttäjä.getUserId() || käyttäjä.getRole() == "ADMIN" ) {
			trepository.deleteById(id);
			
		}
		return "redirect:../tapahtumalista";
	}
	
	@GetMapping("/nayta-lippu/{id}")
	public String naytaLippu(Model model, @PathVariable("id") Long id) {
		model.addAttribute("tp", lrepository.findById(id).get().getTapahtuma());
		model.addAttribute("lippu", lrepository.findById(id));
		return "Lippu";
	}
	
	@PostMapping("/kayta-lippu")
	public String kaytaLippu(Lippu lippu) {
		lrepository.kaytalippu(lippu.getLippuId());
		return "redirect:omat-liput";
	}
	
}
