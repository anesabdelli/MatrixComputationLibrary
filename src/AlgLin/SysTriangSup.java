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
        // Example of an upper triangular matrix and a vector
        double mat[][] = {
            {2, 3, 1},
            {0, 3, 2},
            {0, 0, 4}
        };
        Matrice m = new Matrice(mat);
        Vecteur v = new Vecteur(new double[]{5.0, 6.0, 7.0});

        try {
            // Create an instance of SysTriangSup and solve the system
            SysTriangSup sys = new SysTriangSup(m, v);
            Vecteur solution = sys.resolution();

            // Display the solution
            System.out.println("Solution du système triangulaire supérieur :");
            for (int i = 0; i < solution.getTaille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            // Handle exceptions
            System.out.println(e);
        }
    }
}
