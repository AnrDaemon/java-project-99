package hexlet.code.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails, BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Email
    @NotNull
    @NotBlank
    private String email;

    private String firstName;

    private String lastName;

    @NotBlank
    private String passwordDigest;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    /**
     * @return Password digest.
     */
    @Override
    public String getPassword() {
        return passwordDigest;
    }

    /**
     * @return email.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * @return bool
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return list
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>();
    }

    /**
     * @return bool
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return bool
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return bool
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
