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
public class Snowboard {

     
    private int cantEsq,salidaEsq;    
    private LinkedList colaEsq = new LinkedList();      
    String aCadena1,aCadena2;
    private boolean tiempo=false,hayInstructor=true;   
    private boolean hayGrupo;
    private long comienzoClase;    
    private CabinaInstructores cabina;
    
    public Snowboard(CabinaInstructores cabin){
        
        cabina=cabin;           
        cantEsq=0;          
        salidaEsq=0;          
        aCadena2="";        
        hayGrupo=false;
        comienzoClase=0;
        
    }
    
    
       public void tomarClaseSnowboard(Esquiador actual,Semaphore semCompartido){
        
          synchronized(this){
           
              while(hayGrupo||tiempo){
                  try { this.wait();} catch (InterruptedException ex){Logger.getLogger(Escuela_Sky_Snow.class.getName()).log(Level.SEVERE, null, ex);}
              }
               actual.setHorarioLlegada(System.currentTimeMillis());               
               if(cantEsq==0)   comienzoClase=actual.getHorarioLlegada();
               
                  cantEsq=cantEsq+1;
                  colaEsq.addFirst(actual);
               if((actual.getHorarioLlegada()-comienzoClase<5000)&&(cabina.getCantidad()>0)){
                
                    if(cantEsq==4){
                        try {semCompartido.acquire();} catch (InterruptedException ex) {Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);}
                        if(cabina.getCantidad()>0){
                            hayGrupo=true;
                            cabina.decInstructores();
                        }
                         else {hayGrupo=false;
                               hayInstructor=false;}
                            semCompartido.release();
                        }
                     else {
                      try {this.wait(5000);} catch (InterruptedException ex){Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);}
                     
                           if(!hayGrupo && cantEsq<4)tiempo=true;
                     }
               }
                else          
                   
                   tiempo=true;
                       
               cantEsq=cantEsq-1;
             if(hayGrupo){            
                          
                 actual.setEstado(1);           
              }
              else{
                    if(hayInstructor==false)
                       actual.setEstado(0);
                    else
                       actual.setEstado(2);                
              
              }
               
             if(cantEsq==0){
                 hayInstructor=true;
                 hayGrupo=false;
                 tiempo=false;
                
             }
             
               this.notifyAll();
          }
          
          while(!actual.equals(colaEsq.getLast())){}
          
          synchronized(this){
              
              colaEsq.removeLast();
             if(actual.getEstado()==1){
                aCadena2=aCadena2+" "+actual.getNombre();
                 //aca juntar a todos mostrar tomando clase
                 actual.setArriba(false);
                 salidaEsq=salidaEsq+1;
                 if(salidaEsq==4) {
                     System.out.println("Tomando clase de Snowboard: "+aCadena2);
                     System.out.println("Terminamos la clase de Snowboard: "+aCadena2);
                     cabina.incInstructores();
                     salidaEsq=0;
                     aCadena2="";
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

