package com.miniprj.interviewcode.model.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miniprj.interviewcode.model.role.Role;

@Entity
@Table(name = "m_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "no_telp" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    @Size(max = 15)
	    @Column(name="no_telp")
	    private String noTelp;

	    @NaturalId
	    @NotBlank
	    @Size(max = 40)
	    @Email
	    private String email;

	    @NotBlank
	    @Size(max = 100)
	    private String password;
	 
		
		@Column(name="is_login")
		private Boolean isLogin;
	
		
		@Column(name="created_at")
		private Instant createdAt;

	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "r_user_role",
	            joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id"))
	    private Set<Role> roles = new HashSet<>();

	    public User() {
	    	
	    }

	    public User(String noTelp,String password, Boolean isLogin,  Instant createdAt) {
	        this.noTelp = noTelp;
	        this.password = password;
	        this.isLogin = isLogin;
	        this.createdAt = createdAt;
	        
	    }
	    
	    public User(String noTelp, String email, String password, Boolean isLogin,  Instant createdAt) {
	        this.noTelp = noTelp;
	        this.email = email;
	        this.password = password;
	        this.isLogin = isLogin;
	        this.createdAt = createdAt;
	        
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }


	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
	    
		public String getNoTelp() {
			return noTelp;
		}

		public void setNoTelp(String noTelp) {
			this.noTelp = noTelp;
		}
		
		
		public Boolean getIsLogin() {
			return isLogin;
		}

		public void setIsLogin(Boolean isLogin) {
			this.isLogin = isLogin;
		}

		public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public Set<Role> getRoles() {
	        return roles;
	    }

	    public void setRoles(Set<Role> roles) {
	        this.roles = roles;
	    }
	    
	    
	
	
}