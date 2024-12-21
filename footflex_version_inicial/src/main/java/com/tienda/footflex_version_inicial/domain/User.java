
package com.tienda.footflex_version_inicial.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", unique = true)
    private Long id;

    @Column(unique = true)
    private String name;
    
    private String email;
    private String password;
    private String role;
    private Long phone;
    private String address;
}
