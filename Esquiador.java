/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Final Estructrura
 */
public class Esquiador extends Thread {
    
    private boolean pase;
    private boolean arriba; //variable utilizada para verificar si esta arriba o no!                   
    private CentroDeEsqui centro;
    private String nombre;
    private int boleta;
    private long horarioLlegada;
    private int estado;
    
    
    public Esquiador(CentroDeEsqui caidaRapida,String nom) {
        this.pase = true;
        this.arriba = false;
        this.centro = caidaRapida;
        this.nombre=nom;
        this.boleta=0;//la boleta es para comer en la confiteria 1 es menu simple 2 es con postre
        this.estado=0;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    

    public boolean getPase() {
        return pase;
    }

    public long getHorarioLlegada(){
    
        return this.horarioLlegada;
    }
    
    public void setHorarioLlegada(long llegada){
    
        this.horarioLlegada=llegada;
    }
    
    public String getNombre(){
    
        return this.nombre;
    }
    
    public boolean getArriba() {
        return arriba;
    }

    public void setPase(boolean pase) {
        this.pase = pase;
    }

    public void setArriba(boolean arriba) {
        this.arriba = arriba;
    }

    public void setBoleta(int boleta) {
        this.boleta = boleta;
    }

    public int getBoleta() {
        return boleta;
    }
    
   
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Esquiador other = (Esquiador) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
    
    public void subirMontaña(){
    
        centro.subirMontaña(this);
    }
    
     public void run(){
        
         while(centro.estaEnHorario()){
            
             
             
             Random  numAleatorio = new Random();
             int num=numAleatorio.nextInt(3)+1;
           
           
             if(num==1 && centro.estaEnHorarioEsquiar()) centro.esquiar(this);
             if(num==2 && centro.estaEnHorarioComida()) centro.comer(this);
             if(num==3 && centro.estaEnHorarioClase()) centro.tomarClases(this);
            
         }
        
         if(arriba){
         
                System.out.println(nombre+" : Bajando montaña pues cerro el centro de sky..");
                arriba=false;
         }
         System.out.println(nombre+" : La pase muy bien adios...");
      
         
     }
    
    
}
