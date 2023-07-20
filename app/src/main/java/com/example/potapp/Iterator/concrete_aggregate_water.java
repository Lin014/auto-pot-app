package com.example.potapp.Iterator;

public class concrete_aggregate_water implements Aggregate{
    //The concrete aggregate of the data of water.
    private History_Water[] his_data;
    private int last = 0;
    public concrete_aggregate_water(int max){
        this.his_data = new History_Water[max];
    }
    public int getLength() {
        return last;
    }

    public void append(Object data) {
        this.his_data[last] = new History_Water((int) data);
        last++;
    }

    public Object get(int index) {
        return his_data[index].get_data();
    }

    public Iterator iterator() {
        return new concrete_iterator(this);
    }
}
