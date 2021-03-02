package co.com.sofka.crud.repository;

import co.com.sofka.crud.model.Todo;
import co.com.sofka.crud.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Iterable<Todo> findAllTodosById(Long id);
}
