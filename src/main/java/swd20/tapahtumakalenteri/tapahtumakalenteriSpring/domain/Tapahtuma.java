package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;




import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Table(name = "Tapahtuma")
@Entity
public class Tapahtuma {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tapahtumaId;
	private String name;
	private String kuvaus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paiva;
	private double hinta;
	private int lippujaJaljella;
	private String paikka;
	
	
	@ManyToOne
	@JsonIgnoreProperties("tapahtumat")
	@JoinColumn(name = "kategoriaId")
	private Kategoria kategoria;
	

	
	@ManyToOne
	@JsonIgnoreProperties("tapahtumat")
	@JoinColumn(name = "userId")
	private User user;

	@ManyToMany(mappedBy = "tapahtumat" )
	@JsonIgnoreProperties("tapahtumat")
	private List<Tagi> tagit = new ArrayList<Tagi>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tapahtuma" )
	@JsonIgnoreProperties("tapahtuma")
	private List<Lippu> liput;

	
	
	public Tapahtuma() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Tapahtuma( String name, String kuvaus, Date paiva, double hinta, int lippujaJaljella,
			String paikka, Kategoria kategoria, List<Tagi> tagit, User user) {
		super();
		this.name = name;
		this.kuvaus = kuvaus;
		this.paiva = paiva;
		this.hinta = hinta;
		this.lippujaJaljella = lippujaJaljella;
		this.paikka = paikka;
		this.kategoria = kategoria;
		this.tagit = tagit;
		this.user = user;
	}


	

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<Lippu> getLiput() {
		return liput;
	}


	public void setLiput(List<Lippu> liput) {
		this.liput = liput;
	}


	public List<Tagi> getTagit() {
		return tagit;
	}


	public void setTagit(List<Tagi> tagit) {
		this.tagit = tagit;
	}


	public Long getTapahtumaId() {
		return tapahtumaId;
	}


	public void setTapahtumaId(Long tapahtumaId) {
		this.tapahtumaId = tapahtumaId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getKuvaus() {
		return kuvaus;
	}


	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}


	public Date getPaiva() {
		return paiva;
	}


	public void setPaiva(Date paiva) {
		this.paiva = paiva;
	}


	public double getHinta() {
		return hinta;
	}


	public void setHinta(double hinta) {
		this.hinta = hinta;
	}


	public int getLippujaJaljella() {
		return lippujaJaljella;
	}


	public void setLippujaJaljella(int lippujaJaljella) {
		this.lippujaJaljella = lippujaJaljella;
	}


	public String getPaikka() {
		return paikka;
	}


	public void setPaikka(String paikka) {
		this.paikka = paikka;
	}


	public Kategoria getKategoria() {
		return kategoria;
	}


	public void setKategoria(Kategoria kategoria) {
		this.kategoria = kategoria;
	}


	@Override
	public String toString() {
		return "Tapahtuma [id=" + tapahtumaId + ", name=" + name + ", kuvaus=" + kuvaus + ", paiva=" + paiva + ", hinta=" + hinta
				+ ", lippujaJaljella=" + lippujaJaljella + ", paikka=" + paikka + "]";
	}


}
