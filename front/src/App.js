import React from 'react';
import {StoreProvider} from "./store";
import {ListView} from "./list/ListView";
import {FormView} from "./list/FormView";
import { ToastProvider} from 'react-toast-notifications';

function App() {
  return <StoreProvider>
    <div className="title">
      <h3>Dashboard</h3>
    </div>
    <div className="container">
      <div className="content">
      <ToastProvider>
        <FormView />
        </ToastProvider>
        <ListView />
      </div>
    </div>
  </StoreProvider>
}

export default App;
