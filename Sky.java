/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Final Estructrura
 */



public class Sky {

    private int cantEsq;
    private LinkedList colaEsq = new LinkedList();       
    String aCadena1,aCadena2;
    private boolean tiempo=false;     
    private boolean hayGrupo;
    private long comienzoClase;   
    private CabinaInstructores cabina;
    private int salidaEsq;
    private boolean hayInstructor=true;
    
    
    public Sky(CabinaInstructores cabin){
        
        cabina=cabin;
        cantEsq=0;
        aCadena1="";       
        hayGrupo=false;
        comienzoClase=0;
        salidaEsq=0;
        
    }
    
      public void tomarClaseSky(Esquiador actual, Semaphore semCompartido){
        
          synchronized(this){
           
              while(hayGrupo||tiempo){//si hay un grupo esperando para tomar la clase  espera
                  //si hay unos esquiadores que se cansaron de formar un grupo tienen que esperar a que salgan
                  
                  try { this.wait();} catch (InterruptedException ex){Logger.getLogger(Escuela_Sky_Snow.class.getName()).log(Level.SEVERE, null, ex);}
              }
               actual.setHorarioLlegada(System.currentTimeMillis());  //marca su horario de llegada             
               if(cantEsq==0) comienzoClase=actual.getHorarioLlegada();//si es el primero que llego pra tomar la clase marca el horario de referencia para despues 
                                                                      //verificar si paso un tiempo prudencial de espera               
                  cantEsq=cantEsq+1;
                  colaEsq.addFirst(actual);
               
                  if((actual.getHorarioLlegada()-comienzoClase<5000)&&(cabina.getCantidad()>0)){//si esta en horario y hay instructores continua
                
                    if(cantEsq==4){//si es el esquiador numero 4 tomo el semaforo compartido para que los de Snowboard no usen la cabina
                         try {semCompartido.acquire();} catch (InterruptedException ex) {Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);}
                        if(cabina.getCantidad()>0){//si hay instructor aviso que se formo un grupo y decremento un instructor
                            hayGrupo=true;                            
                            cabina.decInstructores();
                        }
                         else {hayGrupo=false;//sino aviso que no se formo un grupo y que se van por que no hay instructor disponibles
                               hayInstructor=false;
                           }
                            semCompartido.release();
                        }
                  else {//sino es el esquiador numero 4 tiene que espera a que se forme el grupo
                      try {this.wait(5000);} catch (InterruptedException ex){Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);}
                      
                      if(!hayGrupo && cantEsq<4)tiempo=true;//cuando se despierte si no se formo un grupo avisa que se tienen que ir por que se cansaron de esperar
                  }
              }
              else //si esta fuera de tiempo o no hay instructores pone en tiempo en true para avisar a que tienen que esperar a entrar para que salgan los que estan ahi                                                     
                   tiempo=true;     
                               
               cantEsq=cantEsq-1;
             if(hayGrupo){ // si se formo el grupo le seteo el estado en 1 para cuando salga sepa que se va por que hay grupo           
                          
                 actual.setEstado(1);           
              }
              else{
                if(hayInstructor==false)//si no habia instructor setea el estado en 0 
                    actual.setEstado(0);
                else
                   actual.setEstado(2);    //si esta fuera de tiempo lo setea en 2          
              }
               
             if(cantEsq==0){
                 
                 hayInstructor=true;
                 hayGrupo=false;
                 tiempo=false;
                
             }
             this.notifyAll();
          }
          
          //el codigo siguiente es para sacarlo de la cola y que muestren sus respectivos mensajes de salida
          while(!actual.equals(colaEsq.getLast())){ }
          
          synchronized(this){
              
              colaEsq.removeLast();              
             if(actual.getEstado()==1){             
                aCadena1=aCadena1+" "+actual.getNombre();
                actual.setArriba(false);
                 salidaEsq=salidaEsq+1;
                 if(salidaEsq==4) {
                     System.out.println("Tomando clase de Sky: "+aCadena1);
                     System.out.println("Terminamos la clase de Sky: "+aCadena1);
                     cabina.incInstructores();
                     salidaEsq=0;
                     aCadena1="";
                 }
             }
             else{
                  if(actual.getEstado()==0)
                      System.out.println("Me voy por que no hay intructores ya: "+actual.getNombre());
                  else
                      System.out.println("Me voy me canse de esperar para formar grupo: "+actual.getNombre());
            
             }
             this.notifyAll();
              
          }
      
        while(salidaEsq!=0){}
     
        
    }
    
}
