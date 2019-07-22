package com.ayon.fastnumbers;

import java.util.List;

public class Demo {
    public static void main(String[] args) {
        ListQueue ls = new ListQueue();
        for ( int i = 1; i < 5; i++ ) {
            ls.enqueue(i);
        }
        System.out.println(ls.dequeue());
        System.out.println(ls.dequeue());
        System.out.println(ls.dequeue());
        System.out.println(ls.dequeue());
        System.out.println(ls.dequeue());
        System.out.println(ls.dequeue());
    }
}
