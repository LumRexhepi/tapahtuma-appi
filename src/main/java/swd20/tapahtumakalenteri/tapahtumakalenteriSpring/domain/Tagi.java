package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;



import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Table(name = "Tagi")
@Entity
public class Tagi {
	@Id
	@Column(name = "tagId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagId;
	@Column(name = "nimi")
	private String nimi;
	
	
	
	
	@Column(name = "tapahtumat")
	@ManyToMany(mappedBy = "tagit" )
	private List<Tapahtuma> tapahtumat;

	public Tagi(String nimi) {
		super();
		this.nimi = nimi;

	}

	public Tagi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Tapahtuma> getTapahtumat() {
		return tapahtumat;
	}

	public void setTapahtumat(List<Tapahtuma> tapahtumat) {
		this.tapahtumat = tapahtumat;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

}