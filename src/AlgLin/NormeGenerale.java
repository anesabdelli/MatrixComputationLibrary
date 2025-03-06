package AlgLin;

/**
 * Interface permettant de calculer une norme quelconque d'une matrice.
 */
public interface NormeGenerale {
    /**
     * Calcule la norme d'une matrice.
     * @param m La matrice dont on veut calculer la norme.
     * @return La valeur de la norme.
     */
    double norme(Matrice m);
}
