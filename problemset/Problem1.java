package edu.mit.primes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem1 {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("INPUT NON-ZERO DIGITS: ");
        //debug String test = "32797254695745418883497984";
        String mitNum = input.nextLine().trim();
        //debug System.out.println(mitNum);
        if (!validDigit(mitNum)) {
            System.out.println(mitNum + " is not valid. Pick another number.");
        } else {
            //debug long timerStart = System.currentTimeMillis();
            ResultSet myRs = part(mitNum);
            //debug long timerEnd = System.currentTimeMillis();
            //debug *System.out.println("FINAL RESULT: ");
            //debug myRs.print(0);
            System.out.println(formatPrint(myRs.getPartArray()) + " " + myRs.getFactorArray().size());
            //debug long timeTook = timerEnd - timerStart;
            //debug System.out.println("It took " + timeTook + " milliseconds to complete your program.");
        }
    }

    public static int estimate(String digits) { // Used for the factorization optimization
        int counterFac = 0;
        char numberPart;
        char nextChar;

        for (int i = 0; i < digits.length(); i++) {
            numberPart = digits.charAt(i);
            if (numberPart == '1') {
                if (i < digits.length() - 1) {
                    nextChar = digits.charAt(i+1);
                    if(nextChar == '2' || nextChar == '8'){
                        counterFac += 3;
                    } else if (nextChar == '6'){
                        counterFac += 4;
                    } else if (nextChar == '4' || nextChar == '5' || nextChar == '9'){
                        counterFac += 2;
                    } else {
                        counterFac++;
                    }
                    i++;
                }
            } else if (numberPart == '4' || numberPart == '6' || numberPart == '9') {
                counterFac += 2;
            } else if (numberPart == '8') {
                counterFac += 3;
            } else {
                counterFac++;
            }
        }

        return counterFac;
    }

    public static ResultSet part(String digits) {
        //debug traceFunc(counter, digits, "ENTER PART: ");
        // BigInteger input = new BigInteger(digits);
        int len = digits.length();

        // START OF FACTOR PRUNING OPTIMIZATION
        ResultSet[][] allFactors = new ResultSet[len][len]; // allFactors[i][j] stores prime factorization of digits.substring(i, j+1)
        String subString;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                subString = digits.substring(i, j + 1);
                allFactors[i][j] = factor(subString);
                //debug System.out.println(subString + ": " + allFactors[i][j].getFactorArray());
            }
        }
        // END OF FACTOR PRUNING OPTIMIZATION

        // START OF PARTITION OPTIMIZATION
        ResultSet[] allPartitions = new ResultSet[len]; // at index i of allPartitions, stores resultSet answer to the partition problem for the substring digits.substring(i)
        int compFac;
        ResultSet finalRs;
        for (int i = len - 1; i >= 0; i--) { // recursion part
            finalRs = allFactors[i][len - 1];
            for (int j = i; j < len - 1; j++) {
                compFac = allFactors[i][j].getFactorArray().size() + allPartitions[j + 1].getFactorArray().size(); // number of factors if partition like: digits.substring(i, j+1), digits.substring(j+1)
                if (finalRs.getFactorArray().size() < compFac) { // compFac partition is better
                    finalRs = allFactors[i][j];
                    finalRs.getPartArray().addAll(allPartitions[j + 1].getPartArray());
                    finalRs.getFactorArray().addAll(allPartitions[j + 1].getFactorArray());
                }
            }
            allPartitions[i] = finalRs;
        }
        // END OF PARTITION OPTIMIZATION

        //debug traceFunc(counter, digits, "EXIT PART: ");
        return allPartitions[0];
    } // END OF PART FUNC

    public static ResultSet factor(String digits) {
        //debug traceFunc(counter, digits, "ENTER FACTOR: ");
        BigInteger input = new BigInteger(digits);
        ResultSet rs1 = new ResultSet();
        rs1.getPartArray().add(input);

        BigInteger f = new BigInteger("3");

        while (input.remainder(BigInteger.TWO) == BigInteger.ZERO) {
            input = input.divide(BigInteger.TWO);
            rs1.getFactorArray().add(BigInteger.TWO);
        }

        // START OF FACTORIZATION OPTIMIZATION
        BigInteger checkNum = new BigInteger("1000");
        int checkBase = 3;
        int remainDigits;
        int remainFac;
        int estimateNum = 0;
        while (f.multiply(f).compareTo(input) <= 0) {
            while (input.remainder(f) == BigInteger.ZERO) {
                input = input.divide(f);
                rs1.getFactorArray().add(f);
            }
            f = f.add(BigInteger.TWO); // checking all odd numbers as divisors
            if (f.compareTo(checkNum) == 1) { // reached an odd number > 1000, f = 1001
                remainDigits = input.toString().length();
                remainFac = (remainDigits + checkBase - 1) / checkBase; // upper bound on number of factors > 1000 left. add checkBase - 1 to numerator bc: int rounds down, want to prevent that
                if (estimateNum == 0) {
                    estimateNum = estimate(digits); // lower bound on number of factors
                }
                if (rs1.getFactorArray().size() + remainFac < estimateNum) { // upper bound < estimate: no hope, can exit early
                    rs1.setSafe(false);
                    //debug System.out.println("-- Early exit: " + checkBase + "--");
                    return rs1;
                } else { // need to look for more factors
                    checkNum = checkNum.multiply(BigInteger.TEN);
                    checkBase++;
                }
            }
        }
        // END OF FACTORIZATION OPTIMIZATION

        if (input.compareTo(BigInteger.ONE) != 0) {
            rs1.getFactorArray().add(input);
        }
        //rs1.getPartArray().add(input);
        //debug traceFunc(digits, "EXIT FACTOR: ");
        return rs1;
    } // END OF FACTOR FUNC

    public static boolean validDigit(String str) {
        //debug System.out.println(str);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '1'
                    && str.charAt(i) <= '9') {
                //nothing
            } else {
                return false;
            }
        }
        return true;
    }

    public static String formatPrint(ArrayList<BigInteger> items){
        String temp = items.toString();
        String formattedString = temp.replace("[", "").replace("]", "").trim();
        return formattedString;
    }

    /* debug method
    public static void traceFunc(String digits, String funcName){
        System.out.print(funcName + " " + digits);
        System.out.println();
    }
     */
}
