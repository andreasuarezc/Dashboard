package co.com.sofka.crud.controller;

import co.com.sofka.crud.model.TodoDTO;
import co.com.sofka.crud.service.TodoService;
import co.com.sofka.crud.model.Todo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "api/todos")
    public List<TodoDTO> list(){
        List<Todo> todos = service.list();
        return todos.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping(value = "api/todo")
    public ResponseEntity<String> save(@Valid @RequestBody TodoDTO todoDto) throws ParseException {
        Todo todo = convertToEntity(todoDto);
        Todo todoSaved = service.save(todo);
        convertToDto(todoSaved);
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

    @PutMapping(value = "api/todo")
    public TodoDTO update(@RequestBody TodoDTO todoDto) throws ParseException {
        Todo todo = convertToEntity(todoDto);
        if(todo.getId() != null){
            return convertToDto(service.save(todo));
        }
        throw new RuntimeException("No existe el id para actualizar");
    }

    @DeleteMapping(value = "api/{id}/todo")
    public void delete(@PathVariable("id")Long id){
        service.delete(id);
    }

    @GetMapping(value = "api/{id}/todo")
    public TodoDTO get(@PathVariable("id") Long id){
        return convertToDto(service.getById(id));
    }

    private TodoDTO convertToDto(Todo todo) {
        return modelMapper.map(todo, TodoDTO.class);
    }

    private Todo convertToEntity(TodoDTO todoDto) throws ParseException {
        return modelMapper.map(todoDto, Todo.class);
    }

}
