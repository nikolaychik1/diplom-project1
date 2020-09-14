package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class Controller {

    @FXML
    private Button start_button;

    @FXML
    private RadioButton mm_rt_st;

    @FXML
    private ToggleGroup type;

    @FXML
    private RadioButton bar;

    @FXML
    private RadioButton kpa;

    @FXML
    private Button stop_button;

    @FXML
    private TableView<data> result_field;
    @FXML
    private TableColumn<data,Double> tk;
    @FXML
    private TableColumn<data,Double> tk1;
    @FXML
    private TableColumn<data,Double> tk2;
    @FXML
    private TableColumn<data,Double> tk3;
    @FXML
    private TableColumn<data,Integer> tk4;

    @FXML
    private CategoryAxis time;

    @FXML
    private NumberAxis flash;
    double [] example;
    double[] v;
    double[] vup;
    double[] vdown;
    double[] pressure;
    double input;
    TimerTask tt;
    Timer timer;
    boolean sw;


    double S(double[] x_data){
        double s=0,srx=0,sumx=0;
        for (int i=0;i<x_data.length;i++) sumx = sumx + x_data[i];
        srx=sumx/x_data.length;
        for (int i=0;i<x_data.length;i++) s = s + Math.pow(x_data[i] - srx, 2);
        return s;
    }
    double summ(double[] array1, double[] array2){
        double summa=0;
        int i=array1.length;
        int j=array2.length;

        if(i<j){
            for(int k=0;k<i;k++){
                summa=summa+array1[k]*array2[k];
            }
        }
        else
            for(int k=0;k<j;k++){
                summa=summa+array1[k]*array2[k];
            }
        return summa;
    }

    double summ1(double[] array1){
      double summa=0;
      int i=array1.length;
      for (int k=0; k<i;k++){
          summa=summa+array1[k];
      }
      return summa;
    }





    double mediana(double[] ar1){
        double mediana;
        if(ar1.length % 2 ==0){
            mediana=(ar1[ar1.length/2]+ar1[(ar1.length/2)-1])/2;
        }
        else {
            mediana=ar1[ar1.length/2];
        }
        return mediana;
    }

     double[] outlier(double[] var1,double input){
        Arrays.sort(var1);
        int m,l,x,i=0;
        int i1=0;
        int i2=0;
        double med,medmin,medmax;
        med=mediana(var1);
        x=var1.length/2;
        double[] varmin = new double[x],varmax=new double[x];
        for (l=0;l<var1.length;l++ ){
            if (var1[l]<med){
                varmin[i1]=var1[l];
                i1++;
            }
            else if(var1[l]>med){
                varmax[i2]=var1[l];
                i2++;
            }
        }
        medmin=mediana(varmin);
        medmax=mediana(varmax);
        double diapozon=medmax-medmin;
        double externalbordermin,externalbordermax;

        externalbordermax=medmax+diapozon*3;
        externalbordermin=medmin-diapozon*3;
         int anslen=var1.length;
        for (l=0;l<var1.length;l++){
            if ((var1[l]<externalbordermin)||(var1[l]>externalbordermax)){
                anslen--;
            }
        }
         double[] answer =new double[anslen];
        for(l=0;l<var1.length;l++) {
            if ((var1[l]<externalbordermin)||(var1[l]>externalbordermax)){

            }
            else {
                answer[i]=var1[l];
                i++;
            }
        }
         return answer;
     }
     double det(double[] [] array){
        int row=array.length;
        int col=array.length;
        double det=1;
        double[][] copy=new double[row][col];
        for(int i=0;i<row;i++){
            for (int j=0;j<col;j++){
                copy[i][j]=array[i][j];//копир. исходный массив
            }
        }
        for(int i=0;i<col;i++){
            for(int i1=0;i1<row;i1++){
                for (int i2=0;i2<col;i2++){
                    array[i1][i2]=copy[i1][i2];
                }
            }
            //треуг. матр.
            for(int j=i+1;j<col;j++){
                for(int k=i;k<col;k++){
                    double pr=copy[j][k]-copy[i][k]*array[j][i]/copy[i][i];
                    copy[j][k]=pr;
                }
            }

        }

        for(int x=0;x<row;x++){
            det=det*copy[x][x];
        }

        return det;
     }

     double naimkv(double x1,double x2,double x3,double x4,double[] arrt, double[] arrtup,double[] arrtdown,double[] pressure,double[] itog){

       int i=5;
       int n=arrt.length;

        double vspishka=0;
        

        double[][] mass=new double[i][i];
        mass[0][0]=n;
        mass[0][1]=summ1(arrt);
        mass[0][2]=summ1(arrtup);
        mass[0][3]=summ1(arrtdown);
        mass[0][4]=summ1(pressure);
        mass[1][0]=summ1(arrt);
         mass[1][1]=summ(arrt,arrt);
         mass[1][2]=summ(arrt,arrtup);
         mass[1][3]=summ(arrt,arrtdown);
         mass[1][4]=summ(arrt,pressure);
         mass[2][0]=summ1(arrtup);
         mass[2][1]=summ(arrtup,arrt);
         mass[2][2]=summ(arrtup,arrtup);
         mass[2][3]=summ(arrtup,arrtdown);
         mass[2][4]=summ(arrtup,pressure);
         mass[3][0]=summ1(arrtdown);
         mass[3][1]=summ(arrtdown,arrt);
         mass[3][2]=summ(arrtdown,arrtup);
         mass[3][3]=summ(arrtdown,arrtdown);
         mass[3][4]=summ(arrtdown,pressure);
         mass[4][0]=summ1(pressure);
         mass[4][1]=summ(arrt,pressure);
         mass[4][2]=summ(arrtup,pressure);
         mass[4][3]=summ(arrtdown,pressure);
         mass[4][4]=summ(pressure,pressure);
         double[] det=new double[5];
         double[][] copy=new double[5][5];
         double [] tv=new double[5];
         tv[0]=summ1(itog);
         tv[1]=summ(itog,arrt);
         tv[2]=summ(itog,arrtup);
         tv[3]=summ(itog,arrtdown);
         tv[4]=summ(itog,pressure);
         for (int h=0;h<5;h++){
             for(int i1=0;i1<5;i1++){
                 for (int j1=0;j1<5;j1++){
                     copy[i1][j1]=mass[i1][j1];
                 }
             }

                 for (int k = 0; k < 5; k++) {

                     copy[k][h] = tv[k];

                 }
                 det[h] = det(copy);

         }
         double detob=det(mass);
         double a,b,c,d,e;
         a=(det[0]/detob)/(-40000);
         b=(det[1]/detob);
         c=(det[2]/detob);
         d=det[3]/detob;
         e=det[4]/detob;

        // System.out.println(a);
        // System.out.println(b);
         //System.out.println(c);
         //System.out.println(d);
        // System.out.println(e);



         vspishka=((x1*a+x2*b+x3*c+x4*d+e)/38)-14;
        return vspishka;
     }


    public class TaskOne extends TimerTask  {

        @Override
        public void run() {

        }
    }
    @FXML
    public void initialize(){



        String url = "jdbc:mysql://localhost/analizer_data?serverTimezone=Europe/Moscow&useSSL=false";
        String username = "root";
        String password = "root";

        start_button.setOnAction(event ->{

            tk.setCellValueFactory(new PropertyValueFactory<>("T1"));
            tk1.setCellValueFactory(new PropertyValueFactory<>("T2"));
            tk2.setCellValueFactory(new PropertyValueFactory<>("T3"));
            tk3.setCellValueFactory(new PropertyValueFactory<>("P"));
            tk4.setCellValueFactory(new PropertyValueFactory<>("Vs"));

            ObservableList<data> oblist=FXCollections.observableArrayList(
            );
            sw=true;
            try {


                int i=0;
                Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    Statement statement1 = conn.createStatement();
                    Statement statement2=conn.createStatement();
                    Statement statement3=conn.createStatement();
                    Statement statement4=conn.createStatement();
                    Statement statement5=conn.createStatement();
                    String com ="SELECT sample FROM sample1";
                    String com2="SELECT tj FROM sample1";
                   String com3="SELECT tjup FROM sample1";
                    String com4="SELECT tjdown FROM sample1";
                    String com5="SELECT pressure FROM sample1";
                    ResultSet rs = statement1.executeQuery(com);
                    ResultSet rs1=statement2.executeQuery(com2);
                    ResultSet rs2=statement3.executeQuery(com3);
                    ResultSet rs3=statement4.executeQuery(com4);
                    ResultSet rs4=statement5.executeQuery(com5);
                    int q =0,q1=0,q2=0;
                    while(rs.next()){
                        q++;
                    }
                    rs.beforeFirst();
                    while (rs1.next()){
                        q1++;
                    }

                    rs1.beforeFirst();

                    example=new double[q];
                     v=new double[q1];
                     vup=new double[q1];
                     vdown=new double[q1];
                     pressure=new double[10];
                    int n=0;
                    while (rs.next()){
                       double ex1=rs.getDouble(1);
                       example[n]=ex1;

                       n++;
                    }
                    int nn1=0,nn2=0,nn3=0,nn4=0;
                    while (rs1.next()){
                        double ex2=rs1.getDouble(1);
                        v[nn1]=ex2;
                        nn1++;
                    }
                    while (rs2.next()){
                        double ex3=rs2.getDouble(1);
                        vup[nn2]=ex3;

                        nn2++;
                    }
                    while (rs3.next()){
                        double ex4=rs3.getDouble(1);
                        vdown[nn3]=ex4;
                        nn3++;
                    }
                    while (rs4.next()){
                        double ex5=rs4.getDouble(1);
                        pressure[nn4]=ex5;
                        nn4++;
                    }




                    System.out.println("Success");

                }
            } catch (Exception ex) {
                System.out.println("Connection failed...");


                System.out.println(ex);

            }

            TimerTask tt =new TimerTask() {
                @Override
                public void run() {

                }
            };

             timer=new java.util.Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    input=Math.random();
                    double[] arr1=outlier(example,input);
                     double min=300;
                     double max=320;
                     double min1=260;
                     double max1=280;
                     double min2=250;
                     double max2=270;
                     double t1=Math.random()*(max1-min1)+min1;
                    double t2=Math.random()*(max-min)+min;
                    double t3=Math.random()*(max2-min2)+min2;
                    double p=(Math.random()*(max-min)+min)/140;
                    double vsp=naimkv(t1,t2,t3,p,v,vup,vdown,pressure,example);
                    System.out.println(vsp);
                    if(kpa.isSelected()){
                        double result=vsp+((101.325-p)*0.9/3.3);
                        int itog= (int) (((int) result)+Math.random()*(3-1)+1);
                        System.out.println(itog);
                        Double[] array=new Double[5];
                        array[0]=t1;
                        array[1]=t2;
                        array[2]=t3;
                        array[3]=p;
                        array[4]=(double)itog;

                       /* tk.setCellValueFactory(new PropertyValueFactory<>("T1"));
                        tk1.setCellValueFactory(new PropertyValueFactory<>("T2"));
                        tk2.setCellValueFactory(new PropertyValueFactory<>("T3"));
                        tk3.setCellValueFactory(new PropertyValueFactory<>("P"));
                        tk4.setCellValueFactory(new PropertyValueFactory<>("Vs"));

                        ObservableList<data> oblist=FXCollections.observableArrayList(
                        );*/
                        oblist.add(new data(t1,t2,t3,p,itog));
                        result_field.setItems(oblist);



                    }




                }



            }, 1000,500);

            try {

                    Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
               // timer.cancel();
            }
            System.out.println("stop");



            double result1=0;
            double result2=0;
           double result_fin = 0;
            double p;


            if(bar.isSelected()){
                p=2;
                System.out.println("h");
               // result1=(f_data+1.013-p)*0.9/0.033;
               // result2=(s_data+1.013-p)*0.9/0.033;

            }
            else if(mm_rt_st.isSelected()){
                p=2;
              System.out.println("mm");
              //result1=(f_data+0.0362*(760-p));
                //result2=(s_data+0.0362*(760-p));

            }
            else if(kpa.isSelected()){
                System.out.println("kpa");
                p=2;
                //result1=f_data+((101.325-p)*0.9/3.3);
                //result2=s_data+((101.325-p)*0.9/3.3);

            }
            result_fin=(result1+result2)/2;
            System.out.println(result_fin);

        });

        stop_button.setOnAction(event -> {
            timer.cancel();
        });

    }


}



