package co.com.sofka.crud.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(min = 2, max = 32, message = "El nombre de la tarea debe tener entre 2 y 32 caracteres")
    private String name;
    private boolean isCompleted;


}
