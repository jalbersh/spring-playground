package com.galvanize.jalbersh.springplayground.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RomanConversionTest {
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
    private final static Map<Integer, String> rules = new HashMap<>();
    private final static Map<Integer, Integer> dividers = new HashMap<>();
    private final static int NUM_RULES = 13;

    @Before
    public void setup() {
        rules.put(1, "I");
        rules.put(4, "IV");
        rules.put(5, "V");
        rules.put(9, "IX");
        rules.put(10, "X");
        rules.put(40, "XL");
        rules.put(50, "L");
        rules.put(90, "XC");
        rules.put(100, "C");
        rules.put(400, "CD");
        rules.put(500, "D");
        rules.put(900, "CM");
        rules.put(1000, "M");

        dividers.put(0, 1);
        dividers.put(1, 4);
        dividers.put(2, 5);
        dividers.put(3, 9);
        dividers.put(4, 10);
        dividers.put(5, 40);
        dividers.put(6, 50);
        dividers.put(7, 90);
        dividers.put(8, 100);
        dividers.put(9, 400);
        dividers.put(10, 500);
        dividers.put(11, 900);
        dividers.put(12, 1000);

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    public static String integerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    public static String getRoman(int num) {
        String roman = "";
//        System.out.println("num = "+num);
        while (num != 0) {
            for (int i = NUM_RULES-1;i >= 0 && num != 0;i--) {
//                System.out.println("i="+i);
                Integer divisor = dividers.get(i);
//                System.out.println("divisor="+divisor);
                if (divisor <= num) {
                    String digit = rules.get(divisor);
//                    System.out.println("digit=" + digit);
                    int multiplier = num / divisor;
                    int remainder = num % divisor;
//                    System.out.println("multiplier=" + multiplier);
//                    System.out.println("remainder=" + remainder);
                    if (multiplier < 4) {
                        if (remainder < 4) {
                            for (int j = 0; j < multiplier; j++) {
                                roman += digit;
                            }
                            num -= multiplier * divisor;
//                        } else if (remainder == divisor-1) { // this is a wierd case
//                            roman += rules.get(dividers.get(i - 1)) + rules.get(dividers.get(i + 1));
//                            num -= (4 + divisor);
                        } else {
                            num -= multiplier * divisor;
                            roman += digit;
                        }
                    }
                    else {
                        String newDigit = rules.get(dividers.get(i + 1));
//                        System.out.println("newDigit="+newDigit+"; roman="+roman);
                        roman += (digit + newDigit);
                        num -= multiplier * divisor;
                    }
//                    System.out.println("num = " + num);
                }
            }
        }
        return roman;
    }

    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

    @Test
    public void testRomans() throws Exception {
        assertThat(getRoman(5), equalTo("V"));
        assertThat(getRoman(10), equalTo("X"));
        assertThat(getRoman(20), equalTo("XX"));
        assertThat(getRoman(3), equalTo("III"));
        assertThat(getRoman(4), equalTo("IV"));
        assertThat(getRoman(9), equalTo("IX"));
        assertThat(getRoman(40), equalTo("XL"));
        assertThat(getRoman(49), equalTo("XLIX"));
        assertThat(getRoman(99), equalTo("XCIX"));
        assertThat(getRoman(499), equalTo("CDXCIX"));
        assertThat(getRoman(999), equalTo("CMXCIX"));
        assertThat(getRoman(3000), equalTo("MMM"));
    }

    @Test
    public void testIntegerToRomanNumeral() throws Exception {
        assertThat(integerToRomanNumeral(5), equalTo("V"));
        assertThat(integerToRomanNumeral(10), equalTo("X"));
        assertThat(integerToRomanNumeral(20), equalTo("XX"));
        assertThat(integerToRomanNumeral(3), equalTo("III"));
        assertThat(integerToRomanNumeral(4), equalTo("IV"));
        assertThat(integerToRomanNumeral(9), equalTo("IX"));
        assertThat(integerToRomanNumeral(49), equalTo("XLIX"));
        assertThat(integerToRomanNumeral(99), equalTo("XCIX"));
        assertThat(integerToRomanNumeral(499), equalTo("CDXCIX"));
        assertThat(integerToRomanNumeral(999), equalTo("CMXCIX"));
        assertThat(integerToRomanNumeral(3999), equalTo("MMMCMXCIX"));
    }

    @Test
    public void testToRoman() throws Exception {
        assertThat(toRoman(5), equalTo("V"));
        assertThat(toRoman(10), equalTo("X"));
        assertThat(toRoman(20), equalTo("XX"));
        assertThat(toRoman(3), equalTo("III"));
        assertThat(toRoman(4), equalTo("IV"));
        assertThat(toRoman(9), equalTo("IX"));
        assertThat(toRoman(49), equalTo("XLIX"));
        assertThat(toRoman(99), equalTo("XCIX"));
        assertThat(toRoman(499), equalTo("CDXCIX"));
        assertThat(toRoman(999), equalTo("CMXCIX"));
        assertThat(toRoman(3999), equalTo("MMMCMXCIX"));
    }
}
