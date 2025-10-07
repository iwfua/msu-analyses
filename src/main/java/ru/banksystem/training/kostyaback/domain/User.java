package ru.banksystem.training.kostyaback.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(unique = true, nullable = false)
    private String username;

    @Size(max = 255)
    @Column(nullable = true)
    private String firstName;

    @Size(max = 255)
    @Column(nullable = true)
    private String lastName;
    
    @Column(nullable = false)
    @Size(max = 100)
    private String password;

    @Size(max = 70)
    @Column(nullable = false)
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(name = "account_non_expired")
    @Builder.Default
    private boolean accountNonExpired = true;
    
    @Column(name = "account_non_locked")
    @Builder.Default
    private boolean accountNonLocked = true;
    
    @Column(name = "credentials_non_expired")
    @Builder.Default
    private boolean credentialsNonExpired = true;
    
    @Column(name = "enabled")
    @Builder.Default
    private boolean enabled = true;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
