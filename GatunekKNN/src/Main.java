import javax.swing.*;
import javax.swing.event.ListDataEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
//parametry wywołania parametr k, plik treningowy, plik testowy
public class Main {
    static Object [] linijkiPlikuTren;
    static int iloscWymiarow;
    static File file1;
    static File file2;
    static int k;
    static String linijkaTest;
    public static void main(String[] args) {



        double licznik=0;
        double mianownik=0;
try {
    file1 = new File(args[1]);
     file2 = new File(args[2]);
   k= Integer.parseInt(args[0]);


    BufferedReader br = new BufferedReader(new FileReader(file1));



    linijkiPlikuTren =  br.lines().toArray();
    br= new BufferedReader((new FileReader(file2)));

iloscWymiarow=((String)linijkiPlikuTren[0]).split(",").length-1;
    System.out.println(iloscWymiarow);



 linijkaTest=br.readLine();
List<OdlGat> lista = new ArrayList();


   while(linijkaTest!=null){
       String gatunek=sprawdz(iloscWymiarow,  linijkiPlikuTren,linijkaTest,k);
       if(gatunek.equals(linijkaTest.split(",")[iloscWymiarow]))
           licznik++;
       mianownik++;
       System.out.println(gatunek );

     linijkaTest=br.readLine();

 }
    //System.out.println(licznik);
    //System.out.println(mianownik);
    System.out.println("Skuteczność: "+(licznik/mianownik));



}catch(IOException e){}

GUI gui=new GUI();
gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//gui.setSize(300,150);
gui.setTitle("Sprawź gatunek");
gui.setVisible(true);

    }

    public static String sprawdz(int iloscWymiarow,Object [] linijkiPlikuTren , String linijkaTest,int k){
        String[] linijkaTestowa;
        List<OdlGat> lista = new ArrayList();


        linijkaTestowa=linijkaTest.split(",");


        for(int i=0;i<linijkiPlikuTren.length;i++){

            String[] linijkaTreningowa=((String)linijkiPlikuTren[i]).split(",");
            double suma=0;
            for(int j=0;j<iloscWymiarow;j++){

                suma+=Math.pow((Double.parseDouble(linijkaTestowa[j])-Double.parseDouble(linijkaTreningowa[j]) ),2);

            }
            suma*=100;
            suma=Math.round(suma);
            suma/=100;


            OdlGat odlGat= new OdlGat(suma,linijkaTreningowa[iloscWymiarow]);
            lista.add(odlGat);
            // System.out.println(odlGat);


        }

                 List<String> listaGatunkow =lista.stream().map(e-> e.getGat() ).distinct().collect(Collectors.toList());


                lista= lista.stream().sorted(Comparator.comparingDouble(OdlGat::getOdl)).collect(Collectors.toList()).subList(0,k);

                 String gatunek="";
                 int maxWystepowanie =0;
                for(String g:listaGatunkow) {
                    int wystepowanie = (int) lista.stream().filter(e -> e.getGat().equals(g)).count();
                    if(wystepowanie>maxWystepowanie) {
                        maxWystepowanie = wystepowanie;
                        gatunek = g;
                    }
                    if(wystepowanie==maxWystepowanie)
                        gatunek=(Math.random()>0.5?gatunek:g);

                }





        lista.clear();
        return gatunek;


    }
}

class OdlGat {
   private double odl;
   private String gat;

    public OdlGat(double odl, String gat){
        this.gat=gat;
        this.odl=odl;


    }

    public double getOdl() {
        return odl;
    }

    public String getGat() {
        return gat;
    }

    @Override
    public String toString() {
        return
                odl+" "+gat;
    }


}