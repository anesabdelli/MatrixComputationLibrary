package AlgLin;

/**
 * Classe représentant une matrice de Hilbert.
 */
public class HilbertMatrice extends Matrice {

    /**
     * Construit une matrice de Hilbert d'ordre n.
     * Chaque coefficient H(i,j) = 1/(i+j+1), indices démarrant à 0.
     * @param n l'ordre de la matrice
     */
    public HilbertMatrice(int n) {
        super(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.remplaceCoef(i, j, 1.0 / (i + j + 1));
            }
        }
    }

    /**
     * Teste l'inversion des matrices de Hilbert pour un ordre donné.
     * @param n Ordre de la matrice de Hilbert.
     */
    public static void testerHilbert(int n) {
        System.out.println("\n================================================");
        System.out.println("TEST DE LA MATRICE DE HILBERT D'ORDRE " + n);
        System.out.println("================================================");

        // Création de la matrice de Hilbert
        HilbertMatrice H = new HilbertMatrice(n);
        System.out.println("Matrice de Hilbert H :");
        System.out.println(H);

        try {
            // Calcul de l'inverse de H
            Matrice H_inv = H.inverse();
            System.out.println("Inverse de H :");
            System.out.println(H_inv);

            // Vérification de l'inversion : H * H_inv doit être proche de l'identité
            Matrice produit = Matrice.produit(H, H_inv);
            System.out.println("Produit H * H_inv (devrait être proche de l'identité) :");
            System.out.println(produit);

            // Calcul des normes et du conditionnement
            NormeGenerale norme1 = new Norme_1();
            NormeGenerale normeInf = new Norme_inf();

            double norm1 = norme1.norme(H);
            double normInf = normeInf.norme(H);
            double cond1 = H.cond(norme1);
            double condInf = H.cond(normeInf);

            System.out.println("Norme 1 de H : " + doubleToFraction(norm1));
            System.out.println("Norme infinie de H : " + doubleToFraction(normInf));
            System.out.println("Conditionnement (norme 1) : " + doubleToFraction(cond1));
            System.out.println("Conditionnement (norme infinie) : " + doubleToFraction(condInf));


        } catch (IllegalOperationException e) {
            System.out.println("⚠️ ERREUR : " + e.getMessage());
        } catch (IrregularSysLinException e) {
            System.out.println("⚠️ Erreur lors de la résolution du système : " + e.getMessage());
        }

        System.out.println("================================================");
    }

    /**
     * Programme principal pour tester les matrices de Hilbert d'ordre 3 à 15.
     */
    public static void main(String[] args) {
        System.out.println("\n********** DÉBUT DES TESTS DES MATRICES DE HILBERT **********");

        for (int n = 3; n <= 15; n++) {
            testerHilbert(n);
        }

        System.out.println("\n********** FIN DES TESTS DES MATRICES DE HILBERT **********");
    }
}
