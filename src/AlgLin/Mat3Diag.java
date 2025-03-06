package AlgLin;

/**
 * Classe représentant une matrice tridiagonale d'ordre n.
 * La matrice est stockée dans un tableau à 3 lignes et n colonnes.
 * Les trois lignes représentent respectivement la sous-diagonale, la diagonale principale et la sur-diagonale.
 * Les méthodes nbLigne() et nbColonne() sont surchargées pour retourner l'ordre n de la matrice (le système est ainsi considéré comme carré).
 */
public class Mat3Diag extends Matrice {

    /**
     * Constructeur qui crée une matrice tridiagonale vide d'ordre n à partir des dimensions fournies.
     * @param dim1 Le nombre de lignes du tableau de stockage (doit être égal à 3).
     * @param dim2 Le nombre de colonnes du tableau (définit l'ordre de la matrice).
     * @throws IllegalOperationException Si dim1 n'est pas égal à 3.
     */
    public Mat3Diag(int dim1, int dim2) throws IllegalOperationException {
        super(new double[dim1][dim2]);
        if (dim1 != 3) {
            throw new IllegalOperationException("La première dimension doit être 3 pour une matrice tridiagonale.");
        }
    }

    /**
     * Constructeur qui initialise la matrice tridiagonale avec un tableau donné.
     * Le tableau doit comporter exactement 3 lignes.
     * @param tableau Tableau de coefficients (3 lignes, n colonnes).
     * @throws IllegalOperationException Si le tableau n'a pas exactement 3 lignes.
     */
    public Mat3Diag(double[][] tableau) throws IllegalOperationException {
        super(tableau);
        if (tableau.length != 3) {
            throw new IllegalOperationException("Une matrice tridiagonale doit contenir exactement 3 lignes.");
        }
    }

    /**
     * Constructeur qui crée une matrice tridiagonale vide d'ordre n.
     * @param n L'ordre (nombre de lignes/colonnes du système considéré) de la matrice.
     */
    public Mat3Diag(int n) {
        super(new double[3][n]);
    }

    /**
     * Renvoie l'ordre de la matrice tridiagonale (le nombre de colonnes du tableau de stockage).
     * @return L'ordre n du système.
     */
    @Override
    public int nbLigne() {
        return coefficient[0].length;
    }

    /**
     * Renvoie l'ordre de la matrice tridiagonale (le nombre de colonnes du tableau de stockage).
     * @return L'ordre n du système.
     */
    @Override
    public int nbColonne() {
        return coefficient[0].length;
    }

    /**
     * Renvoie le coefficient de la matrice tridiagonale en position (i, j).
     * <ul>
     *   <li>Si i == j, renvoie le coefficient de la diagonale principale (stocké dans la deuxième ligne).</li>
     *   <li>Si i == j + 1, renvoie le coefficient de la sous-diagonale (première ligne).</li>
     *   <li>Si i + 1 == j, renvoie le coefficient de la sur-diagonale (troisième ligne).</li>
     *   <li>Sinon, renvoie 0.</li>
     * </ul>
     * @param i Indice de la ligne.
     * @param j Indice de la colonne.
     * @return Le coefficient correspondant ou 0 si hors de la tridiagonale.
     */
    @Override
    public double getCoef(int i, int j) {
        if (i == j) {
            return coefficient[1][j]; // Diagonale principale
        } else if (i == j + 1) {
            return coefficient[0][j]; // Sous-diagonale
        } else if (i + 1 == j) {
            return coefficient[2][j - 1]; // Sur-diagonale
        }
        return 0;
    }

    /**
     * Méthode statique pour calculer le produit d'une matrice tridiagonale par un vecteur.
     * @param mat La matrice tridiagonale.
     * @param v Le vecteur à multiplier.
     * @return Le vecteur résultant du produit.
     */
    public static Vecteur produit(Mat3Diag mat, Vecteur v) {
        int n = mat.nbLigne();
        Vecteur res = new Vecteur(n);
        for (int i = 0; i < n; i++) {
            double somme = mat.getCoef(i, i) * v.getCoef(i);
            if (i > 0) {
                somme += mat.getCoef(i, i - 1) * v.getCoef(i - 1);
            }
            if (i < n - 1) {
                somme += mat.getCoef(i, i + 1) * v.getCoef(i + 1);
            }
            res.remplaceCoef(i, somme);
        }
        return res;
    }

    /**
     * Redéfinit l'affichage de la matrice tridiagonale en considérant uniquement les coefficients non nuls.
     * @return Une chaîne formatée représentant la matrice tridiagonale de taille n x n.
     */
    @Override
    public String toString() {
        int n = nbLigne();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double coef = getCoef(i, j);
                sb.append(String.format("%10.4f", coef));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Méthode principale de test pour la classe Mat3Diag.
     * Elle vérifie la création, le produit matrice-vecteur et la gestion des erreurs.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("========== TEST DE LA CLASSE Mat3Diag ==========");
        try {
            // Test de création correcte
            double[][] coef = {
                    {1, 1, 0},    // Sous-diagonale       | 2 1 0 |
                    {2, 3, 2},    // Diagonale principale | 1 3 1 |
                    {1, 1, 0}     // Sur-diagonale        | 0 1 2 |
            };
            Mat3Diag mat = new Mat3Diag(coef);
            System.out.println("Matrice tridiagonale (ordre " + mat.nbLigne() + "):\n" + mat);

            // Test du produit matrice-vecteur
            Vecteur v = new Vecteur(new double[]{1, 2, 3});
            System.out.println("Vecteur v: " + v);
            Vecteur produit = Mat3Diag.produit(mat, v);
            System.out.println("Produit Mat3Diag * v: " + produit);

            // Test avec le constructeur Mat3Diag(int n)
            Mat3Diag mat2 = new Mat3Diag(4);
            // Remplissage d'exemple pour une matrice tridiagonale 4x4
            // Sous-diagonale (utilisée pour les lignes 1 à 3)
            mat2.coefficient[0][0] = 1;
            mat2.coefficient[0][1] = 2;
            mat2.coefficient[0][2] = 3;
            // Diagonale principale
            mat2.coefficient[1][0] = 4;
            mat2.coefficient[1][1] = 5;
            mat2.coefficient[1][2] = 6;
            mat2.coefficient[1][3] = 7;
            // Sur-diagonale (utilisée pour les lignes 0 à 2)
            mat2.coefficient[2][0] = 8;
            mat2.coefficient[2][1] = 9;
            mat2.coefficient[2][2] = 10;
            System.out.println("Matrice tridiagonale 2 (ordre " + mat2.nbLigne() + "):\n" + mat2);

            // Test de la gestion d'erreur : création avec un tableau à mauvais nombre de lignes
            try {
                double[][] mauvaisCoef = { {1, 2}, {3, 4} }; // Seulement 2 lignes
                Mat3Diag matErr = new Mat3Diag(mauvaisCoef);
                System.out.println("Erreur non détectée !");
            } catch (IllegalOperationException e) {
                System.out.println("Erreur correctement levée : " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
        System.out.println("========== FIN TEST Mat3Diag ==========");
    }
}
