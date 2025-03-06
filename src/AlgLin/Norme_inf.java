package AlgLin;

/**
 * Classe implémentant la norme infinie d'une matrice.
 */
public class Norme_inf implements NormeGenerale {
    /**
     * Calcule la norme infinie d'une matrice.
     * @param m La matrice dont on veut calculer la norme.
     * @return La valeur de la norme infinie.
     */
    @Override
    public double norme(Matrice m) {
        return m.norme_inf();
    }
}
