/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Caida_Rapida;

/**
 *
 * @author Final Estructrura
 */
public class CabinaInstructores{
    
    private int cantidad=5;

    public synchronized int getCantidad() {
        return cantidad;
    }

    public synchronized void incInstructores() {
        this.cantidad = cantidad+1;
    }
    
        public synchronized void decInstructores() {
        this.cantidad = cantidad-1;
    }
}
