package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name = "Lippu")
@Entity
public class Lippu {
	@Id
	@Column(name = "lippuid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lippuId;
	
	@Column(name = "kaytetty")
	private boolean kaytetty = false;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name =   "tapahtumaId")
	@JsonIgnoreProperties("liput")
	private Tapahtuma tapahtuma;
	
	
	@ManyToOne
	@JsonIgnoreProperties("liput")
	@JoinColumn(name = "userId")
	private User user;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date aikaleima = new Date();

	public Lippu( Tapahtuma tapahtuma, User user) {
		super();
		this.tapahtuma = tapahtuma;
		this.user = user;
	}

	public Long getLippuId() {
		return lippuId;
	}

	public void setLippuId(Long lippuId) {
		this.lippuId = lippuId;
	}

	public boolean isKaytetty() {
		return kaytetty;
	}

	public void setKaytetty(boolean kaytetty) {
		this.kaytetty = kaytetty;
	}

	public Tapahtuma getTapahtuma() {
		return tapahtuma;
	}

	public void setTapahtuma(Tapahtuma tapahtuma) {
		this.tapahtuma = tapahtuma;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAikaleima() {
		return aikaleima;
	}

	public void setAikaleima(Date aikaleima) {
		this.aikaleima = aikaleima;
	}

	public Lippu() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}