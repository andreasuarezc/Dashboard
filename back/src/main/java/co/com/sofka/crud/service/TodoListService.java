package co.com.sofka.crud.service;

import co.com.sofka.crud.NotFoundIdException;
import co.com.sofka.crud.model.Todo;
import co.com.sofka.crud.model.TodoDTO;
import co.com.sofka.crud.model.TodoList;
import co.com.sofka.crud.model.TodoListDTO;
import co.com.sofka.crud.repository.TodoListRepository;
import co.com.sofka.crud.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TodoListService {
    public static final String NO_FAULT_ID = "No existe el id de la lista";
    @Autowired
    private TodoListRepository todoListRepository;
    @Autowired
    private TodoRepository todoRepository;

    public Set<TodoDTO> getToDosByListId(Long id) {
        return todoListRepository.findById(id)
                .orElseThrow(() -> new NotFoundIdException(NO_FAULT_ID))
                .getToDos().stream()
                .map(item -> new TodoDTO(item.getId(), item.getName(), item.isCompleted(), id))
                .collect(Collectors.toSet());
    }

    public TodoDTO addNewToDoByListId(Long listId, TodoDTO aToDoModel) {
        TodoList listToDo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundIdException(NO_FAULT_ID));
        var toDo = new Todo();

        toDo.setCompleted(aToDoModel.isCompleted());
        toDo.setName(Objects.requireNonNull(aToDoModel.getName()));
        toDo.setId(aToDoModel.getId());

        //addition new to-do
        listToDo.getToDos().add(toDo);

        var listUpdated = todoListRepository.save(listToDo);
        //last item
        var lastToDo = listUpdated.getToDos()
                .stream()
                .max(Comparator.comparingInt(item -> item.getId().intValue()))
                .orElseThrow();
        aToDoModel.setId(lastToDo.getId());
        aToDoModel.setListId(listId);
        return aToDoModel;
    }

    public TodoDTO updateAToDoByListId(Long listId, TodoDTO aToDoModel) {
        var listToDo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundIdException(NO_FAULT_ID));

        //edit to-do
        for(var item : listToDo.getToDos()){
            if(item.getId().equals(aToDoModel.getId())){
                item.setCompleted(aToDoModel.isCompleted());
                item.setName(Objects.requireNonNull(aToDoModel.getName()));
                item.setId(Objects.requireNonNull(aToDoModel.getId()));
            }
        }

        todoListRepository.save(listToDo);

        return aToDoModel;
    }


    public TodoListDTO newListToDo(TodoListDTO aToDoListModel) {
        TodoList listToDo = new TodoList();
        listToDo.setName(Objects.requireNonNull(aToDoListModel.getName()));
        var id = todoListRepository.save(listToDo).getId();
        aToDoListModel.setId(id);
        return aToDoListModel;
    }

    public Set<TodoListDTO> getAllListToDos() {
        return StreamSupport
                .stream(todoListRepository.findAll().spliterator(), false)
                .map(toDoList -> {
                    var listDto = toDoList.getToDos()
                            .stream()
                            .map(item -> new TodoDTO(item.getId(), item.getName(), item.isCompleted(), toDoList.getId()))
                            .collect(Collectors.toSet());
                    return new TodoListDTO(toDoList.getId(), toDoList.getName(), listDto);
                })
                .collect(Collectors.toSet());
    }

    public void deleteListById(Long listId){
        var listToDo = todoListRepository.findById(listId)
                .orElseThrow(() -> new NotFoundIdException(NO_FAULT_ID));
        todoListRepository.delete(listToDo);
    }

    public void deleteAToDoById(Long id) {
        TodoList todoList =todoListRepository.findAll().iterator().next();
        todoList.getToDos().remove(id);
        todoListRepository.save(todoList);
    }
}
