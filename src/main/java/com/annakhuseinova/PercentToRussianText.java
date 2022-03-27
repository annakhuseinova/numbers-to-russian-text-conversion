package com.annakhuseinova;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.nonNull;

public class PercentToRussianText {

    public static void main(String[] args) {
        System.out.println("Введите число процентов");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextBigDecimal()){
            System.out.println(getPercentsWithFractionAsRussianText(scanner.nextBigDecimal()));
        }
    }

    private static String getPercentsWithFractionAsRussianText(BigDecimal percentsWithFraction){
        int percentNumber = percentsWithFraction.intValue();
        String fractionAsText = concatFractionToText(percentsWithFraction);
        if (percentNumber < 10){
            String percentText = underTenPercentsMap().get(percentNumber);
            return String.format("%s %s %s", percentText, fractionAsText, "процентов");
        } else if (percentNumber < 20){
            String percentText = tenToNineteenPercentsMap().get(percentNumber);
            return String.format("%s %s %s", percentText, fractionAsText, "процентов");
        } else if (percentNumber < 100){
            String tensText = tensToTextMap().get(parseNumberOfGivenOrder(percentNumber, 0, 1));
            String integersText = underTenPercentsMap().get(parseNumberOfGivenOrder(percentNumber, 1, 2));
            String percentText = String.format("%s %s",  tensText, nonNull(integersText) ? integersText: "целых");
            return String.format("%s %s %s", percentText, fractionAsText, "процентов");
        }
        throw new RuntimeException();
    }

    private static String concatFractionToText(BigDecimal bigDecimal) {
        int fraction = getFraction(bigDecimal);
        int numberOfHundreds = 0;
        int numberOfTens = 0;
        int numberOfIntegers = 0;
        if (fraction > 99){
            numberOfHundreds = parseNumberOfGivenOrder(fraction,0, 1);
            numberOfTens =  parseNumberOfGivenOrder(fraction,1, 2);
            numberOfIntegers =  parseNumberOfGivenOrder(fraction,2, 3);
        } else if (fraction > 9 && fraction < 100){
            numberOfTens = parseNumberOfGivenOrder(fraction, 0, 1);
            numberOfIntegers = parseNumberOfGivenOrder(fraction, 1, 2);
        } else if (fraction > 0 && fraction < 10){
            numberOfIntegers = parseNumberOfGivenOrder(fraction, 0, 1);
        }
        boolean isNumberOfIntegersZero = numberOfIntegers == 0;
        boolean isNumberOfTensZero = numberOfTens == 0;
        boolean isNumberOfHundredsZero = numberOfHundreds == 0;
        if (isNumberOfIntegersZero && isNumberOfTensZero && isNumberOfHundredsZero) {
            return "ноль тысячных";
        } else if (isNumberOfHundredsZero && isNumberOfTensZero){
            return numberUnderTenOutOfThousand().get(numberOfIntegers);
        } else if (isNumberOfHundredsZero && fraction > 10 && fraction < 20){
            return elevenToNineteenOfFractionMap().get(fraction);
        } else if (isNumberOfHundredsZero && fraction == 10){
            return "десять тысячных";
        } else if (isNumberOfHundredsZero && fraction > 19){
            String tensAsText = tensToTextMap().get(parseNumberOfGivenOrder(fraction, 0, 1));
            if (numberOfIntegers != 0){
                return String.format("%s %s", tensAsText, numberUnderTenOutOfThousand().get(numberOfIntegers));
            }
            return String.format("%s %s", tensAsText, "тысячных");
        } else if (isNumberOfTensZero && isNumberOfIntegersZero){
            String hundredsAsText = hundredsToTextMap().get(numberOfHundreds);
            return String.format("%s %s", hundredsAsText, "тысячных");
        } else if (isNumberOfTensZero) {
            String hundredsAsText = hundredsToTextMap().get(numberOfHundreds);
            return String.format("%s %s", hundredsAsText, numberUnderTenOutOfThousand().get(numberOfIntegers));
        } else if (isNumberOfIntegersZero){
            String hundredsAsText = hundredsToTextMap().get(numberOfHundreds);
            return String.format("%s %s %s", hundredsAsText, tensToTextMap().get(numberOfTens), "тысячных");
        } else  {
            String hundredsAsText = hundredsToTextMap().get(numberOfHundreds);
            int tens = parseNumberOfGivenOrder(fraction, 1, 3);
            if (tens > 10 && tens < 20){
                return String.format("%s %s", hundredsAsText, elevenToNineteenOfFractionMap().get(tens));
            }
            return String.format("%s %s %s", hundredsAsText, tensToTextMap().get(numberOfTens), numberUnderTenOutOfThousand().get(numberOfIntegers));
        }
    }

    private static int parseNumberOfGivenOrder(int number, int startIndexInclusive, int endIndexExclusive){
        return Integer.parseInt(String.valueOf(number).substring(startIndexInclusive, endIndexExclusive));
    }

    public static Map<Integer, String> numberUnderTenOutOfThousand(){
        return Map.of(
                1, "одна тысячная",
                2, "две тысячных",
                3, "три тысячных",
                4, "четыре тысячных",
                5, "пять тысячных",
                6, "шесть тысячных",
                7, "семь тысячных",
                8, "восемь тысячных",
                9, "девять тысячных"
        );
    }

    public static Map<Integer, String> underTenPercentsMap(){
        return Map.of(
                1, "одна целая",
                2, "две целых",
                3, "три целых",
                4, "четыре целых",
                5, "пять целых",
                6, "шесть целых",
                7, "семь целых",
                8, "восемь целых",
                9, "девять целых"
        );
    }

    public static Map<Integer, String> tenToNineteenPercentsMap(){
        return Map.of(
                10, "десять целых",
                11, "одиннадцать целых",
                12, "двенадцать целых",
                13, "тринадцать целых",
                14, "четырнадцать целых",
                15, "пятнадцать целых",
                16, "шестнадцать целых",
                17, "семнадцать целых",
                18, "восемнадцать целых",
                19, "девятнадцать целых"
        );
    }

    public static Map<Integer, String> elevenToNineteenOfFractionMap(){
        return Map.of(
                11, "одиннадцать тысячных",
                12, "двенадцать тысячных",
                13, "тринадцать тысячных",
                14, "четырнадцать тысячных",
                15, "пятнадцать тысячных",
                16, "шестнадцать тысячных",
                17, "семнадцать тысячных",
                18, "восемнадцать тысячных",
                19, "девятнадцать тысячных"
        );
    }

    private static Map<Integer, String> tensToTextMap(){
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

    private static Map<Integer, String> hundredsToTextMap(){
        return Map.of(
                1, "сто",
                2, "двести",
                3, "триста",
                4, "четыреста",
                5, "пятьсот",
                6, "шестьсот",
                7, "семьсот",
                8, "восемьсот",
                9, "девятьсот"
        );
    }

    public static int getFraction(BigDecimal bigDecimal){
        return bigDecimal.remainder(BigDecimal.ONE).multiply(BigDecimal.valueOf(1000)).setScale(0, RoundingMode.UNNECESSARY).intValue();
    }
}
