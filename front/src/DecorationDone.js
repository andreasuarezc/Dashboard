import React from 'react';

export function DecorationDone(currentList, onChange, onDelete, onEdit) {
  const decorationDone = {
    textDecoration: 'line-through'
  };
  return <div className="contenedor">
    <table>
      <thead>
        <tr>
          <td className="form-label" >ID</td>
          <td className="form-label">Tarea</td>
          <td className="form-label">¿Completado?</td>
        </tr>
      </thead>
      <tbody>
        {currentList.map((todo) => {
          return <tr key={todo.id} style={todo.completed ? decorationDone : {}}>
            <td>{todo.id}</td>
            <td>{todo.name}</td>
            <td><input type="checkbox" defaultChecked={todo.completed} onChange={(event) => onChange(event, todo)}></input></td>
            <td><button onClick={() => onDelete(todo.id)} className="form-submit2">Eliminar</button></td>
            <td><button onClick={() => onEdit(todo)} className="form-submit2">Editar</button></td>
          </tr>;
        })}

      </tbody>
    </table>
  </div>;
}