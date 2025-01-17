package componetsLab.websiteComponents.entity;

import componetsLab.websiteComponents.entity.Visit;
import componetsLab.websiteComponents.entity.enumeration.Specializations;
import componetsLab.websiteComponents.entity.enumeration.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "insurance")
    private Boolean insurance=false;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private Specializations specialization;

    @Enumerated(EnumType.STRING)
    @Column(name="user_role")
    private UserRole userRole;

    @ManyToMany
    @JoinTable(name = "user_visit", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "visit_id"))
    private Set<Visit> visits = new HashSet<>();
}
