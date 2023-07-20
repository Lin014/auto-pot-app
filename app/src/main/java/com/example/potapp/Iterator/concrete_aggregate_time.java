package com.example.potapp.Iterator;

public class concrete_aggregate_time implements Aggregate{
    //The concrete aggregate of the data of time.
    private History_Time[] his_time;
    private int last = 0;
    public concrete_aggregate_time(int max){
        this.his_time = new History_Time[max];
    }
    public void append(Object data) {
        this.his_time[last] = new History_Time((String) data);
        last++;
    }

    public Object get(int index) {
        return his_time[index].get_data();
    }
    public int getLength() {
        return last;
    }
    public Iterator iterator() {
        return new concrete_iterator(this);
    }
}
