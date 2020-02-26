/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Caida_Rapida;

import java.util.Random;
import java.util.concurrent.Semaphore;


/**
 *
 * @author Angel
 */
public class Escuela_Sky_Snow {
    
    private CabinaInstructores cabina=new CabinaInstructores();//tiene una cabina de instructores compartida por la clase de SKY y Snowboard
    private Sky claseDeSky; //tiene una sala de SKY
    private Snowboard claseDeSnowboard;//una sala de Snowboard
    private Semaphore semCompartido;
 
 

          
    public Escuela_Sky_Snow(){
        
       claseDeSky=new Sky(cabina);
       claseDeSnowboard=new Snowboard(cabina);
       semCompartido=new Semaphore(1);
    }
    
    public void tomarClases(Esquiador esq){
           
        if(!esq.getArriba()) esq.subirMontaña();//si no esta arriba sube la montaña
               
                if(esq.getArriba()){//si esta arriba
                     if(cabina.getCantidad()!=0){//si hay instructores disponibles entra a tomar clase de SKY o Snowboard
                        Random  numAleatorio = new Random();
                        int num=numAleatorio.nextInt(2)+1;
                        if(num==1)claseDeSky.tomarClaseSky(esq,semCompartido);
                         if(num==2)claseDeSnowboard.tomarClaseSnowboard(esq,semCompartido);
                }
                else System.out.println("Me voy no hay instructores diponibles! soy: "+esq.getNombre());
            }
    }
           
}
    

