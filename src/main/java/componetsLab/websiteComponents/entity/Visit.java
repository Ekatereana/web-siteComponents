package componetsLab.websiteComponents.entity;

import componetsLab.websiteComponents.entity.enumeration.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name="visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private long id;

    @Column(name = "date")
    private Date date;

    @ManyToMany
    @JoinTable(name="user_visit", joinColumns=@JoinColumn(name = "visit_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @Column(name = "confirmation")
    private Boolean confirmation;

    @Column(name="diagnose")
    private String diagnose;

    @Column(name = "treatment")
    private String treatment;

    @Enumerated(value = EnumType.STRING)
    @Column(name="status")
    private Status status;

    public void addUser(User user){
        users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return id == visit.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
