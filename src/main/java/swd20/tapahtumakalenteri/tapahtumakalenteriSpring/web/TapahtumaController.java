package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.security.Principal;
import java.util.Optional;
import java.util.function.IntToLongFunction;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
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

		// try catchin sisään sosituksia antavia kutsuja (details sivulla " katso myös
		// näitä")
		try {
			// kokeillaan ensin jos tapahtumalla on 2 tagia. käytetään findbytags jossa
			// parametrina menee 2 tagin id:t
			model.addAttribute("tapahtumat",
					trepository.findByTags(trepository.findById(id).get().getTagit().get(0).getTagId(),
							trepository.findById(id).get().getTagit().get(1).getTagId(),
							trepository.findById(id).get().getTapahtumaId()));
		} catch (Exception e) {
			// kokeillaan sen jälkeen findbytag jossa parametrina menee vain yhden tagin id
			try {
				model.addAttribute("tapahtumat",
						trepository.findByTag(trepository.findById(id).get().getTagit().get(0).getTagId(),
								trepository.findById(id).get().getTapahtumaId()));
			} catch (Exception es) {
				try {
					// jos tapahtumalle ole annettu tageja käytetään kategoriaa suosituksena
					model.addAttribute("tapahtumat", trepository
							.findByKategoria(trepository.findById(id).get().getKategoria().getKategoriaId(), id));
				} catch (Exception ex) {
					// viimeisenä vaihtoehtona ehdotetaan kaikkia tapahtumia
					model.addAttribute("tapahtumat", ( trepository.findAll()));
					
				}
			}
		}
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
	public String Save(Tapahtuma tapahtuma, Model m) {
		try {
			trepository.save(tapahtuma);
			return "redirect:tapahtumalista";
		} catch (Exception e) {
			m.addAttribute("teksti", "Tapahtuman lisääminen ei onnistut. Tarkista syötteet.");
			return "virhe";
		}

	}

	@GetMapping("/lisaa-kategoria")
	public String lisaaKategoria(Model model) {
		model.addAttribute("kategoria", new Kategoria());

		return "lisaakategoria";

	}

	@PostMapping("/saveK")
	public String SaveK(Kategoria kategoria, Model m) {
		try {
			krepository.save(kategoria);
			return "redirect:lisaa";
		} catch (Exception e) {
			m.addAttribute("teksti", "Kategorian lisääminen ei onnistut. Tarkista syötteet.");
			return "virhe";
		}

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
	public String saveLippu(Lippu lippu, Model model) {
		try {
			trepository.lippujenVahennys(lippu.getTapahtuma().getTapahtumaId());
			lrepository.save(lippu);
			return "redirect:omat-liput";
		} catch (Exception e) {
			model.addAttribute("teksti", "Tapahtuma on täynnä");
			return "virhe";
		}

	}

	@GetMapping("/delete/{id}")
//	@PreAuthorize("hasAuthority('ADMIN')")
//	@PreAuthorize("#id == authentication.trepository.findById(id)).get().getUser().getUserId()")
	public String deleteTapahtuma(@PathVariable("id") Long id, Model model, Principal pr) {
		Long taphtuma_userId = trepository.findById(id).get().getUser().getUserId();
		User käyttäjä = urepository.findByUsername(pr.getName());
		if (taphtuma_userId == käyttäjä.getUserId() || käyttäjä.getRole() == "ADMIN") {
			trepository.deleteById(id);
			return "redirect:../tapahtumalista";
		} else {
			model.addAttribute("teksti", "Request denied.");
			return "virhe";
		}

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
