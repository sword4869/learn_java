package com.hello.world;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class Test implements Serializable{
    public static void main(String[] args) throws ClassNotFoundException {
        List<List<Integer>> list = new ArrayList<>();
        List<List<Integer>> list2 = new ArrayList<List<Integer>>();

    }
}
