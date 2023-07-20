package com.example.potapp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidationTest {

    // Equivalence
    // <1 - -1, 1-12 - 5, >12 - 15

//    @Test
//    public void checkTime_1() {
//        boolean result = Validation.checkTime(-1);
//
//        assertFalse(result);
//
//        System.out.println("Check Time -1 Test Pass");
//    }
//
//    @Test
//    public void checkTime5() {
//        boolean result = Validation.checkTime(5);
//        assertTrue(result);
//
//        System.out.println("Check Time 5 Test Pass");
//    }
//
//    @Test
//    public void checkTime15() {
//        boolean result = Validation.checkTime(15);
//        assertFalse(result);
//
//        System.out.println("Check Time 15 Test Pass");
//    }

    // Boundary
    // 0,1,2,11,12,13

    @Test
    public void checkTime0() {
        boolean result = Validation.checkTime(0);

        assertFalse(result);
    }

    @Test
    public void checkTime1() {
        boolean result = Validation.checkTime(1);

        assertTrue(result);
    }

    @Test
    public void checkTime2() {
        boolean result = Validation.checkTime(2);

        assertTrue(result);
    }

    @Test
    public void checkTime11() {
        boolean result = Validation.checkTime(11);

        assertTrue(result);
    }

    @Test
    public void checkTime12() {
        boolean result = Validation.checkTime(12);

        assertTrue(result);
    }

    @Test
    public void checkTime13() {
        boolean result = Validation.checkTime(13);

        assertFalse(result);
    }

}