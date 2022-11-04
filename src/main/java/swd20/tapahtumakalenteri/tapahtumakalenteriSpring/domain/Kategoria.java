package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Kategoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long kategoriaid;
	private String nimi;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kategoria" )
	@JsonIgnoreProperties("kategoriat")
	private List<Tapahtuma> tapahtumat;
	

	
	public List<Tapahtuma> getTapahtumat() {
		return tapahtumat;
	}



	public void setTapahtumat(List<Tapahtuma> tapahtumat) {
		this.tapahtumat = tapahtumat;
	}



	public Kategoria() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Kategoria (String nimi) {
		super();
		this.nimi = nimi;
	}



	public Long getKategoriaId() {
		return kategoriaid;
	}



	public void setKategoriaId(Long id) {
		this.kategoriaid = id;
	}



	public String getNimi() {
		return nimi;
	}



	public void setNimi(String nimi) {
		this.nimi = nimi;
	}



	@Override
	public String toString() {
		return "Kategoria [id=" + kategoriaid + ", nimi=" + nimi + "]";
	}
	
	

}
