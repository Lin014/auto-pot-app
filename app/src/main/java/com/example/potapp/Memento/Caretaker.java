package com.example.potapp.Memento;

public class Caretaker {
    //Store the Memento, the maximum number is 30.
    private Memento[] memento = new Memento[30];
    private int index = 0;
    public void setMemento(Memento m) {
        memento[index] = m;
        index++;
    }
    public Memento getMemento(int i) {
        return memento[i];
    }
    public void reset(){
        index = 0;
    }
    public int getIndex(){
        return index;
    }
    public void setIndex(int i){
        index = i;
    }
    public int getMax(){return memento.length;}
}
