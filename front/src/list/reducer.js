import { actionType } from "./events"

export const listReducer = () => {
    const action = {};

    action[actionType.LIST_FINDED] = (state, action) => {
        return { ...state, list: { elements: action.list } }
    };

    action[actionType.LIST_CREATED] = (state, action) => {
        const list = state.list.elements;
        list.push(action.element);
        return { ...state, list: { elements: list, element: {} } };
    };

    action[actionType.LIST_DELETED] = (state, action) => {
        const list = state.list.elements.filter((element) => {
            return element.id !== action.listId;
        });
        return { ...state, list: { elements: list } }
    };
    
    return action;
}