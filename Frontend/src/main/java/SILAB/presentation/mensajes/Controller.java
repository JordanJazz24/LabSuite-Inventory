package SILAB.presentation.mensajes;


import SILAB.logic.ITarget;
import SILAB.logic.Message;
import SILAB.logic.Service;

import java.util.ArrayList;

public class Controller implements ITarget {
    View view;
    Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        Service.instanceListener().setTarget(this);
        Service.instanceListener().start();
    }

    public void limpiar(){
        model.setMessages(new ArrayList<>());
        model.commit(Model.Message);
    }

    public void stop(){
        Service.instanceListener().stop();
    }



    public void deliver(Message message){
        model.messages.add(message);
        model.commit(Model.Message);
    }    
}
