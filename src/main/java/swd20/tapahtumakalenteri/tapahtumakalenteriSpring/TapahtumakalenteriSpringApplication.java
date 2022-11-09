package swd20.tapahtumakalenteri.tapahtumakalenteriSpring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Kategoria;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.KategoriaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Lippu;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.LippuRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tagi;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TagiRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tapahtuma;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TapahtumaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.User;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.UserRepository;

@SpringBootApplication
public class TapahtumakalenteriSpringApplication {
	private static final Logger log = LoggerFactory.getLogger(TapahtumakalenteriSpringApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TapahtumakalenteriSpringApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(TapahtumaRepository trepository, KategoriaRepository krepository, TagiRepository tgrepository, UserRepository urepository, LippuRepository lrepository) {
		return (args) -> {

			Kategoria a = new Kategoria("Urheilu");
			Kategoria b = new Kategoria("Musiikki");
			Kategoria c = new Kategoria("Kulttuuri");
			
			krepository.save(a);
			krepository.save(b);
			krepository.save(c);
			
	
			Tagi d = new Tagi("Jalkapallo");
			Tagi e = new Tagi("Nuoret");
			Tagi f = new Tagi("Hip-Hop");
			Tagi g = new Tagi("Ilmaistapahtuma");
			
			tgrepository.save(d);
			tgrepository.save(e);
			tgrepository.save(f);
			tgrepository.save(g);

			List<Tagi> tagit = new ArrayList<Tagi>();
			
			tagit.add(d);
			tagit.add(e);
			tagit.add(f);
			tagit.add(g);
			
			
			
			SimpleDateFormat fdate = new SimpleDateFormat("dd.MM.yyyy");

			
			
			
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			
			urepository.save(user1);
			urepository.save(user2);
			
			
//			Tapahtuma tp1 = new Tapahtuma(null, null, null, 0, 0, null, a, null, user1);
			Tapahtuma tp2 = new Tapahtuma("Disko", "Disko yökerhossa", fdate.parse("02.11.2022") , 15.00, 1500, "Helsinki", c, tagit, user1);
			
//			trepository.save(tp1);
			trepository.save(tp2);
			
			Lippu l1 = new Lippu(tp2, user1);
			
			lrepository.save(l1);
			
			log.info("Tarkista että tagit on olemassa");
			for (Tagi i : tagit) {
				System.out.println(i.getNimi());
			}

		};
	
	}

}
