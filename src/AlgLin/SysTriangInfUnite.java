package AlgLin;

public class SysTriangInfUnite extends SysTriangInf {

    public SysTriangInfUnite(Matrice m, Vecteur v) throws IrregularSysLinException {
        super(m, v);
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Vecteur solution = new Vecteur(getOrdre());

        for (int i = 0; i < getOrdre(); i++) {
            double sum = getSecondMembre().getCoef(i);

            // Perform forward substitution
            for (int j = 0; j < i; j++) {
                sum -= getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
            }

            // Since diagonal coefficients are all 1, no division is necessary
            solution.remplaceCoef(i, sum);
        }

        return solution;
    }

    public static void main(String[] args) {
        try {
            // Matrice triangulaire inférieure unitaire et second membre
            double[][] matData = {
                    {1, 0, 0},
                    {2, 1, 0},
                    {3, 4, 1}};
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
            SysTriangInfUnite sys = new SysTriangInfUnite(matCopie, bCopie);
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
                System.out.println("Test SysTriangInfUnite réussi !");
            } else {
                System.out.println("Test SysTriangInfUnite échoué.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
