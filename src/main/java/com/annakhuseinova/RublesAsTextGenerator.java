package com.annakhuseinova;

import java.util.Map;

public class RublesAsTextGenerator {

    public static void main(String[] args) {
        for (int rubles = 1; rubles < 10000000; rubles++) {
            System.out.println(getRublesTextRepresentation(rubles));
        }
    }

    /**
     * Метод конвертации числа рублей в текст рассчитан на числа от 1 до 9999999
     * */
    public static String getRublesTextRepresentation(int rubles){
        if (isZeroOrLess(rubles)){
            throw new RuntimeException("Number of rubles cannot be 0 or less than 0");
        } else if (isLessThanTen(rubles)){
            return convertRublesUnderTenToText(rubles);
        } else if (isLessThanTwenty(rubles)){
            return convertRublesTenToNineteenToText(rubles);
        } else if (isLessThanHundred(rubles)){
            return convertRublesUnderHundredToText(rubles);
        } else if (isLessThanThousand(rubles)){
            return convertHundredsOfRublesToText(rubles);
        } else if (isLessThanTenThousand(rubles)){
            return convertThousandsOfRublesToText(rubles);
        } else if (isLessThanHundredThousand(rubles)){
            return convertTensOfThousandsToText(rubles);
        } else if (isLessThanMillion(rubles)){
            return convertHundredsOfThousandsToText(rubles);
        } else if (isLessThanTenMillion(rubles)){
            return convertMillionsToText(rubles);
        }
        throw new RuntimeException("Введено недопустимое число рублей");
    }

    private static int parseNumberOfGivenOrder(int number, int startIndexInclusive, int endIndexExclusive){
        return Integer.parseInt(String.valueOf(number).substring(startIndexInclusive, endIndexExclusive));
    }

    private static String convertRublesUnderTenToText(int rubles){
        return numbersUnderTenToRublesTextMap().get(rubles);
    }

    private static String convertRublesTenToNineteenToText(int rubles){
        return tenToNineteenToTextWithRublesMap().get(rubles);
    }

    private static String convertRublesUnderHundredToText(int rubles){
        if (rubles > 10 && rubles < 20){
            return tenToNineteenToTextWithRublesMap().get(rubles);
        }
        int numberOfTens = parseNumberOfGivenOrder(rubles, 0, 1);
        int numberOfIntegers = parseNumberOfGivenOrder(rubles, 1, 2);
        String numberOfTensAsString = tensToTextMap().get(numberOfTens);
        String numberOfIntegersAsString = numbersUnderTenToRublesTextMap().get(numberOfIntegers);
        return numberOfIntegers == 0 ? String.format("%s %s", numberOfTensAsString, "рублей") :
                String.format("%s %s", numberOfTensAsString, numberOfIntegersAsString);
    }

    private static String convertHundredsOfRublesToText(int rubles) {
        int numberOfHundreds = parseNumberOfGivenOrder(rubles, 0,1);
        int numberOfTens = parseNumberOfGivenOrder(rubles, 1, 2);
        int numberOfIntegers = parseNumberOfGivenOrder(rubles, 2, 3);
        boolean isNumberOfHundredsZero = numberOfHundreds == 0;
        boolean isNumberOfIntegersZero = numberOfIntegers == 0;
        boolean isNumberOfTensZero = numberOfTens == 0;
        String hundredsAsText = hundredsToText().get(numberOfHundreds);
        if (isNumberOfHundredsZero && isNumberOfTensZero && isNumberOfIntegersZero){
            return "ноль рублей";
        } else if (isNumberOfIntegersZero && isNumberOfTensZero) {
            return String.format("%s %s", hundredsAsText, "рублей");
        } else if (isNumberOfIntegersZero){
            return String.format("%s %s %s", hundredsAsText, tensToTextMap().get(numberOfTens), "рублей");
        } else if (isNumberOfTensZero) {
            return String.format("%s %s", hundredsAsText, numbersUnderTenToRublesTextMap().get(numberOfIntegers));
        } else {
            int tens = parseNumberOfGivenOrder(rubles, 1, 3);
            if (tens > 10 && tens < 20){
                return String.format("%s %s", hundredsAsText, tenToNineteenToTextWithRublesMap().get(tens));
            }
            return String.format("%s %s %s", hundredsAsText, tensToTextMap().get(numberOfTens), numbersUnderTenToRublesTextMap().get(numberOfIntegers));
        }
    }

    private static String convertThousandsOfRublesToText(int rubles){
        int numberOfThousands = parseNumberOfGivenOrder(rubles, 0, 1);
        int numberOfHundreds = parseNumberOfGivenOrder(rubles, 1, 2);
        int numberOfTens = parseNumberOfGivenOrder(rubles, 2, 3);
        int numberOfIntegers = parseNumberOfGivenOrder(rubles, 3, 4);
        String numberOfThousandsAsText = thousandsToTextMap().get(numberOfThousands);
        if (numberOfThousands == 0 && numberOfHundreds == 0 && numberOfTens == 0 && numberOfIntegers == 0){
            return "ноль рублей";
        } else if (numberOfHundreds == 0 && numberOfTens == 0 && numberOfIntegers == 0){
            return String.format("%s %s", numberOfThousandsAsText, "рублей");
        } else if (numberOfHundreds == 0 && numberOfTens != 0){
            int tensOfRubles = parseNumberOfGivenOrder(rubles, 2, 4);
            return String.format("%s %s", numberOfThousandsAsText, convertRublesUnderHundredToText(tensOfRubles));
        } else if (numberOfHundreds == 0){
            int rublesUnderTen = parseNumberOfGivenOrder(rubles, 2, 4);
            return String.format("%s %s", numberOfThousandsAsText, convertRublesUnderTenToText(rublesUnderTen));
        }
        int hundredsOfRubles = parseNumberOfGivenOrder(rubles, 1, 4);
        return String.format("%s %s", numberOfThousandsAsText, convertHundredsOfRublesToText(hundredsOfRubles));
    }

    private static String convertTensOfThousandsToText(int rubles){
        int numberOfHundreds = parseNumberOfGivenOrder(rubles, 2, 3);
        int numberOfTens = parseNumberOfGivenOrder(rubles, 3, 4);
        int numberOfIntegers = parseNumberOfGivenOrder(rubles, 4,5);
        int thousands = parseNumberOfGivenOrder(rubles, 0, 2);
        String thousandsAsText;
        if (rubles % 10000 == 0) {
            int numberOfThousands = parseNumberOfGivenOrder(rubles, 0, 1);
            return String.format("%s %s", tensToTextMap().get(numberOfThousands), "тысяч рублей");
        }
        if (thousands == 0){
            return convertHundredsOfRublesToText(parseNumberOfGivenOrder(rubles, 2, 5));
        } else if (thousands < 10){
            thousandsAsText = thousandsToTextMap().get(thousands);
        } else if (thousands < 20){
            thousandsAsText = tenToNineteenMap().get(thousands).concat(" тысяч");
        } else {
            int numberOfTensOfThousands = Integer.parseInt(String.valueOf(rubles).substring(0, 1));
            if (thousands % 10 == 0){
                thousandsAsText = tensToTextMap().get(numberOfTensOfThousands).concat(" тысяч");
            } else {
                int numberOfIntegersOfThousands = Integer.parseInt(String.valueOf(rubles).substring(1, 2));
                thousandsAsText = String.format("%s %s", tensToTextMap().get(numberOfTensOfThousands), thousandsToTextMap().get(numberOfIntegersOfThousands));
            }
        }
        if (rubles % 1000 == 0){
            return thousandsAsText.concat(" рублей");
        } else if (numberOfHundreds == 0 && numberOfTens == 0){
            return String.format("%s %s", thousandsAsText, convertRublesUnderTenToText(numberOfIntegers));
        } else if (numberOfHundreds == 0){
            int hundreds = parseNumberOfGivenOrder(rubles, 3, 5);
            return String.format("%s %s", thousandsAsText, convertRublesUnderHundredToText(hundreds));
        }
        return String.format("%s %s", thousandsAsText,  convertHundredsOfRublesToText(parseNumberOfGivenOrder(rubles, 2, 5)));
    }


    private static String convertHundredsOfThousandsToText(int rubles){
        int hundredsOfThousands = parseNumberOfGivenOrder(rubles, 0, 3);
        String hundredsOfThousandsAsText;
        int numberOfHundredsOfThousands = parseNumberOfGivenOrder(rubles, 0, 1);
        if (isNumberOfHundredsOfThousandsRound(rubles)){
            return  hundredsToText().get(numberOfHundredsOfThousands).concat(" тысяч рублей");
        } else if (hundredsOfThousands == 0){
            int tensOfThousandsRubles = parseNumberOfGivenOrder(rubles, 2, 6);
            return convertThousandsOfRublesToText(tensOfThousandsRubles);
        } else if (hundredsOfThousands % 100 == 0){
            int hundreds = parseNumberOfGivenOrder(rubles, 3, 6);
            int numberOfHundreds = parseNumberOfGivenOrder(rubles, 3, 4);
            int numberOfTens = parseNumberOfGivenOrder(rubles, 4,5);
            int numberOfIntegers = parseNumberOfGivenOrder(rubles, 5, 6);
            hundredsOfThousandsAsText = hundredsToText().get(numberOfHundredsOfThousands).concat(" тысяч");
            if (numberOfHundreds == 0 && numberOfTens == 0 && numberOfIntegers == 0){
                return String.format("%s %s", hundredsOfThousandsAsText, "рублей");
            }else if (numberOfHundreds == 0 && numberOfTens == 0){
                int rublesLessThanTen = parseNumberOfGivenOrder(rubles, 5, 6);
                return String.format("%s %s", hundredsOfThousandsAsText, convertRublesUnderTenToText(rublesLessThanTen));
            } else if (numberOfHundreds == 0){
                int rublesLessThanHundred = parseNumberOfGivenOrder(rubles, 4, 6);
                return String.format("%s %s", hundredsOfThousandsAsText, convertRublesUnderHundredToText(rublesLessThanHundred));
            }
            return String.format("%s %s", hundredsOfThousandsAsText, convertHundredsOfRublesToText(hundreds));
        } else if (isNumberOfIntegersInHundredsOfThousandsZero(hundredsOfThousands)){
            return String.format("%s %s", hundredsToText().get(numberOfHundredsOfThousands), convertTensOfThousandsToText(parseNumberOfGivenOrder(rubles,1,6)));
        } else if (isNumberOfTensInHundredsOfThousandsZero(hundredsOfThousands)){
            int thousands = parseNumberOfGivenOrder(rubles, 2,6);
            return String.format("%s %s", hundredsToText().get(numberOfHundredsOfThousands), convertThousandsOfRublesToText(thousands));
        }
        return String.format("%s %s", hundredsToText().get(numberOfHundredsOfThousands), convertTensOfThousandsToText(parseNumberOfGivenOrder(rubles,1,6)));
    }

    private static String convertMillionsToText(int rubles){
        int numberOfMillions = parseNumberOfGivenOrder(rubles, 0, 1);
        int numberOfHundredsOfThousands = parseNumberOfGivenOrder(rubles, 1, 2);
        int numberOfTensOfThousands = parseNumberOfGivenOrder(rubles, 2, 3);
        int numberOfThousands = parseNumberOfGivenOrder(rubles, 3, 4);
        int numberOfHundreds = parseNumberOfGivenOrder(rubles,4, 5);
        int numberOfTens = parseNumberOfGivenOrder(rubles, 5, 6);
        int numberOfIntegers = parseNumberOfGivenOrder(rubles, 6,7);
        String numberOfMillionsAsText = millionsAsTextMap().get(numberOfMillions);
        if (rubles % 1000000 == 0){
            return String.format("%s %s", numberOfMillionsAsText, "рублей");
        } else if (numberOfHundredsOfThousands == 0 && numberOfTensOfThousands == 0 && numberOfThousands == 0 &&
                   numberOfHundreds == 0 && numberOfTens == 0){
            return String.format("%s %s", numberOfMillionsAsText, convertRublesUnderTenToText(numberOfIntegers));
        } else if (numberOfHundredsOfThousands == 0 && numberOfTensOfThousands == 0 && numberOfThousands == 0 &&
                numberOfHundreds == 0){
            int rublesLessThanHundred = parseNumberOfGivenOrder(rubles, 5, 7);
            return String.format("%s %s", numberOfMillionsAsText, convertRublesUnderHundredToText(rublesLessThanHundred));
        } else if (numberOfHundredsOfThousands == 0 && numberOfTensOfThousands == 0 && numberOfThousands == 0){
            int rublesUnderThousand = parseNumberOfGivenOrder(rubles, 4, 7);
            return String.format("%s %s", numberOfMillionsAsText, convertHundredsOfRublesToText(rublesUnderThousand));
        } else if (numberOfHundredsOfThousands == 0 && numberOfTensOfThousands == 0){
            int rublesUnderTenThousand = parseNumberOfGivenOrder(rubles, 3, 7);
            return String.format("%s %s", numberOfMillionsAsText, convertThousandsOfRublesToText(rublesUnderTenThousand));
        } else if (numberOfHundredsOfThousands == 0){
            int rublesUnderHundredThousand = parseNumberOfGivenOrder(rubles, 2, 7);
            return String.format("%s %s", numberOfMillionsAsText, convertTensOfThousandsToText(rublesUnderHundredThousand));
        }
        int rublesUnderMillion = parseNumberOfGivenOrder(rubles, 1, 7);
        return String.format("%s %s", numberOfMillionsAsText, convertHundredsOfThousandsToText(rublesUnderMillion));
    }

    private static boolean isZeroOrLess(int rubles){
        return rubles <= 0;
    }

    private static boolean isLessThanTen(int number){
        return number < 10;
    }

    private static boolean isLessThanTwenty(int number){
        return number < 20;
    }

    private static boolean isLessThanHundred(int number){
        return number < 100;
    }

    private static boolean isLessThanThousand(int number){
        return number < 1000;
    }
    private static boolean isLessThanTenThousand(int rubles) {
        return rubles < 10000;
    }
    private static boolean isLessThanHundredThousand(int rubles) {
        return rubles < 100000;
    }

    private static boolean isLessThanMillion(int rubles) {
        return rubles < 1000000;
    }

    private static boolean isLessThanTenMillion(int rubles) {
        return rubles < 10000000;
    }


    private static Map<Integer, String> numbersUnderTenToRublesTextMap(){
        return Map.of(
                1, "один рубль",
                2, "два рубля",
                3, "три рубля",
                4, "четыре рубля",
                5, "пять рублей",
                6, "шесть рублей",
                7, "семь рублей",
                8, "восемь рублей",
                9, "девять рублей"
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
                8, "восемьдесят",
                9, "девяносто"
        );
    }

    private static Map<Integer, String> tenToNineteenToTextWithRublesMap(){
        return Map.of(
                10, "десять рублей",
                11, "одиннадцать рублей",
                12, "двенадцать рублей",
                13, "тринадцать рублей",
                14, "четырнадцать рублей",
                15, "пятьнадцать рублей",
                16, "шестьнадцать рублей",
                17, "семнадцать рублей",
                18, "восемнадцать рублей",
                19, "девятнадцать рублей"
        );
    }

    private static Map<Integer, String> tenToNineteenMap(){
        return Map.of(
                10, "десять",
                11, "одиннадцать",
                12, "двенадцать",
                13, "тринадцать",
                14, "четырнадцать",
                15, "пятнадцать",
                16, "шестнадцать",
                17, "семнадцать",
                18, "восемнадцать",
                19, "девятнадцать"
        );
    }

    public static Map<Integer, String> hundredsToText(){
        return Map.of(
                1, "сто",
                2, "двести",
                3, "триста",
                4, "четыреста",
                5, "пятьсот",
                6, "шестьсот",
                7, "семьсот",
                8, "восемьсот",
                9, "девятьсот");
    }

    private static Map<Integer, String> thousandsToTextMap(){
        return Map.of(
                1, "одна тысяча",
                2, "две тысячи",
                3, "три тысячи",
                4, "четыре тысячи",
                5, "пять тысяч",
                6, "шесть тысяч",
                7, "семь тысяч",
                8, "восемь тысяч",
                9, "девять тысяч"
        );
    }

    public static Map<Integer, String> millionsAsTextMap(){
        return Map.of(
                1, "один миллион",
                2, "два миллиона",
                3, "три миллиона",
                4, "четыре миллиона",
                5, "пять миллионов",
                6, "шесть миллионов",
                7, "семь миллионов",
                8, "восемь миллионов",
                9, "девять миллионов"
        );
    }

    private static boolean isNumberOfIntegersInHundredsOfThousandsZero(int hundredsOfThousands){
        int numberOfTens = parseNumberOfGivenOrder(hundredsOfThousands, 1, 2);
        int numberOfIntegers = parseNumberOfGivenOrder(hundredsOfThousands, 2, 3);
        return numberOfTens != 0 && numberOfIntegers == 0;
    }

    private static boolean isNumberOfTensInHundredsOfThousandsZero(int hundredsOfThousands){
        int numberOfTens = parseNumberOfGivenOrder(hundredsOfThousands, 1, 2);
        int numberOfIntegers = parseNumberOfGivenOrder(hundredsOfThousands, 2, 3);
        return hundredsOfThousands % 100 != 0 && numberOfTens == 0 && numberOfIntegers != 0;
    }

    private static boolean isNumberOfHundredsOfThousandsRound(int rubles){
        return rubles % 100000 == 0;
    }
}
