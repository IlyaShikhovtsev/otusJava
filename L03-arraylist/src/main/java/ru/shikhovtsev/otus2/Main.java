package ru.shikhovtsev.otus2;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        var src = new DIYArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            src.add(i);
        }

        System.out.println("Original list: " + src);
        Collections.addAll(src, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36);
        System.out.println("After addAll(): " + src);
        System.out.println();

        var dest = new DIYArrayList<>(35);
        for (int i = 36; i >= 0; i--) {
            dest.add(i);
        }

        System.out.println("Original list: " + src);
        System.out.println("Dest list: " + dest);
        Collections.copy(dest, src);
        System.out.println("After copy, dest: " + src);
        System.out.println();

        src.clear();
        for (int i = 0; i < 30; i++) {
            src.add((int) (Math.random() * 100) * i);
        }
        System.out.println("Before sort(): " + src);
        Collections.sort(src, Integer::compare);
        System.out.println("After sort(): " + src);
    }
}
