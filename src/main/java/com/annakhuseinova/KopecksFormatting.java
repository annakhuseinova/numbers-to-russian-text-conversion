package com.annakhuseinova;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Scanner;

public class KopecksFormatting {

    public static void main(String[] args) {
        System.out.println("Введите количество копеек");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()){
            int kopecks = scanner.nextInt();
            if (kopecks == 0){
                System.out.println("ноль копеек");
            } else if (kopecks < 10){
                System.out.println(underTenKopecksTextMap().get(kopecks));
            } else if (kopecks < 20){
                System.out.println(elevenToNineteenKopecksTextMap().get(kopecks));
            } else if (kopecks < 100){
                int numberOfTens = Integer.parseInt(String.valueOf(kopecks).substring(0, 1));
                int numberOfIntegers = Integer.parseInt(String.valueOf(kopecks).substring(1, 2));
                String text = String.format("%s %s", tensToTextKopecksMap().get(numberOfTens), underTenKopecksTextMap().get(numberOfIntegers));
                System.out.println(text);
            }
        }
    }

    public static String getKopecksTextRepresentation(BigDecimal bigDecimal){
        int kopecks = getKopecks(bigDecimal);
        if (kopecks == 0){
            return "ноль копеек";
        } else if (kopecks < 10){
            return underTenKopecksTextMap().get(kopecks);
        } else if (kopecks < 20){
            return elevenToNineteenKopecksTextMap().get(kopecks);
        } else if (kopecks < 100){
            int numberOfTens = Integer.parseInt(String.valueOf(kopecks).substring(0, 1));
            int numberOfIntegers = Integer.parseInt(String.valueOf(kopecks).substring(1, 2));
            return String.format("%s %s", tensToTextKopecksMap().get(numberOfTens), underTenKopecksTextMap().get(numberOfIntegers));
        }
        throw new RuntimeException("Введено некорректное число копеек");
    }

    public static int getKopecks(BigDecimal bigDecimal){
       return bigDecimal.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.UNNECESSARY).intValue();
    }

    private static Map<Integer, String> underTenKopecksTextMap(){
        return Map.of(
                1, "одна копейка",
                2, "две копейки",
                3, "три копейки",
                4, "четыре копейки",
                5, "пять копеек",
                6, "шесть копеек",
                7, "семь копеек",
                8, "восемь копеек",
                9, "девять копеек"
        );
    }

    private static Map<Integer, String> elevenToNineteenKopecksTextMap(){
        return Map.of(
                11, "одиннадцать копеек",
                12, "двенадцать копеек",
                13, "тринадцать копеек",
                14, "четырнадцать копеек",
                15, "пятнадцать копеек",
                16, "шестнадцать копеек",
                17,  "семнадцать копеек",
                18, "восемнадцать копеек",
                19, "девятнадцать  копеек"
        );
    }

    private static Map<Integer, String> tensToTextKopecksMap(){
        return Map.of(
                1, "десять",
                2, "двадцать",
                3, "тридцать",
                4, "сорок",
                5, "пятьдесят",
                6, "шестьдесят",
                7, "семьдесят",
                8, "восемьдесять",
                9, "девяносто"
        );
    }
}
