package AlgLin;

public class SysDiagonal extends SysLin {

    public SysDiagonal(Matrice m, Vecteur v) throws IrregularSysLinException {
        super(m, v);
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Vecteur solution = new Vecteur(getOrdre());

        for(int i=0;i<getOrdre();i++) {

            double coeff = matriceSystem.getCoef(i, i);
            solution.remplaceCoef(i, secondMembre.getCoef(i) / coeff);

        }
        return solution;
    }

    public static void main(String[] args) {
        try {
            // Matrice diagonale et second membre
            double[][] matData = {
                    {2, 0, 0},
                    {0, 3, 0},
                    {0, 0, 4}};
            Matrice mat = new Matrice(matData);
            Vecteur b = new Vecteur(new double[]{4.0, 6.0, 8.0});

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
            SysDiagonal sys = new SysDiagonal(matCopie, bCopie);
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
                System.out.println("Test SysDiagonal réussi !");
            } else {
                System.out.println("Test SysDiagonal échoué.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}