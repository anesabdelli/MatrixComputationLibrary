package AlgLin;

public class SysTriangSupUnite extends SysTriangSup {

    public SysTriangSupUnite(Matrice m, Vecteur v) throws IrregularSysLinException {
        super(m, v);
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        Vecteur solution = new Vecteur(getOrdre());

        for (int i = getOrdre() - 1; i >= 0; i--) {
            double sum = getSecondMembre().getCoef(i);

            // Perform backward substitution
            for (int j = i + 1; j < getOrdre(); j++) {
                sum -= getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
            }

            // Since diagonal coefficients are all 1, no division is necessary
            solution.remplaceCoef(i, sum);
        }

        return solution;
    }

    public static void main(String[] args) {
        double mat[][] = {
            {1, 2, 3},
            {0, 1, 4},
            {0, 0, 1}
        };
        Vecteur v = new Vecteur(new double[]{6.0, 5.0, 4.0});
        Matrice m = new Matrice(mat);

        try {
            SysTriangSupUnite sys = new SysTriangSupUnite(m, v);
            Vecteur solution = sys.resolution();
            System.out.println("Solution for upper triangular unit system:");
            for (int i = 0; i < solution.getTaille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            System.out.println(e);
        }
    }
}
