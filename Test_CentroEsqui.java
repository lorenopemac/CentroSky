/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Final Estructrura
 */
public class Test_CentroEsqui {
 
    public static void main(String[] args){
    
    CentroDeEsqui a=new CentroDeEsqui();
    Esquiador e1=new Esquiador(a,"Esquiador 1");
    Esquiador e2=new Esquiador(a,"Esquiador 2");
    Esquiador e3=new Esquiador(a,"Esquiador 3");
    Esquiador e4=new Esquiador(a,"Esquiador 4");
    Esquiador e5=new Esquiador(a,"Esquiador 5");
    Esquiador e6=new Esquiador(a,"Esquiador 6");
    Esquiador e7=new Esquiador(a,"Esquiador 7");
    Esquiador e8=new Esquiador(a,"Esquiador 8");
    Esquiador e9=new Esquiador(a,"Esquiador 9");
    Esquiador e10=new Esquiador(a,"Esquiador 10");
    Esquiador e11=new Esquiador(a,"Esquiador 11");
    Esquiador e12=new Esquiador(a,"Esquiador 12");
    Esquiador e13=new Esquiador(a,"Esquiador 13");
    Esquiador e14=new Esquiador(a,"Esquiador 14");
    Esquiador e15=new Esquiador(a,"Esquiador 15");
    Esquiador e16=new Esquiador(a,"Esquiador 16");
    Esquiador e17=new Esquiador(a,"Esquiador 17");
    Esquiador e18=new Esquiador(a,"Esquiador 18");
    Esquiador e19=new Esquiador(a,"Esquiador 19");
        
        e1.start();
        e2.start();
        e3.start();
        e4.start();
        e5.start();
        e6.start();
        e7.start();
        e8.start();
        e9.start();
        e10.start();
        e11.start();
        e12.start();
        e13.start();
        e14.start();
        e15.start();
        e16.start();
        e17.start();
        e18.start();
        e19.start();        
        try {
            e1.join();
            e2.join();
            e3.join();
            e4.join();
            e5.join();
            e6.join();
            e7.join();
            e8.join();
            e9.join();
            e10.join();
            e11.join();
            e12.join();
            e13.join();
            e14.join();
            e15.join();
            e16.join();
            e17.join();
            e18.join();
            e19.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Test_CentroEsqui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        a.mostrarUsosMedios();
        
    }
    
}
