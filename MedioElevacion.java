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

public class MedioElevacion {
    
    private LinkedList colaEsq = new LinkedList();    
    private int uso;
    private int sillasDisponibles;
    private int molinete;
    private int cantEsq;
    private int salidaEsq;
    private boolean hayGrupo,tiempo;
    private long inicio;
    private String aCadena1;
    
    public MedioElevacion(int cant) {//cant es la cantidad de molinetes que tiene el medio
  
    inicio=0;
     tiempo=false;
     hayGrupo=false;
     molinete=cant;
     uso=0;
     sillasDisponibles=5;
     cantEsq=0;
     salidaEsq=0;
     aCadena1="";
    }
    
    public int getUso(){
    
        return uso;
    }
    
    public void incrementarUso(){
    
        uso=uso+1;
    }
    
    public void subirAmontaña(Esquiador actual){
    
          synchronized(this){
           
              while(hayGrupo||tiempo||sillasDisponibles==0){//si hay una silla completa para subir esperar hasta que salga
                                    //si paso un tiempo prudencial y no se ocupo completamente la silla espera a que se bajen los que quedaron esperando para subir
                                        //si no hay sillas disponibles tiene que esperar
                  try { this.wait();} catch (InterruptedException ex){Logger.getLogger(Escuela_Sky_Snow.class.getName()).log(Level.SEVERE, null, ex);}
              }
               actual.setHorarioLlegada(System.currentTimeMillis());//marca su horario de llegada         
               if(cantEsq==0) inicio=actual.getHorarioLlegada(); //si es el primero en llegar a la silla marca el horario de referencia para despues decidir si paso un tiempo
               
                  cantEsq=cantEsq+1;
                  colaEsq.addFirst(actual);
               
                  if((actual.getHorarioLlegada()-inicio<1000)&&(sillasDisponibles>0)){//si esta en el tiempo estimado y hay sillas disponibles continua
                
                    if(cantEsq==molinete){//si se competo la silla
                     
                            hayGrupo=true;             //avisar que hay grupo para sacar la silla               
                            sillasDisponibles=sillasDisponibles-1;
                    }
                     
                  else {//si no es el esquiador que complete la silla tiene que esperar a que se complete
                      try {this.wait(1000);} catch (InterruptedException ex){Logger.getLogger(Sky.class.getName()).log(Level.SEVERE, null, ex);}
                      
                      if(!hayGrupo && cantEsq<molinete)tiempo=true;// cuando se despierte y verifica que no halla grupo
                  }                                                //avisa que tienen que salir todos por que se le paso el tiempo
              }//sino esta en el tiempo esperado pone una variable en true para sacar a todos
              else                                                      
                   tiempo=true;     
                               
               cantEsq=cantEsq-1;
             if(hayGrupo){  //si se completo la silla  seteo el estado en 1 del esqueador para mandar mensaje que sube la montaña          
                          
                 actual.setEstado(1);           
              }
              else{//sino seteo el estado en 2 del esqueador para mandar mensaje que se canso de espera
                
                    actual.setEstado(2);
                              
              }
               
             if(cantEsq==0){// cuando salga la silla avisa que pueden entrar otros 
                 
                 hayGrupo=false;
                 tiempo=false;
                 
             }
             this.notifyAll();
          }
          
          while(!actual.equals(colaEsq.getLast())){}
          
          synchronized(this){
              
              colaEsq.removeLast();              
             if(actual.getEstado()==1){             
                aCadena1=aCadena1+" "+actual.getNombre();
                actual.setArriba(true);//seteo el estado del esqueador en true por que ya subio
                incrementarUso();
                 salidaEsq=salidaEsq+1;
                 if(salidaEsq==molinete) {
                     System.out.println("Subiendo en silla a montaña: "+aCadena1+" en medio de elevacion: "+molinete);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MedioElevacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     System.out.println("Bajando de silla: "+aCadena1+" en medio de elevacion: "+molinete);
                       sillasDisponibles=sillasDisponibles+1;
                     salidaEsq=0;
                     aCadena1="";
                 }
             }
             else{
                  System.out.println("Me voy me canse de esperar para subir a silla: "+actual.getNombre()+" en medio de elevacion: "+molinete);
           
             }
              this.notifyAll();// notiico
          }
      
        while(salidaEsq!=0){}//bajan todos juntos de la silla cuando subieron la montaña
     
        
    }
    

}
