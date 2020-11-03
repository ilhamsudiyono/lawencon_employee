package com.miniprj.interviewcode.auth.sercurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miniprj.interviewcode.model.user.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idUser;

    private String noTelp;

    
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long idUser, String noTelp, String email,String password,Collection<? extends GrantedAuthority> authorities) {
        this.idUser = idUser;
        this.noTelp = noTelp;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getNoTelp(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return idUser;
    }


    public String getEmail() {
        return email;
    }

    

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(idUser, that.idUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idUser);
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return noTelp;
	}
}
