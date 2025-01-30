package AlgLin;

/**
 * Classe abstraite représentant un système linéaire.
 * Cette classe définit la structure d'un système linéaire avec une matrice carrée et un second membre.
 * Elle sert de base pour les différentes implémentations de systèmes linéaires spécifiques.
 */
public abstract class SysLin {

	// Taille du système
	private static int ordre;

	// Matrice représentant le système linéaire
	protected static Matrice matriceSystem;

	// Vecteur représentant le second membre du système
	protected static Vecteur secondMembre;

	/**
	 * Constructeur pour initialiser un système linéaire avec une matrice et un second membre.
	 *
	 * @param m La matrice du système.
	 * @param v Le vecteur représentant le second membre.
	 * @throws IrregularSysLinException Si la matrice n'est pas carrée ou si la taille du second membre ne correspond pas à la taille de la matrice.
	 */
	public SysLin(Matrice m, Vecteur v) throws IrregularSysLinException {
		// Vérifie si la matrice est carrée
		if (m.nbLigne() != m.nbColonne()) {
			throw new IrregularSysLinException("La matrice doit être carrée.");
		}

		// Vérifie que la taille du second membre correspond à la taille de la matrice
		if (v.getTaille() != m.nbLigne()) {
			throw new IrregularSysLinException("La taille du second membre doit correspondre à la taille de la matrice.");
		}

		this.ordre = m.nbLigne();
		this.matriceSystem = m;
		this.secondMembre = v;
	}

	/**
	 * Retourne l'ordre du système linéaire, c'est-à-dire la taille de la matrice (le nombre de lignes/colonnes).
	 *
	 * @return L'ordre du système linéaire.
	 */
	public static int getOrdre() {
		return ordre;
	}

	/**
	 * Retourne la matrice représentant le système linéaire.
	 *
	 * @return La matrice du système.
	 */
	public static Matrice getMatriceSystem() {
		return matriceSystem;
	}

	/**
	 * Retourne le second membre du système linéaire.
	 *
	 * @return Le vecteur représentant le second membre du système.
	 */
	public static Vecteur getSecondMembre() {
		return secondMembre;
	}

	/**
	 * Méthode abstraite pour résoudre le système linéaire.
	 * Chaque sous-classe devra implémenter cette méthode en fonction de la méthode spécifique de résolution du système.
	 *
	 * @return Le vecteur solution du système.
	 * @throws IrregularSysLinException Si le système est irrégulier pendant la résolution.
	 */
	public abstract Vecteur resolution() throws IrregularSysLinException;
}
