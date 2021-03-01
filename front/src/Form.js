import React, { useContext, useRef, useState } from 'react';
import {Store, HOST_API} from './App';
import {ValidateErrors } from "./ValidateErrors";
import {useToasts } from 'react-toast-notifications';
  
export const Form = () => {
  const formRef = useRef(null);
  const { dispatch, state: { todo } } = useContext(Store);
  const item = todo.item;
  const [state, setState] = useState(item);

  const validName = ValidateErrors(state);
  const { addToast } = useToasts();

  const onAdd = (event) => {

    event.preventDefault();
    const {isValid, mensaje} = validName();
    if (!isValid) {
      formRef.current.reset();
      addToast(mensaje,  { appearance: 'error' })
      return;
    };
    const request = {
      name: state.name,
      id: null,
      isCompleted: false
    };

    fetch(HOST_API + "/todo", {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        'Content-Type': 'application/json'
      }
    })
    .catch(error => {
      return console.log(error)})
      .then(response => response.json())
      .then((todo) => {
        dispatch({ type: "add-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  };

  const onEdit = (event) => {
    event.preventDefault();
    const {isValid, mensaje} = validName();
    if (!isValid) {
      formRef.current.reset();
      addToast(mensaje,  { appearance: 'error' })
      return;
    };
    const request = {
      name: state.name,
      id: item.id,
      isCompleted: item.isCompleted
    };

    fetch(HOST_API + "/todo", {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then((todo) => {
        dispatch({ type: "update-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  };

  return <form ref={formRef}>
    <input type="text" 
    name="name" 
    placeholder="¿Qué piensas hacer hoy?" 
    defaultValue={item.name} 
    onChange={(event) => {
      setState({ ...state, name: event.target.value });
    }} className="form-input"></input>
    {item.id && <button onClick={onEdit} className="form-submit">Actualizar</button>}
    {!item.id && <button onClick={onAdd} className="form-submit">Agregar</button>}

  </form>;
};

