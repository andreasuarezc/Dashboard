export function ValidateErrors(state) {

const validName = () => {
    let isValid = true;
    let mensaje ="";
    if (!state.name || state.name.length < 2 || state.name.length > 32 || state.name === null) {
       mensaje = "El nombre de la tarea es obligatorio, debe tener entre 2 y 32 caracteres";
      isValid = false;
    }
    return {isValid, mensaje};
  };
  return validName;
}