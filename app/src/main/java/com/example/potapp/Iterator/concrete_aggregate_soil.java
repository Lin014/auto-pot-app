package com.example.potapp.Iterator;

public class concrete_aggregate_soil implements Aggregate{
    //The concrete aggregate of the data of soil.
    private History_Soil[] his_data;
    private int last = 0;
    public concrete_aggregate_soil(int max){
        this.his_data = new History_Soil[max];
    }
    public int getLength() {
        return last;
    }

    public void append(Object data) {
        this.his_data[last] = new History_Soil((int) data);
        last++;
    }

    public Object get(int index) {
        return his_data[index].get_data();
    }

    public Iterator iterator() {
        return new concrete_iterator(this);
    }
}
