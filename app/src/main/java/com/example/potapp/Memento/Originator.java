package com.example.potapp.Memento;

import com.example.potapp.Iterator.Iterator;

public class Originator {
    //Create and restore Memento.
    private Iterator iterator_time,iterator_soil,iterator_light,iterator_water;
    public void setState(Iterator it_t,Iterator it_s,Iterator it_l,Iterator it_w) {
        this.iterator_time = it_t;
        this.iterator_soil = it_s;
        this.iterator_light = it_l;
        this.iterator_water = it_w;
    }
    public Iterator getState_time() {
        return iterator_time;
    }
    public Iterator getState_soil() {
        return iterator_soil;
    }
    public Iterator getState_light() {
        return iterator_light;
    }
    public Iterator getState_water() {
        return iterator_water;
    }
    public Memento createMemento() {
        return new Memento(iterator_time,iterator_soil,iterator_light,iterator_water);
    }
    public void restoreMemento(Memento m) {
        this.setState(m.getState_time(),m.getState_soil(),m.iterator_light(),m.iterator_water());
    }
}
