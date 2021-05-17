package com.example.candfconverter;

public class convert {
    private double doF,doC;
    boolean FToC = false, CToF = false;

    public double getDoC() {
        return doC;
    }

    public double getDoF() {
        return doF;
    }

    public void setDoC(double doC) {
        this.doC = doC;
    }

    public void setDoF(double doF) {
        this.doF = doF;
    }

    public double convertFToC(){
        FToC = true;
        return doC = (doF - 32)* 5 / 9;
    }

    public double convertCToF(){
        CToF = true;
        return doF = (doC * 9 / 5) + 32;
    }

    String convertString(){
        if(FToC){
            return doF + " độ F sang " + doC + " độ C. \n";
        }

        if(CToF){
            return  doC + " độ C sang " + doF + " độ F. \n";
        }

        return "";
    }
}
