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
        double mat[][] = {
            {1, 0, 0},
            {2, 1, 0},
            {3, 4, 1}
        };
        Vecteur v = new Vecteur(new double[]{5.0, 6.0, 7.0});
        Matrice m = new Matrice(mat);

        try {
            SysTriangInfUnite sys = new SysTriangInfUnite(m, v);
            Vecteur solution = sys.resolution();
            System.out.println("Solution for lower triangular unit system:");
            for (int i = 0; i < solution.getTaille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            System.out.println(e);
        }
    }
}
