package co.com.sofka.crud.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class TodoList {
    @Id
    @Column(name= "list_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY,cascade=javax.persistence.CascadeType.REMOVE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Set<Todo> toDos;
}
