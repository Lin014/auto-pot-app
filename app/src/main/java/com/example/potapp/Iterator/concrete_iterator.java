package com.example.potapp.Iterator;

public class concrete_iterator implements Iterator{
    private Aggregate aggregate;
    private int index;
    public concrete_iterator(Aggregate agg) {
        this.aggregate = agg;
        this.index = 0;
    }
    public boolean hasNext() {
        if (index < aggregate.getLength()) {
            return true;
        } else {
            return false;
        }
    }
    public Object next() {
        Object his;
        his = aggregate.get(index);
        index++;
        return his;
    }
    public void reset(){
        index = 0;
    }
}
