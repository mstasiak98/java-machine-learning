package com.company;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class InputData {

    private final static String COMMA_DELIMITER = ",";
    private final static int SET_SIZE = 683;

    List<InputObject> objectRows = new ArrayList<>();
    List<InputObject> testSetObjects = new ArrayList<>();
    List<InputObject> teachingObjects = new ArrayList<>();
    List<List<Distance>> distances = new ArrayList<>();

    int numberOfElements;

    public InputData(double teachingPercentageOfAllRows, String filename) {
        numberOfElements = (int)(SET_SIZE * teachingPercentageOfAllRows);
        this.readFile(filename);
        this.splitIntoTwoSets();
        for(int i =0;i<testSetObjects.size();i++){
            distances.add(new ArrayList<>());
        }
        this.calculateDistances(testSetObjects, teachingObjects, distances);
    }


    public void exploitation(){
        List<InputObject> newElements = new ArrayList<>();
        List<List<Distance>> distances = new ArrayList<>();
        for(int i =0;i<10;i++){
            distances.add(new ArrayList<>());
        }

        Random random = new Random();
        int k = 5;
        for(int i =0; i<10; i++){
            List<String> randomFeatures = new ArrayList<>();
            int randomValue;
            for(int j =0; j<9;j++){
                randomValue = random.nextInt(10);
                randomFeatures.add(Integer.toString(randomValue));
            }
            newElements.add(new InputObject(randomFeatures));
        }
        this.calculateDistances(newElements, teachingObjects, distances);

        for (int i= 0; i<newElements.size();i++){
            System.out.println(newElements.get(i));
            System.out.println("Klasyfikacja: " + findBelongingToClass(k, i));
        }
    }

    public void calculate(){
        calculateClassificationError(3);
        calculateClassificationError(5);
        calculateClassificationError(7);
    }

    public void calculateClassificationError(int k){
        int b = 0;
        for(int i =0; i<testSetObjects.size(); i++){
            String resultClass = this.findBelongingToClass(k, i);
            if(!resultClass.equals(testSetObjects.get(i).getColumns().get(9))){
                b++;
            }
        }

        System.out.println("Ilość sąsiadów = " + k);
        System.out.println("B = " + b);
        double n = testSetObjects.size();
        //System.out.println("Rozmiar testowego = " +n);
        double blad = (double)(b/n);
        System.out.println("Bład klasyfikacji = "+ String.format("%.5g%n", blad));
    }

    private String findBelongingToClass(int k, int testObjectIdx){
        int result = 0;
        for(int j=0; j<k;j++){
            String textResult = teachingObjects.get(distances.get(testObjectIdx).get(j).getIdxOfTeachingObject()).getColumns().get(9);
            if(textResult.equals("lagodny")){
                result++;
            }
        }
        if(result > (k/2)){
            return "lagodny";
        }else{
            return "zlosliwy";
        }
    }

    private void calculateDistances(List<InputObject> testSetObjects,List<InputObject> teachingObjects, List<List<Distance>> distances){

        for (int i =0; i<testSetObjects.size(); i++){
            for(int j = 0; j< teachingObjects.size(); j++){
                int sum = 0;
                for(int k =0; k<9; k++){
                    sum += manhatanDistance(Integer.parseInt(teachingObjects.get(j).getColumns().get(k)), Integer.parseInt(testSetObjects.get(i).getColumns().get(k)));
                }
                Distance distance = new Distance(sum, j);
                distances.get(i).add(distance);
            }
            Collections.sort(distances.get(i));
        }
    }

    private int manhatanDistance(int x, int y){
        return Math.abs(x-y);
    }

    private void splitIntoTwoSets(){

        List<Integer> indexes = new ArrayList<>();

        if(objectRows.size() != 0){
            Random random = new Random();
            int randomValue;
            while(teachingObjects.size() != numberOfElements){
                randomValue = random.nextInt(SET_SIZE);
                teachingObjects.add(objectRows.get(randomValue));
                indexes.add(randomValue);
            }

            for(int i =0;i<SET_SIZE;i++){
                if(!indexes.contains(i)){
                    testSetObjects.add(objectRows.get(i));
                }
            }

            System.out.println("Liczba elementów pliku uczącego =" + teachingObjects.size());

        }else{
            System.out.println("Plik wejściowy nie został wczytany");
        }
    }

    private void readFile(String filename){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                objectRows.add(new InputObject(Arrays.asList(values)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
