import java.io.*;
import vistes.*;
import domini.*;
import dades.*;
/*;
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lipi
 */
public class main {

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run(){
                try {
                    kVistes vistaPrincipal = new kVistes();
                    if (vistaPrincipal != null) {
                        vistaPrincipal.mostraVPrincipal();
                    }
                } catch (Exception ex) {
                    System.out.println("MAIN.java => Exception!!" +ex.getMessage());
                }
            }
        });
    }
}