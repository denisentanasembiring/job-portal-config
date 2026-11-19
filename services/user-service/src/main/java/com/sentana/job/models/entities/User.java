package com.sentana.job.models.entities;

import com.fasterxml.jackson.annotation.*;
import com.sentana.job.author.Author;
import com.sentana.job.utils.IDResolver;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "users", schema = "auth")
public class User extends Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "account_expired", nullable = false)
    private boolean accountNonExpired = false;

    @Column(name = "account_locked", nullable = false)
    private boolean accountNonLocked = false;

    @Column(name = "credentials_expired", nullable = false)
    private boolean credentialsNonExpired = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = IDResolver.class, scope = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_role_id"), nullable = false)
    private Role role;
}
