package AlgLin;

/**
 * Classe implémentant la norme 1 d'une matrice.
 */
public class Norme_1 implements NormeGenerale {
    /**
     * Calcule la norme 1 d'une matrice.
     * @param m La matrice dont on veut calculer la norme.
     * @return La valeur de la norme 1.
     */
    @Override
    public double norme(Matrice m) {
        return m.norme_1();
    }
}
