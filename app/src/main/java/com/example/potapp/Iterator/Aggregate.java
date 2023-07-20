package com.example.potapp.Iterator;

public interface Aggregate {
    public abstract Iterator iterator();
    public abstract int getLength();
    public abstract void append(Object data);
    public abstract Object get(int index);
}
