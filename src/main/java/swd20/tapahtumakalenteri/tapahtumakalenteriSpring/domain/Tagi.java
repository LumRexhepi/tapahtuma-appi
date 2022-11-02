package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Tagi {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long tagId;
	private String nimi;
	
	 @ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable ( 
			name  = "tapahtuman_tagit",
			joinColumns = {@JoinColumn(name="tagId")},
			inverseJoinColumns = {@JoinColumn (name = "tapahtumaId")}
			
			)
	
	private List<Tapahtuma> tapahtumat;
	
	
	
	public Tagi(String nimi) {
		super();
		this.nimi = nimi;
		
	}
	public Tagi() {
		super();
		// TODO Auto-generated constructor stub
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