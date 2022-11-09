package swd20.tapahtumakalenteri.tapahtumakalenteriSpring.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="users")
public class User {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long userId;

    // Username with unique constraint
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" )
	@JsonIgnoreProperties("user")
	private List<Tapahtuma> tapahtumat;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user" )
	@JsonIgnoreProperties("user")
	private List<Lippu> liput;
	
    
    private String role;
    
    public User() {
    }

	public User(String username, String passwordHash, String role) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}



	public List<Lippu> getLiput() {
		return liput;
	}

	public void setLiput(List<Lippu> liput) {
		this.liput = liput;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Tapahtuma> getTapahtumat() {
		return tapahtumat;
	}

	public void setTapahtumat(List<Tapahtuma> tapahtumat) {
		this.tapahtumat = tapahtumat;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}