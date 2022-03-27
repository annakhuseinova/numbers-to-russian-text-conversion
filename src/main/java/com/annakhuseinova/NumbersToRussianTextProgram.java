package com.annakhuseinova;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumbersToRussianTextProgram {

    public static void main(String[] args) {
        System.out.println(getKopecks(BigDecimal.valueOf(100.001)));
    }


    public static int getKopecks(BigDecimal bigDecimal){
        return bigDecimal.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(1000)).setScale(0, RoundingMode.UNNECESSARY).intValue();
    }
}
