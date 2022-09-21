package edu.mit.primes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem2 {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("INPUT NON-ZERO DIGITS: ");
        String inputNum = input.nextLine().trim();
        System.out.println("INPUT NUMBER BETWEEN 1 AND " + inputNum + ": ");
        String productNum = input.nextLine().trim();
        boolean status;
        if (!Problem1.validDigit(inputNum) || !validZero(productNum)) {
            System.out.println(inputNum + " or " + productNum + " is not valid. Try again.");
        } else {
            //debug long timerStart = System.currentTimeMillis();
            //debug String inputNum = "8273987391823293";
            //debug String productNum = "98127398";
            ArrayList<BigInteger> resultPart = new ArrayList<BigInteger>();
            BigInteger bigProduct = new BigInteger(productNum);
            status = checkPart(inputNum, bigProduct, resultPart);
            //debug long timerEnd = System.currentTimeMillis();
            if (status == true) {
                System.out.println(Problem1.formatPrint(resultPart));
            } else {
                System.out.println("No solution");
            }
        }
        //debug long timeTook = timerEnd - timerStart;
        //debug System.out.println("It took " + timeTook + " milliseconds to complete your program.");
    }

    public static boolean checkPart(String partNum, BigInteger bigProduct, ArrayList<BigInteger> resultSet) {
        String strSeg;
        String leftovers;
        //debug System.out.println("Start:");
        //debug System.out.print(partNum);
        //debug System.out.println("End:");
        BigInteger bigPart = new BigInteger(partNum);
        boolean status;

        if (partNum.length() == 1) {
            if (bigPart.equals(bigProduct)) {
                resultSet.add(bigPart);
                return true;
            } else {
                return false;
            }
        }

        // OPTIMIZATION
        if(bigPart.compareTo(bigProduct) == -1){
            return false;
        } else if (bigPart.compareTo(bigProduct) == 0){
            resultSet.add(bigPart);
            return true;
        }

        for (int i = 0; i < partNum.length(); i++) {
            strSeg = partNum.substring(0, i + 1);
            leftovers = partNum.substring(i + 1);
            bigPart = new BigInteger(strSeg);

            if (bigProduct.mod(bigPart).equals(BigInteger.ZERO)) {
                status = checkPart(leftovers, bigProduct.divide(bigPart), resultSet);
                if (status == true) {
                    resultSet.add(0, bigPart);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validZero(String str) {
        //debug System.out.println(str);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0'
                    && str.charAt(i) <= '9') {
                //nothing
            } else {
                return false;
            }
        }
        return true;
    }
}
