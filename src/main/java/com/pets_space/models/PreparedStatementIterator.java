package com.pets_space.models;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class PreparedStatementIterator implements Iterable<Object> {

    private Object[] array;
    private int counter = 0;
    private String resultString;

    public PreparedStatementIterator(Object[] array) {
        this.array = array;

        if (this.array.length == 0) this.resultString = "";
        else if (this.array.length > 2) this.resultString = Arrays.toString(Collections.nCopies(this.array.length, "?").toArray()).replaceAll("[\\[]|[]]", "");
        else this.resultString = "?";
    }

    @NotNull
    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return counter < array.length;
            }

            @Override
            public Object next() {
                return array[counter++];
            }
        };
    }

    public int getNumber() {
        return this.counter;
    }

    public String resultString() {
        return this.resultString;
    }
}
