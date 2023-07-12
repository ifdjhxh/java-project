package com.example.demo1;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestMain {

    @org.junit.Test
    public void test(){
        List<Integer> lst = new ArrayList<>();
        lst.add(5);
        lst.add(7);
        lst.add(3);

        HelloController helloController = new HelloController();
        // calcWidth
        assertEquals(helloController.calcWidth(0,20), 580);
        assertEquals(helloController.calcWidth(1,20), 280);
        assertEquals(helloController.calcWidth(5,20), 730);
        assertEquals(helloController.calcWidth(8,34), 430);
        assertEquals(helloController.calcWidth(14,34), 2230);

        //calcHeight
        assertEquals(helloController.calcHeight(0), 10);
        assertEquals(helloController.calcHeight(1), 60);
        assertEquals(helloController.calcHeight(5), 110);
        assertEquals(helloController.calcHeight(23), 210);

        //isNumber
        assertEquals(helloController.makeReverseArray(lst),"3, 7, 5");
        lst.add(97);
        assertEquals(helloController.makeReverseArray(lst), "97, 3, 7, 5");
        lst.add(2);
        assertEquals(helloController.makeReverseArray(lst), "2, 97, 3, 7, 5");
        assertEquals(helloController.makeReverseArray(new ArrayList<Integer>()), "");

        //arrayToString
        int[] arr1 = {4, 7, 2, 87, 23, 76, -72, 0};
        int[] arr2 = {5,7,2,87,23,76,-72,0};
        int[] arr3 = {};
        assertEquals(helloController.arrayToString(arr1), "4, 7, 2, 87, 23, 76, -72, 0");
        assertEquals(helloController.arrayToString(arr2), "5, 7, 2, 87, 23, 76, -72, 0");
        assertEquals(helloController.arrayToString(arr3), "");

        //isNumber
        assertTrue(helloController.isNumber("23455"));
        assertTrue(helloController.isNumber("-34565754654390"));
        assertTrue(helloController.isNumber("234553453456910"));
        assertFalse(helloController.isNumber("2-345534534569"));
        assertFalse(helloController.isNumber("4350ы"));
    }
}
