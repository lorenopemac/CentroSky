/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

import java.util.Random;

/**
 *
 * @author Final Estructrgza4ura
 */
public class CentroDeEsqui {
    
    private MedioElevacion medio1;  //el centro de sky tiene 4 medios de elevacion
    private MedioElevacion medio2;
    private MedioElevacion medio3;
    private MedioElevacion medio4;
    private Confiteria confiteria;   //tiene una confiteria
    private Escuela_Sky_Snow clases;  //una escuela de clases de sky y snowboard
    private long inicio,cierre; //horarios de inicio y cierre de actividades del centro

    public CentroDeEsqui() {
    
        medio1=new MedioElevacion(1);
        medio2=new MedioElevacion(2);
        medio3=new MedioElevacion(3);
        medio4=new MedioElevacion(4);
        confiteria = new Confiteria();
        clases=new Escuela_Sky_Snow();
        inicio=System.currentTimeMillis();
        cierre=420000;
    }
    
    public void mostrarUsosMedios(){
    
    System.out.println("Medio de elevacion 1 usado: "+medio1.getUso()+" veces");
    System.out.println("Medio 2 de elevacion usado: "+medio2.getUso()+" veces");
    System.out.println("Medio 3 de elevacion usado: "+medio3.getUso()+" veces");
    System.out.println("Medio 4 de elevacion usado: "+medio4.getUso()+" veces");
        
    }
    
    public void comer(Esquiador actual){
    
        
       if(System.currentTimeMillis()-inicio<cierre-30000){//if le queda media hora antes del cierre puede entrar a la confiteria
           
       if(!actual.getArriba()){//si no esta arriba sube la montaña en algun medio de elevacion    
         subirMontaña(actual);
       }
      if(actual.getArriba())//si ya esta arriba entra a la confiteria
       {
        System.out.println(((System.currentTimeMillis()-inicio)/10000)/6+" Min - "+actual.getNombre()+": Entrando a la confiteria");
        confiteria.comer(actual);
       }
       }
    }
    
    public void esquiar(Esquiador actual) {
    
        
          
            if(actual.getArriba()==true && (System.currentTimeMillis()-inicio<cierre-5000)){//si esta arriba baja esquiando
        
                 actual.setArriba(false);
                 System.out.println(((System.currentTimeMillis()-inicio)/10000)/6+" Min - "+actual.getNombre()+": Esquiando");
                 System.out.println(actual.getNombre()+": Termine de esquiar");
            }
             else{
         
                if(this.estaEnHorarioEsquiar()){//si no esta arriba tiene que subir la montaña
                 
                    subirMontaña(actual);
                
                 if(actual.getArriba()){//si esta arriba esquia
                    System.out.println(((System.currentTimeMillis()-inicio)/10000)/6+" Min - "+actual.getNombre()+": Esquiando");
                    actual.setArriba(false);//seteamos la variable del esquiador para decir que esta abajo
                   System.out.println(actual.getNombre()+": Termine de esquiar");
                 }
                }
             }
    
    }
    
    public void subirMontaña(Esquiador actual){
    
         Random  numAleatorio = new Random();
         int num=numAleatorio.nextInt(4)+1;  //Genera un numero aleatorio de 1 a 4
       
         if(num==1) {medio1.subirAmontaña(actual);}
         if(num==2) {medio2.subirAmontaña(actual);}
         if(num==3) {medio3.subirAmontaña(actual);}
         if(num==4) {medio4.subirAmontaña(actual);}
        
    }
    
    public void tomarClases(Esquiador actual){
      
      
        if((System.currentTimeMillis()-inicio)<cierre-70000){
            
            System.out.println(((System.currentTimeMillis()-inicio)/10000)/6+" Min - "+actual.getNombre()+" : Entrando a escuela para tomar clases");
            clases.tomarClases(actual);
        }
        else System.out.println(((System.currentTimeMillis()-inicio)/10000)/6+"Min - "+actual.getNombre()+" : Ya no hay tiempo para tomar clases!");
       
    }
    
    public boolean estaEnHorario(){
    
        return System.currentTimeMillis()-inicio<cierre;
    }
    
      public boolean estaEnHorarioClase(){
    
        return System.currentTimeMillis()-inicio<cierre-70000;
    }
      
       public boolean estaEnHorarioComida(){
    
        return System.currentTimeMillis()-inicio<cierre-30000;
    }
      
        public boolean estaEnHorarioEsquiar(){
    
        return System.currentTimeMillis()-inicio<cierre-10000;
    }
    
    public long horario(){
    
       return ((System.currentTimeMillis()-inicio)/10000)/6;
    }
}
