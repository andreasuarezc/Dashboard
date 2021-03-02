package co.com.sofka.crud.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
public class TodoListDTO {
    private Long id;
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(min = 2, max = 32, message = "El nombre de la tarea debe tener entre 2 y 32 caracteres")
    private String name;
    private Set<TodoDTO> items = new HashSet<>();

    public TodoListDTO(){
        super();
    }

    public TodoListDTO(Long id, String name, Set<TodoDTO> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }
}
