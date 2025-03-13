package AlgLin;

import java.io.*;
import java.util.*;

/**
 * Classe ModPoly décrivant un modèle linéaire spécifique pour l'ajustement
 * d'un polynôme aux données par la méthode des moindres carrés.
 *
 * Le modèle est défini par :
 *    f(x) = a0 + a1*x + a2*x^2 + ... + ad*x^d,
 * où d est le degré du polynôme.
 *
 * Les fonctions de base sont données par phi_j(x) = x^j.
 */
public class ModPoly {
    private int degree;            // Degré du polynôme
    private double[] coefficients; // Coefficients du polynôme (a0, a1, ..., ad)

    /**
     * Constructeur qui initialise le modèle avec le degré souhaité.
     * @param degree le degré du polynôme à ajuster.
     */
    public ModPoly(int degree) {
        this.degree = degree;
        this.coefficients = new double[degree + 1];
    }

    /**
     * Méthode identifie qui calcule les coefficients du polynôme
     * par la méthode des moindres carrés à partir d'un ensemble de points de support.
     *
     * Pour un ensemble de n points (x_i, y_i) et un polynôme de degré d (m = d+1 coefficients),
     * les équations normales sont :
     *    sum_{i=0}^{n-1} x_i^(j+k) * a_k = sum_{i=0}^{n-1} y_i * x_i^j,  pour j = 0,..., d.
     *
     * @param x tableau des abscisses des points de support.
     * @param y tableau des ordonnées des points de support.
     */
    public void identifie(double[] x, double[] y) {
        int n = x.length;
        int m = degree + 1; // Nombre de coefficients
        // Matrice des équations normales de taille m x m
        double[][] M = new double[m][m];
        // Vecteur second membre de taille m
        double[] B = new double[m];

        // Calcul des sommes de puissances : S[p] = sum_i x_i^p, pour p=0,...,2*m-2.
        double[] S = new double[2 * m - 1];
        Arrays.fill(S, 0.0);
        for (int i = 0; i < n; i++) {
            double power = 1.0;
            for (int p = 0; p < 2 * m - 1; p++) {
                S[p] += power;
                power *= x[i];
            }
        }

        // Remplissage de la matrice M et du vecteur B
        for (int j = 0; j < m; j++) {
            for (int k = 0; k < m; k++) {
                M[j][k] = S[j + k];
            }
            double sum = 0.0;
            for (int i = 0; i < n; i++) {
                double power = 1.0;
                for (int p = 0; p < j; p++) {
                    power *= x[i];
                }
                sum += y[i] * power;
            }
            B[j] = sum;
        }

        // Résolution du système M * a = B par élimination de Gauss
        coefficients = gaussianElimination(M, B);
    }

    /**
     * Méthode de résolution d'un système linéaire par élimination de Gauss.
     * @param A Matrice des coefficients (modifiée in situ).
     * @param B Vecteur second membre (modifié in situ).
     * @return Le vecteur solution.
     */
    private double[] gaussianElimination(double[][] A, double[] B) {
        int m = B.length;
        // Élimination de Gauss avec pivot partiel
        for (int i = 0; i < m; i++) {
            // Recherche du pivot maximal
            int maxRow = i;
            for (int k = i + 1; k < m; k++) {
                if (Math.abs(A[k][i]) > Math.abs(A[maxRow][i])) {
                    maxRow = k;
                }
            }
            // Échange des lignes i et maxRow dans A et B
            double[] temp = A[i];
            A[i] = A[maxRow];
            A[maxRow] = temp;
            double tempVal = B[i];
            B[i] = B[maxRow];
            B[maxRow] = tempVal;

            // Vérification du pivot
            if (Math.abs(A[i][i]) < 1e-12) {
                throw new RuntimeException("Pivot nul détecté. Le système est singulier.");
            }

            // Élimination
            for (int k = i + 1; k < m; k++) {
                double factor = A[k][i] / A[i][i];
                for (int j = i; j < m; j++) {
                    A[k][j] -= factor * A[i][j];
                }
                B[k] -= factor * B[i];
            }
        }
        // Substitution arrière
        double[] xSol = new double[m];
        for (int i = m - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < m; j++) {
                sum += A[i][j] * xSol[j];
            }
            xSol[i] = (B[i] - sum) / A[i][i];
        }
        return xSol;
    }

    /**
     * Évalue le polynôme ajusté en un point x.
     * @param xVal la valeur en abscisse.
     * @return f(xVal) = sum_{j=0}^{degree} a_j * xVal^j.
     */
    public double evaluate(double xVal) {
        double result = 0.0;
        double power = 1.0;
        for (int j = 0; j <= degree; j++) {
            result += coefficients[j] * power;
            power *= xVal;
        }
        return result;
    }

    /**
     * Redéfinit toString pour afficher le modèle sous forme lisible.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f(x) = ");
        for (int j = 0; j <= degree; j++) {
            if (j > 0) {
                sb.append(" + ");
            }
            sb.append(String.format("(%.4f)*x^%d", coefficients[j], j));
        }
        return sb.toString();
    }

    /**
     * Méthode main pour tester l'ajustement par moindres carrés et générer l'affichage avec Gnuplot.
     * Le programme :
     * - Lit un fichier de points de support dont le nom est saisi par l'utilisateur.
     * - Demande le degré du polynôme à ajuster.
     * - Calcule les coefficients par la méthode des moindres carrés.
     * - Génère deux fichiers de données : un pour la courbe ajustée et un pour les points de support.
     * - Lance Gnuplot pour tracer le polynôme ajusté et les points.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le nom du fichier de données (ex: data.txt) : ");
        String filename = sc.nextLine();
        String filepath = "src/AlgLin/Resources/" + filename;

        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();

        // Lecture du fichier de données
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("\\s+");
                if (parts.length < 2) continue;
                double xVal = Double.parseDouble(parts[0]);
                double yVal = Double.parseDouble(parts[1]);
                xList.add(xVal);
                yList.add(yVal);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            return;
        }

        if (xList.size() < 2) {
            System.err.println("Pas assez de points de support.");
            return;
        }

        int n = xList.size();
        double[] xData = new double[n];
        double[] yData = new double[n];
        for (int i = 0; i < n; i++) {
            xData[i] = xList.get(i);
            yData[i] = yList.get(i);
        }

        System.out.print("Entrez le degré du polynôme à ajuster (ex : 3 pour un polynôme de degré 3) : ");
        int degree = sc.nextInt();

        // Création du modèle et identification des paramètres
        ModPoly model = new ModPoly(degree);
        model.identifie(xData, yData);
        System.out.println("Modèle ajusté : " + model);

        // Détermination de l'intervalle d'analyse
        double xmin = xData[0], xmax = xData[0];
        for (double v : xData) {
            if (v < xmin) xmin = v;
            if (v > xmax) xmax = v;
        }

        // Génération de 100 points uniformément répartis dans [xmin, xmax]
        int numPoints = 100;
        double step = (xmax - xmin) / (numPoints - 1);

        // Création des fichiers pour Gnuplot
        String polyCurveFile = "poly_curve.dat";
        String polyPointsFile = "poly_points.dat";

        try (PrintWriter pwCurve = new PrintWriter(new FileWriter(polyCurveFile));
             PrintWriter pwPoints = new PrintWriter(new FileWriter(polyPointsFile))) {

            // Écriture des points de la courbe ajustée
            for (int i = 0; i < numPoints; i++) {
                double xVal = xmin + i * step;
                double yVal = model.evaluate(xVal);
                pwCurve.println(xVal + "\t" + yVal);
            }
            // Écriture des points de support
            for (int i = 0; i < n; i++) {
                pwPoints.println(xData[i] + "\t" + yData[i]);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture des fichiers : " + e.getMessage());
            return;
        }

        System.out.println("Fichiers générés pour Gnuplot : ");
        System.out.println(" - " + polyCurveFile);
        System.out.println(" - " + polyPointsFile);

        // Préparation et exécution de Gnuplot
        try {
            String gnuplotScript =
                    "set title 'Ajustement par Moindres Carres - Polynome de degre " + degree + "'\n" +
                            "set xlabel 'x'\n" +
                            "set ylabel 'y'\n" +
                            "plot '" + polyCurveFile + "' with lines title 'Polynome ajuste', \\\n" +
                            "     '" + polyPointsFile + "' with points pointtype 7 pointsize 1 title 'Points de support'\n" +
                            "pause -1 'Appuyez sur une touche pour fermer'\n";
            String scriptFile = "plotPoly.gp";
            try (PrintWriter pwScript = new PrintWriter(new FileWriter(scriptFile))) {
                pwScript.print(gnuplotScript);
            }
            Process p = Runtime.getRuntime().exec("gnuplot " + scriptFile);
            p.waitFor();
        } catch(Exception e) {
            System.err.println("Erreur lors de l'exécution de Gnuplot : " + e.getMessage());
        }
    }
}
