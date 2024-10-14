package com.imperion.learn.LMS.Entities;


import com.imperion.learn.LMS.Entities.enums.Role;
import com.imperion.learn.LMS.Security.utils.PermissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString//to see user as we have seperately authenticated in post service //also chcek console once you login again
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String email;
    private String password;
    private String name;

    //one user can have only one enrolled  section
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private Enrolled enrolled;

   @ElementCollection(fetch = FetchType.EAGER)//to make sure roles are assigned as soon as user is created
    @Enumerated(EnumType.STRING)//if type is ordinal thn it will stored as numbers
    private Set<Role> roles;



    @Override//roles like admin user create seperate role entity and call here
    public Collection<? extends GrantedAuthority> getAuthorities() {

       Set<SimpleGrantedAuthority> authorities=new HashSet<>();
       roles.forEach(
               role -> {
                   Set<SimpleGrantedAuthority> permissions= PermissionMapping.getAuthorities(role);
                   authorities.addAll(permissions);
                   authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
               }
       );
       return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    //now inorder for auditing
    //we need to create auditable class with MappedSuperClass annot and entity listener and extend it here

    //also add persist keep it untouched your wish
    //enable jpa auditing anot in congig class
    @PrePersist
    void beforeSave(){

    }

    @PreRemove
    void beforeDelete(){

    }

    @PreUpdate
    void beforeUpdate(){

    }
}
