package AlgLin;

/**
 * Classe Spline pour l'interpolation par splines cubiques.
 * Elle utilise l'algorithme de Thomas pour résoudre le système tridiagonal des dérivées secondes.
 */
public class Spline {
    private double[] x; // Abscisses des points de support
    private double[] y; // Ordonnées correspondantes
    private double[] g; // Dérivées secondes calculées
    private int n;      // Nombre d'intervalles

    /**
     * Constructeur de la classe Spline.
     * @param x tableau des abscisses (doit être trié par ordre croissant)
     * @param y tableau des ordonnées correspondant aux abscisses
     * @throws IllegalArgumentException si les tableaux sont invalides.
     */
    public Spline(double[] x, double[] y) {
        if (x == null || y == null || x.length != y.length || x.length < 2) {
            throw new IllegalArgumentException("Les tableaux doivent être non nuls, de même taille et contenir au moins deux points.");
        }
        this.n = x.length - 1;
        this.x = x;
        this.y = y;
        this.g = new double[x.length];
        computeSecondDerivatives();
    }

    /**
     * Calcule les dérivées secondes en résolvant le système tridiagonal avec l'algorithme de Thomas.
     */
    private void computeSecondDerivatives() {
        int nPoints = x.length;
        double[] h = new double[n]; // h[i] = x[i+1] - x[i]
        for (int i = 0; i < n; i++) {
            h[i] = x[i + 1] - x[i];
        }

        // Construction des coefficients du système tridiagonal
        double[] a = new double[n - 1]; // Sous-diagonale
        double[] b = new double[n - 1]; // Diagonale principale
        double[] c = new double[n - 1]; // Sur-diagonale
        double[] d = new double[n - 1]; // Second membre

        for (int i = 1; i < n; i++) {
            a[i - 1] = h[i - 1]; // sous-diagonale
            b[i - 1] = 2 * (h[i - 1] + h[i]); // diagonale principale
            c[i - 1] = h[i]; // sur-diagonale
            d[i - 1] = 6 * ((y[i + 1] - y[i]) / h[i] - (y[i] - y[i - 1]) / h[i - 1]);
        }

        try {
            // Création de la matrice tridiagonale
            Mat3Diag matriceTridiagonale = new Mat3Diag(new double[][]{a, b, c});
            Vecteur vecteurSecondMembre = new Vecteur(d);

            // Utilisation de l'algorithme de Thomas
            Thomas solveur = new Thomas(matriceTridiagonale, vecteurSecondMembre);
            Vecteur solution = solveur.resolution();

            // Assignation des dérivées secondes, avec g[0] et g[n] = 0 (conditions naturelles)
            g[0] = 0;
            for (int i = 1; i < n; i++) {
                g[i] = solution.getCoef(i - 1);
            }
            g[n] = 0;
        } catch (IrregularSysLinException | IllegalOperationException e) {
            System.err.println("Erreur dans la résolution du système tridiagonal : " + e.getMessage());
        }
    }

    /**
     * Évalue la fonction d'interpolation par spline cubique en un point donné.
     * @param xi la valeur en abscisse où évaluer la spline.
     * @return la valeur interpolée f(xi)
     * @throws DataOutOfRangeException si xi est hors de l'intervalle défini.
     */
    public double eval(double xi) throws DataOutOfRangeException {
        if (xi < x[0] || xi > x[n]) {
            throw new DataOutOfRangeException("Valeur hors de l'intervalle [" + x[0] + ", " + x[n] + "]");
        }

        // Recherche de l'intervalle [x[i], x[i+1]] contenant xi
        int i = 0;
        while (i < n && xi > x[i + 1]) {
            i++;
        }

        double hi = x[i + 1] - x[i];
        double A = (x[i + 1] - xi) / hi;
        double B = (xi - x[i]) / hi;

        // Formule d'interpolation par spline cubique
        return A * y[i] + B * y[i + 1]
                + ((A * A * A - A) * g[i] + (B * B * B - B) * g[i + 1]) * (hi * hi) / 6.0;
    }
}
