/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SILAB.presentation.mensajes;


import SILAB.logic.Message;

import java.util.ArrayList;
import java.util.List;

public class Model extends java.util.Observable {
    List<Message> messages;

    public Model() {
       messages= new ArrayList<>();
    }


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit(Model.Message);
    }
    
    public void commit(int properties){
        this.setChanged();
        this.notifyObservers(properties);        
    } 
    
    public static int Message=1;
}
