/**
 * La classe RepositoriProgrames conte la llista de programes actual carregada
 * en memoria.
 * 
 * @author  Felip Moll
 * @version 0.1, 30 Maig 2008 
 * 
 * Versió #2a Entrega#
 */
package dades;

import domini.ClasseAmbClau;
import java.util.LinkedList;

public class RepositoriProgrames<K, T extends ClasseAmbClau<K>> {

    private LinkedList<T>[][] llistaProgrames;
    private GestorDisc<LinkedList<T>[][]> gestDisc;
    private GestorDisc<LinkedList> gestDisc2;

    public RepositoriProgrames() throws Exception {

        try {

            LinkedList llistaAux;
            gestDisc = new GestorDisc("[[Ljava.util.LinkedList;");
            llistaAux = gestDisc.carregaTots("RepositoriProgrames.db");

            if (llistaAux.size() == 1) {
                llistaProgrames = (LinkedList<T>[][]) llistaAux.get(0);
            }

        } catch (GestorDiscException g) {
            llistaProgrames = new LinkedList[3][10];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 10; j++) {
                    llistaProgrames[i][j] = new LinkedList<T>();
                }
            }
            exportaProgrames("RepositoriProgrames.db");
        }
    }

    /**
     *  Afegeix un Objecte programa a la llista.  
     *  @param  T Objecte a insertar.
     *  @param format Numero corresponent al format: 0:Normal 1:Continu 2:Directe
     *  @param categoria Numero corresponent a la categoria:
     *  Categoria ve donat per un INT de 0 a 10:
     * "Altres", "Adults", "Concurs", "Documental", "Esport","Infantil", 
     * "Musica", "Noticies", "Pelicula", "Serie", "Tertulia";
     *  @pre    -
     *  @post   S'ha inserit el programa al repositori a la llista que li correspon.
     *  @return Cert si s'ha inserit, fals si ja existia amb el mateix nom.
     */
    public boolean afegirPrograma(T programa, int format, int categoria) {
        int[] resultat = existeixPrograma(programa.getClau());

        if (resultat != null) {
            return false;
        } else {
            llistaProgrames[format][categoria].add(programa);
        }
        return true;
    }

    /**
     *  Elimina un programa de la llista.  
     *  @param  nomPrograma Clau del programa a eliminar.
     *  @pre    -
     *  @post   S'ha eliminat el programa de la llista.
     */
    public void eliminarPrograma(K nomPrograma) {
        int[] resultat = existeixPrograma(nomPrograma);
        if (resultat != null) {
            llistaProgrames[resultat[0]][resultat[1]].remove(resultat[2]);
        }
    }

    /**
     *  Elimina tots els programes de la llista.  
     *  @pre    -
     *  @post   S'ha buidat la llista.
     */
    public void buidarLlista() {
        llistaProgrames = new LinkedList[3][11];
    }

    /**
     * Sense copiar la llista d'objectes, la obte i envia.
     * @pre  -
     * @post -
     * @return Una llista de objectes T ordenats per [format][categoria]
     */
    public LinkedList<T>[][] llistarProgrames() {
        return llistaProgrames;
    }

    /**
     * Obte una subllista de la llistaProgrames tals que compleixen amb el format especificat.
     * @param format Es el format del qual es volen obtenir els programes, 0:Normal, 1:Continu
     * 2:Directe
     * @param sure Serveix per el polimorifsme, pot tenir qualsevol valor.
     * @pre  format va de 0..2
     * @post -
     * @return Una llista de objectes T que estaven dins la llista de "format".
     */
    public LinkedList<T>[] llistarProgrames(int format, boolean sure) {
        return llistaProgrames[format];
    }

    /**
     * Obte una subllista de la llistaProgrames tals que compleixen amb la categoria especificada.
     * Ho ordena per format.
     * @param categoria Numero corresponent a la categoria:
     *  Categoria ve donat per un INT de 0 a 10:
     * "Altres", "Adults", "Concurs", "Documental", "Esport","Infantil", 
     * "Musica", "Noticies", "Pelicula", "Serie", "Tertulia";
     * @pre  categoria va de 0..10
     * @post llistaResultant es una llista ordenada per format que nomes conte Objectes de
     * la categoria pasada per parametre.
     * @return Una llista de objectes T que pertanyen exclusivament a la categoria.
     */
    public LinkedList<T>[] llistarProgrames(int categoria) {
        LinkedList<T> llistaResultant[] = new LinkedList[3];
        for (int i = 0; i < 3; i++) {
            llistaResultant[i] = llistaProgrames[i][categoria];
        }
        return llistaResultant;
    }

    /**
     *  Cerca de la llista de Objectes T de format i categoria determinats,
     *   el que te la clau "nom"
     *  @param nom Es la clau de l'objecte que estem cercant.
     *  @param format Es el format de l'objecte que cerquem.
     *  @param categoria Es la categoria de l'objecte que cerquem.
     *  @pre    format esta entre 0..2, categoria 0..10
     *  @post   -
     *  @return Retorna l'Objecte T tal que la clau de T es nom, null altrament.
     */
    public T getPrograma(int format, int categoria, K nom) {
        for (int i = 0; i < llistaProgrames[format][categoria].size(); i++) {
            if (nom.equals(llistaProgrames[format][categoria].get(i).getClau())) {
                return llistaProgrames[format][categoria].get(i);
            }
        }
        return null;
    }

    /**
     *  Cerca de la llista de Objectes T el que te la clau "nom"
     *  @param nom Es la clau de l'objecte que estem cercant.
     *  @pre    -
     *  @post   -
     *  @return Retorna l'Objecte T tal que la clau de T es nom, null altrament.
     */
    public T getPrograma(K nom) {
        int[] resultat = existeixPrograma(nom);
        if (resultat != null) {
            return llistaProgrames[resultat[0]][resultat[1]].get(resultat[2]);
        }
        return null;
    }

    /**
     *  Cerca un Programa amb un nom, i n'obté l'index en que es troba.
     *  @param nomPrograma El nom del programa a obtenir. 
     *  @pre    -
     *  @post   -
     *  @return La posició de la llista en que es troba, si no hi és -1.
     */
    public int[] existeixPrograma(K nomPrograma) {
        int[] resultat = new int[3]; //0Format, 1categoria i 2index
        int listSize;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                listSize = llistaProgrames[i][j].size();
                for (int k = 0; k < listSize; k++) {

                    if (nomPrograma.equals(llistaProgrames[i][j].get(k).getClau())) {
                        resultat[2] = k;
                    }
                    resultat[1] = j;
                    resultat[0] = i;
                    return resultat;
                }
            }
        }
        return null;
    }

    /**
     *  Calcula el nombre de programes que hi ha a la llista en total.
     *  @pre    -
     *  @post   -
     *  @return Nombre de programes que hi ha al repositori.
     */
    public int getListSize() {
        int total = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                total = total + llistaProgrames[i][j].size();
            }
        }
        return total;
    }

    /**
     *  Donat un nom de fitxer, interactua amb el gestor de disc per guardar la llista
     *    del repositori a disc.
     *  @pre    -
     *  @post   S'ha guardat el repositori al disc.
     *  @return true si ha anat be, fals altrament.
     */
    public boolean exportaProgrames(String nomFitxer) throws GestorDiscException {

        LinkedList llistaAux = new LinkedList();
        llistaAux.add(llistaProgrames);
        gestDisc2 = new GestorDisc("java.util.LinkedList");
        gestDisc2.guardaUn(llistaAux, nomFitxer);
        return true;
    }

    /**
     *  Donat un nom de fitxer, interactua amb el gestor de disc per obtenir una
     *  repositori que hi ha guardat.
     *  @pre    -
     *  @post   S'ha carregat el repositori de disc.
     *  @return true si ha anat be, fals altrament.
     */
    public boolean importaProgrames(String rutaFitxer) throws GestorDiscException {
        LinkedList llistaAux;
        llistaAux = gestDisc.carregaTots(rutaFitxer);

        if (llistaAux.size() == 1) {
            llistaProgrames = (LinkedList<T>[][]) llistaAux.get(0);
            return true;
        }

        return false;
    }

    /**
     *  Sincronitza el repositori al disc, al fitxer RepositoriProgrames.dat
     *  @pre    -
     *  @post   S'ha guardat el repositori a disc.
     *  @return true si ha anat be, fals altrament.
     */
    public boolean saveAll() throws GestorDiscException {
        try {
            gestDisc.esborra("RepositoriProgrames.db");
            return exportaProgrames("RepositoriProgrames.db");
        } catch (GestorDiscException g) {
            return exportaProgrames("RepositoriProgrames.db");
        }
    }
}
