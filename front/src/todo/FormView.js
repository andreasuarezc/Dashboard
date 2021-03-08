import React, { useContext, useRef, useState } from 'react';
import {consumer} from "./consumer";
import {events} from "./events";
import {Store} from "../store";
import {ValidateErrors } from "../ValidateErrors";
import { useToasts } from 'react-toast-notifications';

export const ToDoForm = ({ listId, todo }) => {
    const formRef = useRef(null);
    const { dispatch } = useContext(Store);
    const item = todo.item[listId] ? todo.item[listId] : {};
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
            completed: false
        };

        consumer.save(listId, request)
        .then(response => response.json())
        .then((result) => {
                    dispatch(events.saved(listId, result));
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
            completed: item.completed
        };

        consumer.update(listId, request).then((response) => {
            if(response.ok){
                response.json().then((result) => {
                    dispatch(events.updated(listId, result));
                    setState({ name: "" });
                    formRef.current.reset();
                });
            }
           
        });
    }



    return <form ref={formRef}>
        <input
            type="text"
            name="name"
            placeholder="¿Qué piensas hacer?"
            defaultValue={item.name}
            onChange={(event) => {
                setState({ ...state, name: event.target.value })
            }}  ></input>
        {item.id && <button onClick={onEdit}>Actualizar</button>}
        {!item.id && <button onClick={onAdd}>Crear</button>}
    </form>
}