/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vistes;

import domini.ControladorProgrames;

/**
 *
 * @author lipi
 */
public class kVistes 
{
    /** Tindra:
     * - Controladors de domini
     * - Controladors de vistes
     * - VistaPrincipal
     */
    ControladorProgrames CPG;
    kVistaGProgrames kvGProgs;
    VistaPrincipal vPrincipal;
    
    public kVistes() throws Exception
    {
        
        /**Controladors del domini*/
        CPG = new ControladorProgrames();
        
        /**Controladors de les vistes (han de tenir els controladors de domini que necessitin*/
        kvGProgs = new kVistaGProgrames(CPG);

        /**Vista principal (ha de tenir totes les vistes*/
        vPrincipal = new VistaPrincipal(kvGProgs.getVista());
        
        
    }
    
    public void mostraVPrincipal()
    {
        vPrincipal.setVisible(true);
    }
}
