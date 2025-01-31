package AlgLin;

public class SysTriangSup extends SysLin {

    public SysTriangSup(Matrice m, Vecteur v) throws IrregularSysLinException {
        super(m, v);
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Vecteur solution = new Vecteur(getOrdre());

        // Iterate over rows in reverse order for backward substitution
        for (int i = getOrdre() - 1; i >= 0; i--) {
            double coeff = getMatriceSystem().getCoef(i, i); // Get the diagonal coefficient
            if (coeff == 0) { // If the diagonal coefficient is zero, throw an exception
                throw new IrregularSysLinException("Coefficient nul trouvé sur la diagonale.");
            }

            // Start with the value from the second member
            double sum = getSecondMembre().getCoef(i);

            // Subtract the contributions from already computed variables
            for (int j = i + 1; j < getOrdre(); j++) {
                sum -= getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
            }

            // Compute the solution for x_i
            solution.remplaceCoef(i, sum / coeff);
        }

        return solution;
    }

    public static void main(String[] args) {
        try {
            // Matrice triangulaire supérieure et second membre
            double[][] matData = {{2, 3, 1}, {0, 3, 2}, {0, 0, 4}};
            Matrice mat = new Matrice(matData);
            Vecteur b = new Vecteur(new double[]{5.0, 6.0, 7.0});

            // Afficher la matrice et le second membre
            System.out.println("Matrice du système :");
            System.out.println(mat);
            System.out.println("Second membre b :");
            System.out.println(b);

            // Créer une copie de la matrice et du second membre
            Matrice matCopie = new Matrice(mat.nbLigne(), mat.nbColonne());
            matCopie.recopie(mat);
            Vecteur bCopie = new Vecteur(b.getTaille());
            bCopie.recopie(b);

            // Résoudre le système
            SysTriangSup sys = new SysTriangSup(matCopie, bCopie);
            Vecteur x = sys.resolution();

            // Afficher la solution
            System.out.println("Solution x :");
            System.out.println(x);

            // Calculer Ax - b
            Vecteur Ax = new Vecteur(b.getTaille());
            for (int i = 0; i < b.getTaille(); i++) {
                double sum = 0.0;
                for (int j = 0; j < b.getTaille(); j++) {
                    sum += mat.getCoef(i, j) * x.getCoef(j);
                }
                Ax.remplaceCoef(i, sum);
            }

            // Calculer le résidu Ax - b
            Vecteur residu = new Vecteur(b.getTaille());
            for (int i = 0; i < residu.getTaille(); i++) {
                residu.remplaceCoef(i, Ax.getCoef(i) - b.getCoef(i));
            }

            // Afficher le résidu et sa norme
            System.out.println("Résidu Ax - b :");
            System.out.println(residu);
            double norme = residu.normeLinf();
            System.out.println("Norme du résidu (L∞) : " + norme);

            // Vérifier la norme du résidu
            if (norme < Matrice.EPSILON) {
                System.out.println("Test SysTriangSup réussi !");
            } else {
                System.out.println("Test SysTriangSup échoué.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
