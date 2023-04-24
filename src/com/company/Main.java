package com.company;

public class Main {

    public static void main(String[] args) {

        InputData inputData = new InputData(0.8, "dane_medyczne.csv");
        inputData.calculate();
        System.out.println("-----------------------------------------------------");
        inputData.exploitation();
    }
}
