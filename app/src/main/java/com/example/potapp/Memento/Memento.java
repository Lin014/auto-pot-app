package com.example.potapp.Memento;

import com.example.potapp.Iterator.Iterator;

public class Memento {
    //Save the Iterator, the system can restore the previous Iterator by calling Memento.
    private Iterator iterator_time,iterator_soil,iterator_light,iterator_water;
    public Memento(Iterator it_t,Iterator it_s,Iterator it_l,Iterator it_w) {
        this.iterator_time = it_t;
        this.iterator_soil = it_s;
        this.iterator_light = it_l;
        this.iterator_water = it_w;
    }
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
    public Iterator iterator_light() {
        return iterator_light;
    }
    public Iterator iterator_water() {
        return iterator_water;
    }
}
