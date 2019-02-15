// File: Measurement.java
// Holds the Measurement Class
//
// ­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­­
// Class: CS290                    Instructor: Dr. Don Roberts
// Assignment: Homework 1          Date assigned:  Jan. 18, 2019
// Programmer: Austin Brummett     Date completed: Jan. 26, 2019

import java.util.Vector;
import java.util.Collections; // I love how Java has a library for almost everything

public class Measurement {

    //
    private double value; // value of measurement
    private Vector<String> numerator; // numerator of unit
    private Vector<String> denominator; // denominator of unit

    // constructors
    public Measurement(){
       this(0, "");
    }
    public Measurement(double value, String unit){
        this.value = value;
        Vector<String> tmp = new Vector<>();


        if(unit.contains("/") == false) {
            tmp.add(unit); // adds unit to Vector
            this.numerator = tmp;
            this.denominator = new Vector<>(); // blank list that is here when it is needed
        }
        else{
            String [] parts = unit.split("/");
            tmp.add(parts[1]); // adds unit to Vector
            this.numerator = new Vector<>();
            this.numerator.add("");

            this.denominator = tmp;
        }
    }
    private Measurement(double value, Vector<String> numerator, Vector<String> denominator) {
        simplifyUnits(numerator, denominator); // clean up units before assigning the variables
        this.value = value;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    // member functions
    public double getValue(){
        return value;
    }
    public Vector<String> getNumerator(){
        return numerator;
    }
    public Vector<String> getDenominator(){
        return denominator;
    }
    public Measurement add(Measurement m){
        if((m.numerator.equals(this.getNumerator())) && (m.denominator.equals(this.getDenominator()))) {
           return new Measurement(this.getValue() + m.getValue(), this.getNumerator(), this.getDenominator());
        }
        else throw new ArithmeticException();
    }

    public Measurement sub(Measurement m){
        if((m.getNumerator().equals(this.numerator)) && (m.getDenominator().equals(this.getDenominator()))) {
            return new Measurement(this.value - m.getValue(), this.getNumerator(), this.getDenominator());
        }
        else throw new ArithmeticException();
    }

    public Measurement mult(Measurement m){
        return new Measurement(this.getValue()*m.getValue(), combineLists(this.getNumerator(), m.getNumerator()),
                combineLists(this.getDenominator(), m.getDenominator()));
    }

    public Measurement div(Measurement m){
        return new Measurement(this.value/m.getValue(), combineLists(this.getNumerator(), m.getDenominator()),
                combineLists(this.getDenominator(), m.getNumerator()));
    }

    public String toString(){
        String str = new String();
        str += Double.toString(this.value);
        str += convertUnitsToString(this.numerator);
        if(this.getDenominator().size() > 0 ){
            str += "/";
            str += convertUnitsToString(this.denominator);
        }
        return str;
    }

    // private functions to make the other functions prettier
    private void simplifyUnits(Vector<String> num2, Vector<String> denom2) {
        String tmp;
        // iterate through based on the numerator list size
        for (int i = 0; i < num2.size(); i++) {
            tmp = num2.get(i); // look at element i

            // if tmp is in the denominator, remove it
            if (denom2.remove(tmp)) {
                num2.remove(tmp); // remove the occurrence from the numerator because division
            }
        }
    }

    private Vector<String> combineLists(Vector<String> list1, Vector<String> list2) {
        Vector<String> newList = new Vector<>();
        newList.addAll(list1); // add all of list 1 to the new unit list
        newList.addAll(list2); // add all of list 2 to the new unit list
        Collections.sort(newList); // sort the new unit list because it is probably a mess now
        return newList;
    }


    private String convertUnitsToString(Vector<String> StringList) {
        StringBuffer output = new StringBuffer(); // for this purpose, this is a bit better than the string
        int count; // keeps track of the exponent
        Vector<String> newList = new Vector<>(StringList); //  new list based off the old one
        for (int i = 0; i < newList.size(); ) {
            count = 1;
            // look through the list for multiple occurences of the same unit
            // and keep track of the power
            for (int j = i + 1; j < newList.size(); j++)
                if (newList.get(i).equals(newList.get(j)))
                    count++;
            output.append(newList.get(i)); // add the current unit to the StringBuffer
            String temp; // this is only useful for the remove statement
            temp = newList.get(i); // get a copy of the current unit
            if (count > 1) // if it is higher than the first power, show it
                output.append("^").append(count);
            while(newList.remove(temp)); // get rid of the first occurrence until there are no more
        }
        return output.toString();
    }
}
