package AlgLin;

/**
 * Exception levée lorsqu'une opération illégale est tentée sur une matrice.
 */
public class IllegalOperationException extends Exception {
  /**
   * Constructeur de l'exception avec un message explicatif.
   *
   * @param message Message décrivant l'erreur.
   */
  public IllegalOperationException(String message) {
    super(message);
  }

  /**
   * Retourne une représentation textuelle de l'exception.
   *
   * @return Chaîne décrivant l'exception avec son message.
   */
  @Override
  public String toString() {
    return "IllegalOperationException: " + super.getMessage();
  }
}
