package AlgLin;

/**
 * Exception levée lorsque la valeur demandée est en dehors de l'intervalle
 * délimité par les points de support de l'interpolation.
 */
public class DataOutOfRangeException extends Exception {

    /**
     * Constructeur avec message d'erreur.
     *
     * @param message le message d'erreur à afficher.
     */
    public DataOutOfRangeException(String message) {
        super(message);
    }

    /**
     * Constructeur par défaut.
     */
    public DataOutOfRangeException() {
        super("Donnée hors de portée.");
    }
}
