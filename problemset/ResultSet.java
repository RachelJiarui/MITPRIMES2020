package edu.mit.primes;

import java.math.BigInteger;
import java.util.ArrayList;

public class ResultSet {
    private ArrayList<BigInteger> partArray;
    private ArrayList<BigInteger> factorArray;
    private boolean isSafe;

    public ArrayList<BigInteger> getFactorArray() {
        return factorArray;
    }

    public void setFactorArray(ArrayList<BigInteger> factorArray) {
        this.factorArray = factorArray;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public ResultSet(){
        partArray = new ArrayList<BigInteger>();
        factorArray = new ArrayList<BigInteger>();
        isSafe = true;
    }

    public ArrayList<BigInteger> getPartArray() {
        return partArray;
    }

    public void setPartArray(ArrayList<BigInteger> partArray) {
        this.partArray = partArray;
    }

    public void print(int counter){
        for(int i = 0; i < counter; i++){
            System.out.print(" ");
        }
        System.out.print("PART: " + partArray);
        System.out.println();
        for(int i = 0; i < counter; i++){
            System.out.print(" ");
        }
        System.out.print("FACT: " + factorArray);
        System.out.println();

        System.out.print("NUMBER OF FACTORS: " + factorArray.size());
        System.out.println();
    }
}
