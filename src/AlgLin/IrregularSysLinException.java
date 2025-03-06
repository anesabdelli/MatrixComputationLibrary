package AlgLin;

/**
 * Exception levée lorsqu'un système linéaire est détecté comme irrégulier lors de la résolution.
 */
public class IrregularSysLinException extends Exception {
    /**
     * Construit une nouvelle exception IrregularSysLinException avec un message personnalisé.
     * @param message Message personnalisé décrivant l'irrégularité.
     */
    public IrregularSysLinException(String message) {
        super(message);
    }

    /**
     * Retourne une représentation textuelle de l'exception.
     * @return Le message indiquant l'irrégularité du système.
     */
    @Override
    public String toString() {
        return "IrregularSysLinException : " + super.getMessage();
    }
}
