import React, {createContext } from 'react';
import { Form } from './Form';
import { List } from "./List";
import { StoreProvider } from './StoreProvider';
import { ToastProvider} from 'react-toast-notifications';
  

export const HOST_API = "http://localhost:8080/api"
export const initialState = {
  todo: { list: [], item: {} }
};

export const Store = createContext(initialState)
function App() {
  return <StoreProvider>
    <ToastProvider>
    <Form/>
  </ToastProvider>
    <List/>
  </StoreProvider>
}

export default App;
