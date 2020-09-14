package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class data {
    private SimpleDoubleProperty T1;
    private SimpleDoubleProperty T2;
    private SimpleDoubleProperty T3;
    private SimpleDoubleProperty P;
    private SimpleIntegerProperty Vs;
    public data(double T1,double T2,double T3, double P,int Vs){
        this.T1=new SimpleDoubleProperty(T1);
        this.T2=new SimpleDoubleProperty(T2);
        this.T3=new SimpleDoubleProperty(T3);
        this.P=new SimpleDoubleProperty(P);
        this.Vs=new SimpleIntegerProperty(Vs);
    }
    public Double getT1(){
        return T1.get();
    }
    public void setT1(double T1){
        this.T1=new SimpleDoubleProperty(T1);
    }
    public double getT2(){
        return T2.get();
    }
    public void setT2(double T2){
        this.T2=new SimpleDoubleProperty(T2);
    }
    public double getT3(){
        return T3.get();
    }
    public void setT3(double T3){
        this.T3=new SimpleDoubleProperty(T3);
    }
    public double getP(){
        return P.get();
    }
    public void setP(double P){
        this.P=new SimpleDoubleProperty(P);
    }
    public Integer getVs(){
        return Vs.get();
    }
    public void setVs(int Vs){
        this.Vs=new SimpleIntegerProperty(Vs);
    }
}
