package se.lexicon.g33.andreas.data;

import se.lexicon.g33.andreas.model.Person;
import se.lexicon.g33.andreas.model.Todo;

import java.util.Arrays;

public class TodoItems {
    private static Todo[] toDoArray = new Todo[0]; // Array with all toDoItems objects
    private static Todo[] toDoEmpty = new Todo[0]; //For clearing toDoArray

    public static int size(){
        return toDoArray.length;
    } // To know how big the array with toDoItems are
    public static Todo[] findAll(){
        return toDoArray; //Access all toDoItems
    }
    public static Todo findById(int toDoId){
        int answer = 0;
        for (int i = 0; i< toDoArray.length; i++){
            if (toDoArray[i].getTodo_id() == (toDoId)){
                answer = i;// Saves the wanted toDoItems place in the Array so we return the right one
                break;
            }
        }
        return toDoArray[answer]; //Returns a specific wanted toDoItem
    }
    public static Todo add(String description, boolean done){
        int toDoId = TodoSequencer.nextTodoId();
        Todo newTodo = new Todo(toDoId, description);//Create a toDoItem
        newTodo.setDone(done);
        Todo[] newArray = Arrays.copyOf(toDoArray, toDoArray.length + 1);//make big array
        newArray[newArray.length-1] = newTodo;//Put the new toDoItem in the bigger array
        toDoArray = newArray;//Makes the toDoarray same as the bigger array
        return newTodo;//Returns new toDoItem
    }
    public static void clear(){
        if (toDoArray == null) {
            // System.out.println("Todo list is not initialized, now initialized.");
        } else {
            // System.out.println("Todo list had been emptied!");
        }
        TodoSequencer.reset();// personId reset to value 0
        toDoArray = toDoEmpty;//Empty toDoarray
    }
    public static boolean remove(int toDoId) {
        boolean isDeleted = false;
        if (toDoArray.length == 0) {
            System.out.println("Array is empty");
        } else if ((itemExist(toDoId))) {
            Todo[] newArray = new Todo[toDoArray.length - 1];
            for (int i = 0, j = 0; i < toDoArray.length; i++, j++) {
                if (toDoArray[i].getTodo_id() == (toDoId)) {
                    --j;// toDoItem is removed
                    isDeleted = true;//Remove action finished
                } else {
                    newArray[j] = toDoArray[i];// To save those toDoItems that should not be removed
                }
            }
            toDoArray = newArray;// Not removed toDoItems sent to toDoItemarray
        } else {
            System.out.println("Could not find name to remove");
        }

        return isDeleted;//Tells if item was removed
    }
    public static boolean itemExist(int toDoId) {
        boolean IsExisting = false;
        for (int i = 0; i < toDoArray.length; i++) {
            if (toDoArray[i].getTodo_id() == (toDoId)) {
                IsExisting = true;//Wanted item exist
                break;
            }
        }
        return IsExisting;// Tells if wanted item exist
    }
    public static Todo[] findByDoneStatus(boolean doneStatus) {
        boolean answer = false;
        Todo[] newArray = new Todo[toDoArray.length - 1];// we need a new array containing whats done
        int newLength = 0;// we might need to take away empty places in new array
        if (toDoArray.length == 0) {
            System.out.println("Array is empty");
        } else {
            for (int i = 0, j = 0; i < toDoArray.length; i++, j++) {
                if (toDoArray[i].getDone() != (doneStatus)) {
                    --j;// makes new array shorter because done status not what was asked for
                } else {
                    newArray[j] = toDoArray[i];// saves items that have asked for done status
                    answer = true;
                    newLength++;
                }
            }
        }
        if (answer) System.out.println("No matches found");
        Todo[] myArray = Arrays.copyOf(newArray,newLength);// we dont want to send array with potentiel empty places
        return myArray;
    }
    public static Todo[] findByAssigne(int personId) {
        boolean answer = false;
        Todo[] newArray = new Todo[toDoArray.length - 1];// we need a new array containing toDoItem for asked for assigne
        int newLength = 0;// we might need to take away empty places in new array
        if (toDoArray.length == 0) {
            System.out.println("Array is empty");
        } else {
            for (int i = 0, j = 0; i < toDoArray.length; i++, j++) {
                if (toDoArray[i].getAssignee_id().getPersonId() != (personId)) {
                    --j;// makes new array shorter because assigne is not the right one
                } else {
                    newArray[j] = toDoArray[i];// saves items that have the right assigne
                    answer = true;
                    newLength++;
                }
            }
        }
        if (!answer) System.out.println("No matches found");
        Todo[] myArray = Arrays.copyOf(newArray,newLength);// we dont want to send array with potentiel empty places
        return myArray;
    }
    public static Todo[] findByAssigne(Person assignee) {
        boolean answer = false;
        Todo[] newArray = new Todo[toDoArray.length - 1];// we need a new array containing toDoItem for asked for assigne
        int newLength = 0;// we might need to take away empty places in new array
        if (toDoArray.length == 0) {
            System.out.println("Array is empty");
        } else {
            for (int i = 0, j = 0; i < toDoArray.length; i++, j++) {
                if (toDoArray[i].getAssignee_id() != (assignee)) {
                    --j;// makes new array shorter because assignee is not the right one
                } else {
                    newArray[j] = toDoArray[i];// saves items that have the right assigne
                    answer = true;
                    newLength++;
                }
            }
        }
        if (!answer) System.out.println("No matches found");
        Todo[] myArray = Arrays.copyOf(newArray,newLength);// we dont want to send array with potentiel empty places
        return myArray;
    }
    public static Todo[] findUnassignedTodoItems() {
        boolean answer = false;
        Todo[] newArray = new Todo[toDoArray.length];// we need a new array containing toDoItem that have no assigne
        int newLength = 0;// we might need to take away empty places in new array
        if (toDoArray.length == 0) {
            System.out.println("Array is empty");
        } else {
            for (int i = 0, j = 0; i < toDoArray.length; i++, j++) {
                if (toDoArray[i].getAssignee_id() != null) {
                    --j;// makes new array shorter because someone is assigned to item
                } else {
                    newArray[j] = toDoArray[i];// saves items that have no assigne
                    answer = true;
                    newLength++;
                }
            }
        }
        if (!answer) System.out.println("No matches found");
        Todo[] myArray = Arrays.copyOf(newArray,newLength);// we dont want to send array with potentiel empty places
        return myArray;
    }
}