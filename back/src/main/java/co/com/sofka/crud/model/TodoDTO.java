package co.com.sofka.crud.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TodoDTO{
    private Long listId;
    private Long id;
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(min = 2, max = 32, message = "El nombre de la tarea debe tener entre 2 y 32 caracteres")
    private String name;
    private boolean isCompleted;

    public TodoDTO() {
        super();
    }
    public TodoDTO(Long id, String name, boolean completed, Long listId) {
        this.id = id;
        this.name = name;
        this.isCompleted = completed;
        this.listId = listId;
    }

}
