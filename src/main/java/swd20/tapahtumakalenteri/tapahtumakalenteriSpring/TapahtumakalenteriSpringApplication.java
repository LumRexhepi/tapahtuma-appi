package swd20.tapahtumakalenteri.tapahtumakalenteriSpring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import javax.xml.crypto.Data;

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
	public CommandLineRunner demo(TapahtumaRepository trepository, KategoriaRepository krepository,
			TagiRepository tgrepository, UserRepository urepository, LippuRepository lrepository) {
		return (args) -> {

			Kategoria a = new Kategoria("Urheilu");
			Kategoria b = new Kategoria("Musiikki");
			Kategoria c = new Kategoria("Kulttuuri");
			Kategoria k1 = new Kategoria("Taide");
			Kategoria k2 = new Kategoria("Tiede");

			krepository.save(a);
			krepository.save(b);
			krepository.save(c);
			krepository.save(k1);
			krepository.save(k2);

			Tagi d = new Tagi("Jalkapallo");
			Tagi e = new Tagi("Nuoret");
			Tagi f = new Tagi("Hip-Hop");
			Tagi g = new Tagi("Ilmaistapahtuma");
			Tagi h = new Tagi("Joulutapahtuma");
			Tagi i = new Tagi("Valotaide");
			Tagi tj = new Tagi("Tekno");
			Tagi tr = new Tagi("Yliopisto");

			tgrepository.save(d);
			tgrepository.save(e);
			tgrepository.save(f);
			tgrepository.save(g);
			tgrepository.save(h);
			tgrepository.save(i);
			tgrepository.save(tj);
			tgrepository.save(tr);

			List<Tagi> tagit = new ArrayList<Tagi>();
			List<Tagi> tagit1 = new ArrayList<Tagi>();
			List<Tagi> tagit2 = new ArrayList<Tagi>();
			List<Tagi> tagit3 = new ArrayList<Tagi>();
			List<Tagi> tagit4 = new ArrayList<Tagi>();
			List<Tagi> tagit5 = new ArrayList<Tagi>();

			tagit.add(d);
			tagit.add(e);
			tagit1.add(h);
			tagit1.add(g);
			tagit2.add(i);
			tagit2.add(e);
			tagit3.add(tj);
			tagit3.add(e);
			tagit4.add(tr);
			tagit4.add(e);
			tagit5.add(e);
			tagit5.add(tj);

			SimpleDateFormat fdate = new SimpleDateFormat("dd.MM.yyyy");

			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			User user3 = new User("testaaja", "$2a$12$bcAPdNFkuxPKjRqSExqub.vSNU3D8DZkq57lv2/fErGWMYRGM3Ree", "USER");

			urepository.save(user1);
			urepository.save(user2);
			urepository.save(user3);

			Tapahtuma tp2 = new Tapahtuma("Joulun avausmarssi", "Reitti ja aikataulu:\n"
					+ "Paraati l??htee Senaatintorin kupeesta noin kello 16 ja kulkee Aleksanterinkatua pitkin Mannerheimintielle, josta se jatkaa Pohjoisesplanadia pitkin Unioninkadulle ja sielt?? p????ttyen takaisin Senaatintorille. Paraatin kokonaiskiertoaika on noin tunti.\n"
					+ "Aleksanterinkadun jouluvalot syttyv??t asteittain jouluparaatin l??htiess?? liikkeelle, ota siis ajoissa hyv?? paikka kadun varrelta ja tule mukaan nauttimasta taianomaisesta tunnelmasta!\n"
					+ "\n" + "Paraatiin osallistuvien henkilo??iden kokonaisma??a??ra?? on enna??tyksellinen:\n"
					+ "Paraatiin osallistuu yli 300 henkilo??a?? ja useita erilaisia kulkuneuvoja ja hevosia! Mukana on kymmenitta??in iloisia sirkus- ja satuhahmoja seka?? Lumikuningatar. Snellmaninkadun ala-asteen iloisia lapsia tonttupukuineen reilu 50 ja reippaan lutuisia koiria yli 100!",
					fdate.parse("19.11.2022"), 0.00, 15000, "Helsinki", c, tagit1, user2);

			Tapahtuma tp3 = new Tapahtuma("Talot taiteena Kalasatamassa",
					"Junonkatu tulvii valoa ja v??ri?? marraskuussa kahden illan aikana, kun taiteilija Anne Roininen toteuttaa yhdess?? Antareksenkatu kolmen ja Junonkatu nelj??n asukkaiden kanssa kerrostalojen kokoisen valotaideteoksen. Kalasataman valoteoksessa kaupunkimaisema el???? ja talojen arkkitehtuuri muuttaa muotoaan talojen asukkaiden valitsemien valojen avulla. My??s arkiel??m?? vaikuttaa teokseen: nukkumaanmenoajat, juhlat ja televisioillat saattavat muuttaa sen valojen vivahteita. Vaikuttava teos on mittakaavaltaan suuri, mutta samalla ihmisl??heinen taide-el??mys kaikille alueella liikkuville ja sen tekemiseen osallistuville. Jokaisen valon takana on inhimillisi?? valintoja, mieltymyksi??, mielentiloja ja tarinoita.\n"
							+ "Taiteilija Anne Roininen on yhdess?? kollegoidensa Mikki Noroilan, Johanna Lapinleimun ja Teo Mattilan kanssa keskustellut talojen asukkaiden kanssa heid??n valomieltymyksist????n ja sitten toteuttaneet n??m?? valoideat. Tapahtumailtoina asukkaat laittavat valot p????lle sovittuun aikaan ja teos on valmis talon hohtaessa sen asukkaiden valitsemissa v??reiss??.",
					fdate.parse("18.11.2022"), 15.00, 1500, "Helsinki", k1, tagit2, user2);

			Tapahtuma tp4 = new Tapahtuma(
					"Post Bar ??? Young Marco", "03.12.\n" + "Free before 23 / 14??? after. 3,5??? sec fee.\n" + "??????\n"
							+ "22???05\n" + "Post Bar\n" + "Kaikukatu 2",
					fdate.parse("03.12.2022"), 14.00, 1500, "Helsinki", b, tagit3, user2);

			Tapahtuma tp5 = new Tapahtuma(
					"Miten k??y kapitalismin? ", "Mil??lai??nen on tu??le??vai??suu??den ta??lous, joka ot??taa ny??kyis??t?? pa??rem??min huo??mioon maa??pal??lon ra??jal??li??set re??surs??sit? Onko ta??lous??kas??vua ja ym??p????ris??t????krii??sien hi??das??ta??mis??ta mah??dol??lis??ta yh??dis??t????? T??s??s?? ta??pah??tu??mas??sa kes??kus??tel??laan sii??t??, mik??si ja mi??ten ta??lous??j??r??jes??tel??m??m??me pi??t???? muut??tua ym??p????ris??t????krii??sien my????t??.\n"
							+ "Tulevaisuuden kest??v???? taloutta pohtivat taloustieteilij?? Sixten Korkman, Sitran Kest??vyysratkaisut-teeman johtaja Mari Pantsar, tutkijatohtori Tero Toivanen sek?? yliopistotutkija Tuuli Hirvilammi. Keskustelua luotsaa Jari Hanska.\n"
							+ "Osallistu tapahtumaan paikan p????ll?? tai seuraa keskustelua striimin v??lityksell??: https://tiedekulmamedia.helsinki.fi/???/tiede???/player/webcast???\n"
							+ "Julkaisemme keskustelusta j??lkik??teen podcastin ja tallenteen Tiedekulman Youtube-kanavalla."
							,
							fdate.parse("22.11.2022"), 0.00, 1500, "Helsinki", k2, tagit4, user2);
			
			Tapahtuma tp6 = new Tapahtuma(
					"Turku Techno Congress III", "Real Congressmen play music made with machines.\n"
							+ "Again it's time to get together in the smallest venue in town with a strobo, a mirror ball, and bunch of newcomers in chamber of techno.\n"
							+ "Congressmen of the night\n"
							+ "Jori Hulkkonen\n"
							+ "Ricardo\n"
							+ "Pete G\n"
							+ "Boot\n"
							+ "Vinyl only"
							,
							fdate.parse("16.12.2022"), 0.00, 1500, "Turku", b, tagit5, user2);

			trepository.save(tp2);
			trepository.save(tp3);
			trepository.save(tp4);
			trepository.save(tp5);
			trepository.save(tp6);

			Lippu l1 = new Lippu(tp2, user2);
			Lippu l2 = new Lippu(tp2, user2);
			Lippu l3 = new Lippu(tp2, user2);
			Lippu l4 = new Lippu(tp4, user2);
			Lippu l5 = new Lippu(tp4, user2);

			lrepository.save(l1);
			lrepository.save(l2);
			lrepository.save(l3);
			lrepository.save(l4);
			lrepository.save(l5);

			log.info("Tarkista ett?? tagit on olemassa");
			for (Tagi tagi : tagit) {
				System.out.println(i.getNimi());
			}

		};

	}

}
