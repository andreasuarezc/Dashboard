package co.com.sofka.crud.service;

import co.com.sofka.crud.repository.TodoRepository;
import co.com.sofka.crud.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public List<Todo> list(){
        return repository.findAll();
    }

    public Todo save(Todo todo){
        return repository.save(todo);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Todo getById(Long id){
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

}
