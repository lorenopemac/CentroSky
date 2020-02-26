/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Final Estructrura
 */
public class Confiteria {
    
    private Semaphore menuSimple;
    private Semaphore menuPostre;
    private int cantidad;
    private int ocupado;
    private LinkedList cola = new LinkedList();   
    
    public Confiteria() {
    
        menuSimple=new Semaphore(2);//pueden pasar a retirar comida dos personas
        menuPostre=new Semaphore(1);//el postre se retira de a una persona
        cantidad=100;// la confiteria tiene capacidad para 100 personas
        ocupado=0;//contador para ver cuantas personas estan en la confiteria
        
    }
    
    public void comer(Esquiador esq){
   
        synchronized(this){
        
            while(ocupado>=cantidad){//si esta llena la confiteria tiene que esperar
            
                try {this.wait();} catch (InterruptedException ex){Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);}
            }
        
           cola.addFirst(esq);
           Random  numAleatorio = new Random();
           int num=numAleatorio.nextInt(2)+1; //numero para elegir menu
           //paga el menu que compro menu1= comida menu2=comida y postre
           if(num==1)System.out.println(esq.getNombre()+" page por comida");
           else System.out.println(esq.getNombre()+" pague por comida y postre");
           esq.setBoleta(num);//le damos la boleta al esquiador
           ocupado=ocupado+1;  //ocupamos un lugar en la confiteria        
        }
        while(!(esq.equals(cola.getLast()))){}//espera activa para que salgan el primero que pago en la caja
        

        try {
            menuSimple.acquire();//tomamos el menu simple ya que todos comen la comida
            cola.removeLast();//saco al esquiador de la cola
            esq.setBoleta(esq.getBoleta()-1);//le saca la parte de comida al esquiador
            menuSimple.release();//otro puede pasar a comprar la comida
            System.out.println("Comiendo.. soy:"+esq.getNombre());
            Thread.sleep(1000);
            if(esq.getBoleta()==1){//si a la boleta le quedo un uno quiere decir que le queda el postre
               menuPostre.acquire();//tomo el semaforo de postre
               esq.setBoleta(0);//pongo en 0 la boleta ya que consumio todo lo que pago
               menuPostre.release(); //suelto el menu del postre
               System.out.println("comiendo postre.. soy:"+esq.getNombre());
               Thread.sleep(500);
            }
                       
          synchronized(this){
              ocupado=ocupado-1;//libero un lugar
              System.out.println("termine me voy :"+esq.getNombre()); //se a de la confiteria
              this.notifyAll();
          }
          
                        
        } catch (InterruptedException ex) {Logger.getLogger(Confiteria.class.getName()).log(Level.SEVERE, null, ex);}
                      
        
    }
    
}
