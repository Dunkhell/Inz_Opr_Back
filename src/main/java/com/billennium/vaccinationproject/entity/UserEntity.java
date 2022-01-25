package com.billennium.vaccinationproject.entity;

import com.billennium.vaccinationproject.security.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name="user")
public class UserEntity implements org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "username"
    )
    private String username;

    @Column(
            name = "password"
    )
    private String password;

    @Column(
            name = "role"
    )
    private AppUserRole applicationUserRole;

    @Column(
            name = "is_account_non_expired"
    )
    private boolean isAccountNonExpired;

    @Column(
            name = "is_account_non_locked"
    )
    private boolean isAccountNonLocked;

    @Column(
            name = "is_credentials_non_expired"
    )
    private boolean isCredentialsNonExpired;

    @Column(
            name = "is_enabled"
    )
    private boolean isEnabled;

    @Column(
            name = "email",
            unique = true
    )
    @Email
    private String email;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "user_details_id",
            referencedColumnName = "id"
    )
    private UserDetailsEntity userDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return applicationUserRole.getGrantedAuthority();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}