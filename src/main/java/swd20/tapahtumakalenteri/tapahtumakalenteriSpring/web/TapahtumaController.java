package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.web;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;

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
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Search;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tagi;
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
	public String getBooks(Model model, Search s) {
		model.addAttribute("tapahtumat", trepository.findAll(Sort.by(Sort.Direction.ASC, "paiva")));
		model.addAttribute("kategoriat", krepository.findAll2());
		model.addAttribute("tagit", tgrepository.findAllTagit());
		model.addAttribute("search", new Search());
		return "tapahtumalista";

	}

	@GetMapping("/details/{id}")
	public String showTapahtuma(@PathVariable("id") Long id, Model model, Principal principal) {
		model.addAttribute("tp", trepository.findById(id));
		// k??ytet????n suositukset metodia l??yt??m????t sopivimmat suositukset
		model.addAttribute("tapahtumat", suositukset(trepository.findById(id).get().getTagit(), id));
		model.addAttribute("user", urepository.findByUsername(principal.getName()));
		model.addAttribute("lippu", new Lippu());
		return "tapahtumaDetails";
	}

//	 suositukset "algoritmi". Tarkoituksena on kokeilla hakea ensin kahdella t??gill??. sitten yhdell??.
//	 jos tagej?? ei ole annettu haetaan suosituksia kategoriasta.
//	 Jos suotiukset j????v??t alle 4 tapahtuman pituiseksi lis??t????n siihen lis???? tapahtumia
	public List<Tapahtuma> suositukset(List<Tagi> tagit, Long id) {
		//luodaan palautettava lista
		List<Tapahtuma> suositukset = new ArrayList<Tapahtuma>();
		
		// yritet????n ensin l??yt???? tapahtumat jossa on samat tagit kuin details sivun tapahtulla
		try {
			List<Tapahtuma> tagi2 = trepository.findByTags(trepository.findById(id).get().getTagit().get(0).getTagId(),
					trepository.findById(id).get().getTagit().get(1).getTagId(),
					trepository.findById(id).get().getTapahtumaId());
			//lis??t????n tyhj????n listaan
			for (Tapahtuma t : tagi2) {
				if (!suositukset.contains(t)) {
					suositukset.add(t);
				}
			}
		} catch (Exception e) {

		}
		//jos luotu lista j???? lyhyeksi tai sen t??ytt?? ep??onnistui yritet????n luoda suositukset listaa
		// tapahtumilla jossa on vain yksi yhteinen tagi
		if (suositukset.size() < 4) {
			try {
				//luodaan apulista joka t??ytet????n tapahtumilla jossa yksi tagi on sama
				List<Tapahtuma> tapahtumat_tagilla = new ArrayList<Tapahtuma>();
				// jos tarkasteltavalla tapahtumalla on vain yksi tagi
				if (trepository.findById(id).get().getTagit().size() == 1) {
					tapahtumat_tagilla = trepository.findByTag(
							trepository.findById(id).get().getTagit().get(0).getTagId(),
							trepository.findById(id).get().getTapahtumaId());
				} else {
					// 2 tagia, kokeillaan molemmilla erikseen
					tapahtumat_tagilla = trepository.findByTag2(
							trepository.findById(id).get().getTagit().get(0).getTagId(),
							trepository.findById(id).get().getTagit().get(1).getTagId(),
							trepository.findById(id).get().getTapahtumaId());
				}
				// lis??t????n tapahumat palautettavaan listaan
				for (Tapahtuma t : tapahtumat_tagilla) {
					if (!suositukset.contains(t)) {
						suositukset.add(t);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			//jos lista j???? edelleen lyhyeksi tai tapahtumalla ei ole ollenkaan tagej??, suositellaan k??ytt??j??lle 
			//tapahtumia samasta kategoriasta

			if (suositukset.size() < 4) {
				try {
								List<Tapahtuma> kategoria = trepository
							.findByKategoria(trepository.findById(id).get().getKategoria().getKategoriaId(), id);
					for (Tapahtuma t : kategoria) {
						if (!suositukset.contains(t)) {
							suositukset.add(t);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				//jos lista ei viel??k????n ole tarpeeksi iso suositellaan k??ytt??j??lle tapahtumia yleisesti
				List<Tapahtuma> kaikki = trepository.findAllBut(id);
				if (suositukset.size() < 4) {

					
					for (Tapahtuma t : kaikki) {
						if (!suositukset.contains(t)) {
							suositukset.add(t);
						}
					}

				}
			}
		}

		// jos lista on suurempi kun 4 yritet????n palauttaa vain 4
		try {
			return suositukset.subList(0, 4);
		} catch (Exception c) {
			return suositukset;
		}

	}

	@GetMapping("/lisaa")
	public String lisaaTapahtuma(Model model, Principal pr) {
		model.addAttribute("tp", new Tapahtuma());
		model.addAttribute("kategoriat", krepository.findAll());
		model.addAttribute("tagit", tgrepository.findAll());
		model.addAttribute("user", urepository.findByUsername(pr.getName()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		model.addAttribute("mindate", dateFormat.format(new Date()));
		return "lisaaTapahtuma";
	}

	@PostMapping("/save")
	public String Save(Tapahtuma tapahtuma, Model m) {
		try {
			trepository.save(tapahtuma);
			return "redirect:tapahtumalista";
		} catch (Exception e) {
			m.addAttribute("teksti", "Tapahtuman lis????minen ei onnistut. Tarkista sy??tteet.");
			return "virhe";
		}

	}

	@GetMapping("/lisaa-kategoria")
	public String lisaaKategoria(Model model) {
		model.addAttribute("kategoria", new Kategoria());

		return "lisaakategoria";

	}

	@GetMapping("/lisaa-tagi")
	public String lisaaTagi(Model model) {
		model.addAttribute("tagi", new Tagi());

		return "lisaatagi";

	}

	@PostMapping("/saveK")
	public String SaveK(Kategoria kategoria, Model m) {
		try {
			krepository.save(kategoria);
			return "redirect:lisaa";
		} catch (Exception e) {
			m.addAttribute("teksti", "Kategorian lis????minen ei onnistut. Tarkista sy??tteet.");
			return "virhe";
		}

	}

	@PostMapping("/saveT")
	public String SaveT(Tagi tagi, Model m) {
		try {
			tgrepository.save(tagi);
			return "redirect:lisaa";
		} catch (Exception e) {
			m.addAttribute("teksti", "Kategorian lis????minen ei onnistut. Tarkista sy??tteet.");
			return "virhe";
		}

	}

	@GetMapping("/edit/{id}")
	public String showEditTp(@PathVariable("id") Long id, Model model, Principal pr) {
		// estet????n my??s metoditasolla muokkaus
		Long taphtuma_userId = trepository.findById(id).get().getUser().getUserId();
		User k??ytt??j?? = urepository.findByUsername(pr.getName());
		if (taphtuma_userId == k??ytt??j??.getUserId() || k??ytt??j??.getRole() == "ADMIN") {
			model.addAttribute("tp", trepository.findById(id));
			model.addAttribute("kategoriat", krepository.findAll());
			model.addAttribute("tagit", tgrepository.findAll());
			return "edit-tapahtuma";
		}else {
			model.addAttribute("teksti", "Ei lupaa");
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
			model.addAttribute("teksti", "Tapahtuma on t??ynn??");
			return "virhe";
		}

	}

	@GetMapping("/delete/{id}")
//	@PreAuthorize("hasAuthority('ADMIN')")
//	@PreAuthorize("#id == authentication.trepository.findById(id)).get().getUser().getUserId()")
	public String deleteTapahtuma(@PathVariable("id") Long id, Model model, Principal pr) {
		//estet????n metoditasolla luvaton tapahtuman poisto
		Long taphtuma_userId = trepository.findById(id).get().getUser().getUserId();
		User k??ytt??j?? = urepository.findByUsername(pr.getName());
		if (taphtuma_userId == k??ytt??j??.getUserId() || k??ytt??j??.getRole() == "ADMIN") {
			trepository.deleteById(id);
			return "redirect:../tapahtumalista";
		} else {
			model.addAttribute("teksti", "Kielletty pyynt??.");
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

	// filtter??ity tapahtumalista
	@GetMapping("/tulokset/{filter}/{keyword}")
	public String findByKeyword(@PathVariable("filter") String filter, @PathVariable("keyword") String keyword,
			Model m) {
		// haetaan ja sortataan tulokset findbykeyword haulla
		m.addAttribute("tapahtumat", trepository.findByKeyword(keyword, Sort.by(Sort.Direction.ASC, "paiva")));
		m.addAttribute("kategoriat", krepository.findAll2());
		m.addAttribute("tagit", tgrepository.findAllTagit());
		m.addAttribute("filter", filter);
		m.addAttribute("keyword", keyword);
		m.addAttribute("search", new Search());
		return "tapahtumalista";
	}
	
	
	// haku hakusanalla 
	@PostMapping("/tulokset/hakusana")
	public String findBySearch(Search keyword, Model m) {
		m.addAttribute("tapahtumat",
				trepository.findBySearch(keyword.getKeyword(), Sort.by(Sort.Direction.ASC, "paiva")));
		m.addAttribute("kategoriat", krepository.findAll2());

		m.addAttribute("tagit", tgrepository.findAllTagit());
		m.addAttribute("filter", "Hakusana");
		m.addAttribute("keyword", keyword.getKeyword());
		return "tapahtumalista";
	}
	
	
	// hakutulosten sorttaus
	@PostMapping("/tulokset/{filter}/{keyword}/sort")
	public String sortWithParam(Search search, @PathVariable("filter") String filter,
			@PathVariable("keyword") String keyword, Model m) {
		
		// katsotaan mill?? perusteella k??ytt??j?? haluaa j??rjest???? taulun
		if (search.getSortby().equals("suosio")) {
			m.addAttribute("tapahtumat", trepository.findByPopularity(search.getKeyword().toLowerCase()));
		} else {
			if (search.getFilter().equals("Hakusana")) {
				m.addAttribute("tapahtumat", trepository.findBySearch(search.getKeyword(),
						Sort.by(Sort.Direction.ASC, search.getSortby())));
			} else {
				m.addAttribute("tapahtumat", trepository.findByKeyword(search.getKeyword(),
						Sort.by(Sort.Direction.ASC, search.getSortby())));
			}

		}

		m.addAttribute("kategoriat", krepository.findAll2());

		m.addAttribute("tagit", tgrepository.findAllTagit());
		m.addAttribute("filter", filter);
		m.addAttribute("keyword", keyword);
		m.addAttribute("search", new Search());

		return "tapahtumalista";
	}
	
	
	// etusivun sorttaus, sama logiikka kun yl??puolella
	@PostMapping("/sort")
	public String sortEtusivu(Search search, Model m) {

		if (search.getSortby().equals("suosio")) {
			m.addAttribute("tapahtumat", trepository.findByPopularity(""));
		} else {

			m.addAttribute("tapahtumat", trepository.findAll(Sort.by(Sort.Direction.ASC, search.getSortby())));
		}

		m.addAttribute("kategoriat", krepository.findAll2());
		m.addAttribute("tagit", tgrepository.findAllTagit());
		m.addAttribute("search", new Search());

		return "tapahtumalista";
	}

}
