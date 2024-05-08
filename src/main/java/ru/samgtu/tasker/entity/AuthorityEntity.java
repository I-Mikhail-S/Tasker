package ru.samgtu.tasker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "authorities")
public class AuthorityEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private Integer id;
    private String authority;

    public AuthorityEntity(String authority) {
        this.authority = authority;
    }
}
