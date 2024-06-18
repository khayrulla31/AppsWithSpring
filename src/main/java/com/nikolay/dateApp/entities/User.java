package com.nikolay.dateApp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, message = "it must have greater than 3 ")
    private String userName;

    @Size(min = 5, message = "it must have greater than 3 ")
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)//, cascade = CascadeType.ALL)//закоментил это и перестало удаляться из ROLE
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            //inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
    private List<Role> roles = new ArrayList<>();
   // private Set<Role> roles;// = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Person person;

}
