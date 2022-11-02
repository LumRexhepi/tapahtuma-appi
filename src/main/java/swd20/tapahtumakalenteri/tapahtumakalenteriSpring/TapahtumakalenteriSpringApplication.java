package swd20.tapahtumakalenteri.tapahtumakalenteriSpring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Kategoria;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.KategoriaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tagi;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TagiRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.Tapahtuma;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.TapahtumaRepository;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.User;
import swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain.UserRepository;

@SpringBootApplication
public class TapahtumakalenteriSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(TapahtumakalenteriSpringApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(TapahtumaRepository trepository, KategoriaRepository krepository, TagiRepository tgrepository, UserRepository urepository) {
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

			
			trepository.save(new Tapahtuma(null, null, null, 0, 0, null, a, null));
			trepository.save(new Tapahtuma("Disko", "Disko yökerhossa", fdate.parse("02.11.2022") , 15.00, 1500, "Helsinki", c, tagit));
			
			
			urepository.save(new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
			urepository.save(new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN"));
			
//			Iterator<Tagi> itr = tagit.iterator();
//			  
//	        // check element is present or not. if not loop will
//	        // break.
//	        while (itr.hasNext()) {
//	            System.out.println(itr.next());}
		};
	
	}

}
