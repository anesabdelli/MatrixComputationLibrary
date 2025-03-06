package AlgLin;

import java.io.*;
import java.util.*;

public class Matrice {
	/** Définir ici les attributs de la classe **/
	protected double coefficient[][];
	/** Définition de l'epsilon numérique **/
	public static final double EPSILON = 1.0E-06;


	/**
	 * Constructeur qui crée une matrice vide de dimensions spécifiées.
	 * @param nbligne Nombre de lignes de la matrice.
	 * @param nbcolonne Nombre de colonnes de la matrice.
	 */
	Matrice (int nbligne, int nbcolonne){
		this.coefficient = new double[nbligne][nbcolonne];
	}

	/**
	 * Constructeur qui initialise la matrice avec un tableau 2D.
	 * @param tableau Tableau de coefficients représentant la matrice.
	 */
	Matrice(double[][] tableau){
		coefficient = tableau;
	}

	/**
	 * Constructeur qui initialise la matrice à partir d'un fichier.
	 * @param fichier Nom du fichier contenant les données de la matrice.
	 */
	Matrice(String fichier){
		try {
			Scanner sc = new Scanner(new File(fichier));
			int ligne = sc.nextInt();
			int colonne = sc.nextInt();
			this.coefficient = new double[ligne][colonne];
			for(int i=0; i<ligne;i++)
				for(int j=0; j< colonne; j++)
					this.coefficient[i][j]=sc.nextDouble();
			sc.close();

		}
		catch(FileNotFoundException e) {
			System.out.println("Fichier absent");
		}
	}

    /**
	 * Copie les coefficients d'une autre matrice dans la matrice courante.
	 * @param arecopier Matrice à recopier.
	 */
	public void recopie(Matrice arecopier){
		int ligne, colonne;
		ligne = arecopier.nbLigne(); colonne = arecopier.nbColonne();
		this.coefficient = new double[ligne][colonne];
		for(int i=0; i<ligne; i++)
			for (int j=0;j<colonne;j++)
				this.coefficient[i][j]= arecopier.coefficient[i][j];
	}

	/**
	 * Retourne le nombre de lignes de la matrice.
	 * @return Nombre de lignes.
	 */
	public int nbLigne(){
		return this.coefficient.length;
	}

	/**
	 * Retourne le nombre de colonnes de la matrice.
	 * @return Nombre de colonnes.
	 */
	public int nbColonne(){
		return this.coefficient[0].length;
	}

	/**
	 * Récupère le coefficient à la position spécifiée.
	 * @param ligne Indice de la ligne.
	 * @param colonne Indice de la colonne.
	 * @return Valeur du coefficient.
	 */
	public double getCoef(int ligne, int colonne){
		return this.coefficient[ligne][colonne];
	}

	/**
	 * Remplace un coefficient spécifique de la matrice.
	 * @param ligne Indice de la ligne.
	 * @param colonne Indice de la colonne.
	 * @param value Nouvelle valeur du coefficient.
	 */
	public void remplaceCoef(int ligne, int colonne, double value){
		this.coefficient[ligne][colonne]=value;
	}

	/**
	 * Retourne une représentation textuelle formatée de la matrice.
	 * @return Chaîne représentant la matrice sous forme de tableau structuré.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int lignes = this.nbLigne();
		int colonnes = this.nbColonne();

		// Déterminer la largeur maximale de chaque colonne pour un affichage structuré
		int[] colWidths = new int[colonnes];

		// Calcul de la largeur maximale par colonne
		for (int j = 0; j < colonnes; j++) {
			int maxWidth = 0;
			for (int i = 0; i < lignes; i++) {
				String value = doubleToFraction(this.getCoef(i, j));
				maxWidth = Math.max(maxWidth, value.length());
			}
			colWidths[j] = maxWidth;
		}

		// Génération de l'affichage structuré
		for (int i = 0; i < lignes; i++) {
			for (int j = 0; j < colonnes; j++) {
				String value = doubleToFraction(this.getCoef(i, j));
				sb.append(String.format("%" + colWidths[j] + "s", value)); // Alignement à droite
				if (j < colonnes - 1) {
					sb.append("  "); // Espacement entre colonnes
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	/**
	 * Multiplie la matrice par un scalaire.
	 * @param scalaire Valeur scalaire par laquelle multiplier chaque élément de la matrice.
	 * @return La matrice résultante après multiplication.
	 */
	public Matrice produit(double scalaire){
		int ligne = this.nbLigne();
		int colonne = this.nbColonne();
		for(int i=0; i<ligne;i++)
			for(int j=0; j< colonne; j++)
				this.coefficient[i][j]*=scalaire;
		return this;
	}

	/**
	 * Additionne deux matrices.
	 * @param a Première matrice.
	 * @param b Deuxième matrice.
	 * @return La matrice résultante après addition.
	 */
	static Matrice addition(Matrice a, Matrice b){
		int ligne = a.nbLigne();
		int colonne = a.nbColonne();
		Matrice mat = new Matrice(ligne, colonne);
		for(int i=0; i<ligne;i++)
			for(int j=0; j< colonne; j++)
				mat.coefficient[i][j]=a.coefficient[i][j] + b.coefficient[i][j];
		return mat;
	}

	/**
	 * Additionne deux matrices après vérification de leurs dimensions.
	 * @param a Première matrice.
	 * @param b Deuxième matrice.
	 * @return La matrice résultante après addition.
	 * @throws Exception Si les dimensions des matrices ne correspondent pas.
	 */
	static Matrice verif_addition(Matrice a, Matrice b) throws Exception{
		if((a.nbLigne() == b.nbLigne()) && (a.nbColonne() == b.nbColonne()))
		{
			int ligne = a.nbLigne();
			int colonne = a.nbColonne();
			Matrice mat = new Matrice(ligne, colonne);
			for(int i=0; i<ligne;i++)
				for(int j=0; j< colonne; j++)
					mat.coefficient[i][j]=a.coefficient[i][j] + b.coefficient[i][j];
			return mat;
		}
		else {
			throw new Exception("Les deux matrices n'ont pas les mêmes dimensions !!!");
		}
	}

	/**
	 * Multiplie deux matrices entre elles.
	 * @param a Première matrice.
	 * @param b Deuxième matrice.
	 * @return La matrice résultante après multiplication.
	 */
	static Matrice produit(Matrice a, Matrice b){
		int ligne, colonne;
		ligne = a.nbLigne();
		colonne = b.nbColonne();
		Matrice mat = new Matrice(ligne, colonne);
		for(int i=0; i<ligne;i++)
			for(int j=0; j< colonne; j++)
			{
				mat.coefficient[i][j]=0;
				for(int k=0; k <a.nbColonne();k++)
					mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
			}
		return mat;
	}

	/**
	 * Multiplie deux matrices après vérification de leurs dimensions.
	 * @param a Première matrice.
	 * @param b Deuxième matrice.
	 * @return La matrice résultante après multiplication.
	 * @throws Exception Si les dimensions des matrices ne sont pas compatibles pour la multiplication.
	 */
	static Matrice verif_produit(Matrice a, Matrice b) throws Exception{
		int ligne = 0;
		int colonne = 0;
		if(a.nbColonne()==b.nbLigne())
		{
			ligne = a.nbLigne();
			colonne = b.nbColonne();
		}
		else{
			throw new Exception("Dimensions des matrices à multiplier incorrectes");
		}

		Matrice mat = new Matrice(ligne, colonne);
		for(int i=0; i<ligne;i++)
			for(int j=0; j< colonne; j++)
			{
				mat.coefficient[i][j]=0;
				for(int k=0; k <a.nbColonne();k++)
					mat.coefficient[i][j] += a.coefficient[i][k] * b.coefficient[k][j];
			}
		return mat;
	}

	/**
	 * Calcule l'inverse de la matrice courante.
	 * @return La matrice inverse.
	 * @throws IllegalOperationException Si la matrice n'est pas carrée.
	 * @throws IrregularSysLinException Si un problème survient lors de la résolution du système.
	 */
	public Matrice inverse() throws IllegalOperationException, IrregularSysLinException {
		if (this.nbLigne() != this.nbColonne()) {
			throw new IllegalOperationException("La matrice n'est pas carrée, inversion impossible.");
		}

		int n = this.nbLigne();
		Matrice inverse = new Matrice(n, n);

		// Création de la matrice identité
		Matrice identite = new Matrice(n, n);
		for (int i = 0; i < n; i++) {
			identite.remplaceCoef(i, i, 1.0);
		}

		// Calcul de chaque colonne de l'inverse en résolvant A * X = I
		for (int j = 0; j < n; j++) {
			// Extraire la colonne j de l'identité comme second membre
			Vecteur colonneIdentite = new Vecteur(n);
			for (int i = 0; i < n; i++) {
				colonneIdentite.remplaceCoef(i, identite.getCoef(i, j));
			}

			// Créer une copie de la matrice originale pour éviter les modifications
			Matrice copieMatrice = new Matrice(n, n);
			copieMatrice.recopie(this);

			// Résolution du système via la factorisation LDR (Helder)
			Helder systeme = new Helder(copieMatrice, colonneIdentite);
			Vecteur solution = systeme.resolution();

			// Stocker la solution dans la colonne j de la matrice inverse
			for (int i = 0; i < n; i++) {
				inverse.remplaceCoef(i, j, solution.getCoef(i));
			}
		}

		return inverse;
	}

	/**
	 * Calcule la norme 1 (maximum des sommes absolues des colonnes).
	 * @return La norme 1 de la matrice.
	 */
	public double norme_1() {
		int rows = this.nbLigne();
		int cols = this.nbColonne();
		double max = 0;

		for (int j = 0; j < cols; j++) {
			double sum = 0;
			for (int i = 0; i < rows; i++) {
				sum += Math.abs(this.getCoef(i, j));
			}
			max = Math.max(max, sum);
		}
		return max;
	}

	/**
	 * Calcule la norme infinie (maximum des sommes absolues des lignes).
	 * @return La norme infinie de la matrice.
	 */
	public double norme_inf() {
		int rows = this.nbLigne();
		int cols = this.nbColonne();
		double max = 0;

		for (int i = 0; i < rows; i++) {
			double sum = 0;
			for (int j = 0; j < cols; j++) {
				sum += Math.abs(this.getCoef(i, j));
			}
			max = Math.max(max, sum);
		}
		return max;
	}

	/**
	 * Calcule le conditionnement de la matrice en norme 1.
	 * @return Le nombre de conditionnement (cond_1).
	 * @throws IllegalOperationException si la matrice n'est pas inversible.
	 * @throws IrregularSysLinException en cas d'erreur lors de l'inversion.
	 */
	public double cond_1() throws IllegalOperationException, IrregularSysLinException {
		Matrice inv = this.inverse();
		return this.norme_1() * inv.norme_1();
	}

	/**
	 * Calcule le conditionnement de la matrice en norme infinie.
	 * @return Le nombre de conditionnement (cond_inf).
	 * @throws IllegalOperationException si la matrice n'est pas inversible.
	 * @throws IrregularSysLinException en cas d'erreur lors de l'inversion.
	 */
	public double cond_inf() throws IllegalOperationException, IrregularSysLinException {
		Matrice inv = this.inverse();
		return this.norme_inf() * inv.norme_inf();
	}

	/**
	 * Calcule le conditionnement de la matrice en utilisant une norme matricielle donnée.
	 * @param norme Un objet implémentant l'interface NormeGenerale.
	 * @return Le conditionnement calculé.
	 * @throws IllegalOperationException si la matrice n'est pas inversible.
	 * @throws IrregularSysLinException en cas de problème d'inversion.
	 */
	public double cond(NormeGenerale norme) throws IllegalOperationException, IrregularSysLinException {
		Matrice inv = this.inverse();
		return norme.norme(this) * norme.norme(inv);
	}

	/**
	 * Convertit une valeur double en une chaîne représentant une fraction approchée.
	 * On fixe une tolérance pour déterminer quand la valeur est proche d'un entier.
	 */
	public static String doubleToFraction(double value) {
		final double EPS = 1e-10; // tolérance pour comparer à un entier

		// Si la valeur est proche d'un entier, on l'affiche comme tel.
		if (Math.abs(value - Math.rint(value)) < EPS) {
			return String.valueOf((long)Math.rint(value));
		}

		// Définir un maximum pour le dénominateur (pour éviter des fractions avec de très grands nombres)
		long maxDenom = 1000;
		long bestNumerator = 0;
		long bestDenom = 1;
		double bestError = Double.MAX_VALUE;

		// Recherche du meilleur dénominateur
		for (long denom = 1; denom <= maxDenom; denom++) {
			long numerator = Math.round(value * denom);
			double approx = (double) numerator / denom;
			double error = Math.abs(value - approx);
			if (error < bestError) {
				bestError = error;
				bestNumerator = numerator;
				bestDenom = denom;
			}
			if (bestError < EPS) {
				break;
			}
		}

		// Simplification de la fraction en divisant par le PGCD
		long gcd = pgcd(bestNumerator, bestDenom);
		bestNumerator /= gcd;
		bestDenom /= gcd;

		if (bestDenom == 1) {
			return String.valueOf(bestNumerator);
		}
		return bestNumerator + "/" + bestDenom;
	}

	/**
	 * Calcule le plus grand commun diviseur (PGCD) de deux nombres.
	 */
	public static long pgcd(long a, long b) {
		a = Math.abs(a);
		b = Math.abs(b);
		if (b == 0) return a;
		return pgcd(b, a % b);
	}


	public static void main(String[] args) throws Exception {
		double mat[][]= {{2,1},{0,1}};
		Matrice a = new Matrice(mat);
		System.out.println("construction d'une matrice par affectation d'un tableau :\n"+a);
		Matrice b = new Matrice("src/AlgLin/resources/matrice1.txt");
		System.out.println("Construction d'une matrice par lecture d'un fichier :\n"+b);
		Matrice c = new Matrice(2,2);
		c.recopie(b);
		System.out.println("Recopie de la matrice b :\n"+c);
		System.out.println("Nombre de lignes et colonnes de la matrice c : "+c.nbLigne()+
				", "+c.nbColonne());
		System.out.println("Coefficient (2,2) de la matrice b : "+b.getCoef(1, 1));
		System.out.println("Nouvelle valeur de ce coefficient : 8");
		b.remplaceCoef(1, 1, 8);
		System.out.println("Vérification de la modification du coefficient");
		System.out.println("Coefficient (2,2) de la matrice b : "+b.getCoef(1, 1));
		System.out.println("Addition de 2 matrices : affichage des 2 matrices "+
				"puis de leur addition");
		System.out.println("matrice 1 :\n"+a+"matrice 2 :\n"+b+"somme :\n"+
				Matrice.addition(a,b));
		System.out.println("Produit de 2 matrices : affichage des 2 matrices "+
				"puis de leur produit");
		System.out.println("matrice 1 :\n"+a+"matrice 2 :\n"+b+"produit :\n"+
				produit(a,b));

		/** Script de test pour la methode inverse() **/
		try {
			System.out.println("=== Test de la méthode inverse() ===");

			// Test 1 : Inversion d'une matrice carrée 2x2
			double[][] data = {
					{4, 7},
					{2, 6}
			};
			Matrice A = new Matrice(data);
			System.out.println("Matrice A:");
			System.out.println(A);

			Matrice A_inv = A.inverse();
			System.out.println("Inverse de A:");
			System.out.println(A_inv);

			Matrice produit = Matrice.produit(A, A_inv);
			System.out.println("Produit A * A_inv (devrait être proche de l'identité) :");
			System.out.println(produit);

			// Test 2 : Matrice non carrée (doit lever une exception)
			double[][] nonCarreeData = {
					{1, 2, 3},
					{4, 5, 6}
			};
			Matrice nonCarree = new Matrice(nonCarreeData);
			System.out.println("Test d'une matrice non carrée (doit lever une exception) :");
			nonCarree.inverse();

		} catch (IllegalOperationException e) {
			System.out.println("Exception capturée : " + e.getMessage());
		} catch (IrregularSysLinException e) {
			System.out.println("Erreur de système linéaire : " + e.getMessage());
		}

		/** Script de test pour les normes et le conditionnement **/
		try {
			System.out.println("\n");
			System.out.println("=== Test des normes et du conditionnement ===");

			// Définition d'une matrice test
			double[][] data = {
					{4, 7},
					{2, 6}
			};
			Matrice A = new Matrice(data);
			System.out.println("Matrice A:");
			System.out.println(A);

			// Test des normes
			NormeGenerale norme1 = new Norme_1();
			NormeGenerale normeInf = new Norme_inf();

			double valeurNorme1 = norme1.norme(A);
			double valeurNormeInf = normeInf.norme(A);

			System.out.println("Norme 1 de A : " + valeurNorme1);
			System.out.println("Norme infinie de A : " + valeurNormeInf);

			// Test du conditionnement avec norme_1 et norme_inf
			double cond1 = A.cond(norme1);
			double condInf = A.cond(normeInf);

			System.out.println("Conditionnement de A (norme 1) : " + cond1);
			System.out.println("Conditionnement de A (norme infinie) : " + condInf);

		} catch (IllegalOperationException e) {
			System.out.println("Exception capturée : " + e.getMessage());
		} catch (IrregularSysLinException e) {
			System.out.println("Erreur de système linéaire : " + e.getMessage());
		}
	}
}
