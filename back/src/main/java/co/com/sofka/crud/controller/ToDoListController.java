package co.com.sofka.crud.controller;

import co.com.sofka.crud.NotFoundIdException;
import co.com.sofka.crud.model.TodoDTO;
import co.com.sofka.crud.model.TodoListDTO;
import co.com.sofka.crud.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {

    @Autowired
    private TodoListService toDoListService;
    
    @PostMapping(value = "api/todolist")
    public ResponseEntity<String> newListToDo(@Valid @RequestBody TodoListDTO todo){
        toDoListService.newListToDo(todo);
        return ResponseEntity.ok("Tarea añadida correctamente");
    }

    @PostMapping(value = "api/{listId}/todo")
    public ResponseEntity<String> addNewToDoByListId(@PathVariable("listId") Long listId,@Valid @RequestBody TodoDTO todo){
        toDoListService.addNewToDoByListId(listId, todo);
        return ResponseEntity.ok("Tarea añadida correctamente");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @PutMapping(value = "api/{listId}/todo")
    public TodoDTO updateAToDoByListId(@PathVariable("listId") Long listId, @RequestBody TodoDTO todo){
        if(todo.getId() != null){
            return toDoListService.updateAToDoByListId(listId, todo);
        }
        throw new NotFoundIdException("No existe el id para actualizar");
    }

    @GetMapping(value = "api/list")
    public Iterable<TodoListDTO> getAllListToDos(){
        return toDoListService.getAllListToDos();
    }

    @GetMapping(value = "api/{listId}/todos")
    public Iterable<TodoDTO> getToDosByListId(@PathVariable("listId") Long listId){
        return toDoListService.getToDosByListId(listId);
    }

    @DeleteMapping(value = "api/{id}/todolist")
    public void deleteListById(@PathVariable("id") Long id){
         toDoListService.deleteListById(id);
    }

    @DeleteMapping(value = "api/{id}/todo")
    public void deleteAToDoById(@PathVariable("id")Long id){
        toDoListService.deleteAToDoById(id);
    }

}