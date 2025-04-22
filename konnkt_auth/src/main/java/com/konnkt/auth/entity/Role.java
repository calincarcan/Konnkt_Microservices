package com.konnkt.auth.entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(unique = true, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}
