package com.sapient.wfs.common.collections;

import java.io.Serializable;
import java.util.Arrays;

public class CustomList<T> implements Serializable {
    private static final long serialVersionUID = 8683452581122892189L;
    private Object[] myStore;
    private int actSize = 0;

    public CustomList() {
        myStore = new Object[10];
    }

    public T get(int index) {
        if (index < actSize) {
            return (T) myStore[index];
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public boolean add(T obj) {
        if (myStore.length - actSize <= 5) {
            increaseListSize();
        }
        myStore[actSize++] = obj;
        return true;
    }

    public T remove(int index) {
        if (index < actSize) {
            T obj = (T) myStore[index];
            myStore[index] = null;
            int tmp = index;
            while (tmp < actSize) {
                myStore[tmp] = myStore[tmp + 1];
                myStore[tmp + 1] = null;
                tmp++;
            }
            actSize--;
            return obj;
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }

    }

    public int size() {
        return actSize;
    }

    private void increaseListSize() {
        myStore = Arrays.copyOf(myStore, myStore.length * 2);
        System.out.println("\nNew length: " + myStore.length);
    }


}
