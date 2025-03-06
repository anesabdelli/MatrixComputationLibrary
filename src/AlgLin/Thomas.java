package AlgLin;

import static AlgLin.Matrice.EPSILON;

/**
 * Classe qui implémente l'algorithme de Thomas pour la résolution des systèmes tridiagonaux.
 * Cette classe dérive de SysLin et utilise une matrice tridiagonale stockée dans un objet Mat3Diag.
 */
public class Thomas extends SysLin {

    /**
     * Constructeur de la classe Thomas.
     * @param m La matrice tridiagonale (Mat3Diag) du système.
     * @param v Le vecteur second membre.
     * @throws IrregularSysLinException Si la matrice n'est pas carrée ou si le second membre n'est pas de taille compatible.
     */
    public Thomas(Mat3Diag m, Vecteur v) throws IrregularSysLinException {
        super(m, v);
    }

    /**
     * Implémente l'algorithme de Thomas pour résoudre un système tridiagonal Ax = b.
     * @return Le vecteur solution x.
     * @throws IrregularSysLinException Si le système est irrégulier (division par zéro).
     */
    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        int n = getOrdre();
        Mat3Diag A = (Mat3Diag) getMatriceSystem();
        Vecteur b = getSecondMembre();
        Vecteur x = new Vecteur(n);

        // Cas particulier pour un système 1x1
        if(n == 1) {
            double diag = A.getCoef(0, 0);
            if(Math.abs(diag) < EPSILON)
                throw new IrregularSysLinException("Système irrégulier : division par zéro.");
            x.remplaceCoef(0, b.getCoef(0) / diag);
            return x;
        }

        double[] cPrime = new double[n];
        double[] dPrime = new double[n];

        // Première ligne
        double a00 = A.getCoef(0, 0);
        if(Math.abs(a00) < EPSILON)
            throw new IrregularSysLinException("Système irrégulier : division par zéro à la ligne 0.");
        cPrime[0] = A.getCoef(0, 1) / a00;
        dPrime[0] = b.getCoef(0) / a00;

        // Balayage avant (forward sweep)
        for (int i = 1; i < n; i++) {
            double diag = A.getCoef(i, i);
            double sub = A.getCoef(i, i - 1);
            double denom = diag - sub * cPrime[i - 1];
            if (Math.abs(denom) < EPSILON)
                throw new IrregularSysLinException("Système irrégulier : division par zéro à la ligne " + i + ".");
            cPrime[i] = (i < n - 1) ? A.getCoef(i, i + 1) / denom : 0;
            dPrime[i] = (b.getCoef(i) - sub * dPrime[i - 1]) / denom;
        }

        // Substitution arrière (back substitution)
        x.remplaceCoef(n - 1, dPrime[n - 1]);
        for (int i = n - 2; i >= 0; i--) {
            x.remplaceCoef(i, dPrime[i] - cPrime[i] * x.getCoef(i + 1));
        }
        return x;
    }

    /**
     * Méthode principale de test pour la classe Thomas.
     * Elle exécute plusieurs scénarios :
     * <ul>
     *   <li>Test 1 : Système tridiagonal simple (ordre 3).</li>
     *   <li>Test 2 : Système tridiagonal de grande taille (ordre 10).</li>
     *   <li>Test 3 : Système singulier (doit lever une exception).</li>
     *   <li>Test 4 : Système tridiagonal 1x1.</li>
     * </ul>
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("========== TEST COMPLET DE LA MÉTHODE DE THOMAS ==========\n");

        // ----- Test 1 : Exemple donné dans le TD 2 (ordre 4) -----
        try {
            System.out.println("🔹 Test 1 : Système tridiagonal (ordre 4)");

            double[][] coef1 = {
                    { -1, -1, -1,  0 }, // sous-diagonale
                    {  2,  2,  2,  2 }, // diagonale principale
                    { -1, -1, -1,  0 }  // sur-diagonale
            };

            Mat3Diag A1 = new Mat3Diag(coef1);
            Vecteur b1 = new Vecteur(new double[]{ -2, -2, -2, 23 });

            System.out.println("Matrice A1 :\n" + A1);
            System.out.println("Second membre b1 : " + b1);

            Thomas systeme1 = new Thomas(A1, b1);
            Vecteur sol1 = systeme1.resolution();
            System.out.println("\n✅ Solution calculée : " + sol1);

            // Vérification du résidu Ax - b
            Vecteur Ax1 = Mat3Diag.produit(A1, sol1);
            Vecteur residu1 = new Vecteur(b1.getTaille());
            for (int i = 0; i < b1.getTaille(); i++) {
                residu1.remplaceCoef(i, Ax1.getCoef(i) - b1.getCoef(i));
            }
            System.out.println("\n🔍 Résidu (Ax1 - b1) : " + residu1);
            System.out.println("🔍 Norme infinie du résidu : " + residu1.normeLinf());

            // Vérification explicite avec la solution attendue : (1,4,9,16)
            Vecteur expected = new Vecteur(new double[]{1, 4, 9, 16});
            System.out.println("\n📌 Solution attendue : " + expected);

            Vecteur diffAttendue = new Vecteur(sol1.getTaille());
            for(int i = 0; i < sol1.getTaille(); i++){
                diffAttendue.remplaceCoef(i, sol1.getCoef(i) - expected.getCoef(i));
            }

            double erreur = diffAttendue.normeLinf();
            System.out.println("🔍 Différence avec la solution attendue : " + diffAttendue);
            System.out.println("🔍 Norme infinie de la différence : " + diffAttendue.normeLinf());

            if(diffAttendue.normeLinf() < EPSILON){
                System.out.println("🎉 Test validé avec succès : La solution est correcte.");
            } else {
                System.out.println("⚠️ Test non validé : La solution diffère significativement de l'attendue.");
            }

        } catch (Exception e) {
            System.out.println("❌ Test échoué : " + e.getMessage());
            e.printStackTrace();
        }


        // ----- Test 2 : Système tridiagonal de grande taille (ordre 10) -----
        try {
            System.out.println("🔹 Test 2 : Système tridiagonal de grande taille (ordre 10)");
            int n2 = 10;
            double[][] coef2 = new double[3][n2];
            // Remplissage : diagonale = 4, sous et sur-diagonale = 1
            for (int j = 0; j < n2; j++) {
                coef2[1][j] = 4; // Diagonale principale
            }
            for (int j = 0; j < n2 - 1; j++) {
                coef2[0][j] = 1; // Sous-diagonale
                coef2[2][j] = 1; // Sur-diagonale
            }
            Mat3Diag A2 = new Mat3Diag(coef2);
            double[] b2Array = new double[n2];
            for (int i = 0; i < n2; i++) {
                b2Array[i] = i + 1; // Second membre : 1, 2, ..., 10
            }
            Vecteur b2 = new Vecteur(b2Array);
            System.out.println("Matrice A2 :\n" + A2);
            System.out.println("Second membre b2 : " + b2);
            Thomas systeme2 = new Thomas(A2, b2);
            Vecteur sol2 = systeme2.resolution();
            System.out.println("Solution x2 : " + sol2);
            // Calcul du résidu
            Vecteur Ax2 = Mat3Diag.produit(A2, sol2);
            Vecteur residu2 = new Vecteur(b2.getTaille());
            for (int i = 0; i < b2.getTaille(); i++) {
                residu2.remplaceCoef(i, Ax2.getCoef(i) - b2.getCoef(i));
            }
            System.out.println("Résidu Ax2 - b2 : " + residu2);
            System.out.println("Norme infinie du résidu : " + residu2.normeLinf() + "\n");
        } catch (Exception e) {
            System.out.println("❌ Test 2 échoué : " + e.getMessage() + "\n");
        }

        // ----- Test 3 : Système tridiagonal singulier (attendu : exception) -----
        try {
            System.out.println("🔹 Test 3 : Système tridiagonal singulier (attendu : exception)");
            double[][] coef3 = {
                    {1, 1, 0},         // Sous-diagonale
                    {0, 3, 2},         // Diagonale principale avec 0 en position (0,0)
                    {1, 1, 0}          // Sur-diagonale
            };
            Mat3Diag A3 = new Mat3Diag(coef3);
            Vecteur b3 = new Vecteur(new double[]{3, 5, 4});
            System.out.println("Matrice A3 :\n" + A3);
            System.out.println("Second membre b3 : " + b3);
            Thomas systeme3 = new Thomas(A3, b3);
            Vecteur sol3 = systeme3.resolution();
            System.out.println("❌ Test 3 échoué : aucune exception levée, solution : " + sol3 + "\n");
        } catch (Exception e) {
            System.out.println("✅ Test 3 réussi : Exception levée -> " + e.getMessage() + "\n");
        }

        // ----- Test 4 : Système tridiagonal 1x1 -----
        try {
            System.out.println("🔹 Test 4 : Système tridiagonal 1x1");
            // Pour un système 1x1, le tableau doit être 3 x 1.
            double[][] coef4 = {
                    {0},    // Sous-diagonale (non utilisée)
                    {5},    // Diagonale unique
                    {0}     // Sur-diagonale (non utilisée)
            };
            Mat3Diag A4 = new Mat3Diag(coef4);
            Vecteur b4 = new Vecteur(new double[]{10});
            System.out.println("Matrice A4 :\n" + A4);
            System.out.println("Second membre b4 : " + b4);
            Thomas systeme4 = new Thomas(A4, b4);
            Vecteur sol4 = systeme4.resolution();
            System.out.println("Solution x4 : " + sol4);
            // Calcul du résidu
            Vecteur Ax4 = Mat3Diag.produit(A4, sol4);
            Vecteur residu4 = new Vecteur(b4.getTaille());
            for (int i = 0; i < b4.getTaille(); i++) {
                residu4.remplaceCoef(i, Ax4.getCoef(i) - b4.getCoef(i));
            }
            System.out.println("Résidu Ax4 - b4 : " + residu4);
            System.out.println("Norme infinie du résidu : " + residu4.normeLinf() + "\n");
        } catch (Exception e) {
            System.out.println("❌ Test 4 échoué : " + e.getMessage() + "\n");
        }

        System.out.println("========== FIN DES TESTS DE THOMAS ==========");
    }
}
