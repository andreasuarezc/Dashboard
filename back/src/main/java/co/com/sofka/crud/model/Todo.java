package co.com.sofka.crud.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private boolean isCompleted;


}
