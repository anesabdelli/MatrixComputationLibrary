package AlgLin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Programme principal pour tester l'interpolation par splines cubiques.
 * <p>
 * Le programme lit un fichier de points de support dont le nom est saisi par l'utilisateur
 * (par exemple "data.txt") et se trouvant dans le dossier "AlgLin/Resources". Il construit
 * l'objet Spline, évalue la fonction interpolée sur 100 points répartis uniformément sur
 * l'intervalle défini par les abscisses des points de support, puis génère deux fichiers de données
 * pour Gnuplot :
 * <ul>
 *   <li><code>spline_curve.dat</code> : courbe interpolée</li>
 *   <li><code>spline_points.dat</code> : points de support</li>
 * </ul>
 * Enfin, le programme lance Gnuplot pour afficher les résultats.
 * </p>
 */
public class MainSpline {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez le nom du fichier de données (ex: data.txt) :");
        String filename = sc.nextLine();

        // Le fichier est situé dans "AlgLin/Resources"
        String filepath = "src/AlgLin/resources/" + filename;

        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();

        // Lecture du fichier de points
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Ignorer les lignes vides ou commençant par '#' (commentaires)
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    continue;
                }
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
            System.err.println("Pas assez de points dans le fichier.");
            return;
        }

        // Conversion des listes en tableaux
        int n = xList.size();
        double[] x = new double[n];
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = xList.get(i);
            y[i] = yList.get(i);
        }

        // Création de l'objet Spline
        Spline spline = new Spline(x, y);

        // Détermination de l'intervalle d'interpolation (on suppose que les points sont triés)
        double xmin = x[0];
        double xmax = x[n - 1];

        // Génération de 100 points uniformément répartis dans [xmin, xmax]
        int numPoints = 100;
        double step = (xmax - xmin) / (numPoints - 1);

        // Fichiers de sortie pour Gnuplot
        String splineCurveFile = "spline_curve.dat";
        String splinePointsFile = "spline_points.dat";

        try (PrintWriter pwCurve = new PrintWriter(new FileWriter(splineCurveFile));
             PrintWriter pwPoints = new PrintWriter(new FileWriter(splinePointsFile))) {

            // Écriture des points de la courbe interpolée
            for (int i = 0; i < numPoints; i++) {
                double xi = xmin + i * step;
                double yi = spline.eval(xi);
                pwCurve.println(xi + "\t" + yi);
            }
            // Écriture des points de support
            for (int i = 0; i < n; i++) {
                pwPoints.println(x[i] + "\t" + y[i]);
            }
        } catch (IOException | DataOutOfRangeException e) {
            System.err.println("Erreur lors de l'écriture des fichiers : " + e.getMessage());
            return;
        }

        System.out.println("Les fichiers de données pour Gnuplot ont été générés :");
        System.out.println(" - " + splineCurveFile);
        System.out.println(" - " + splinePointsFile);

        // Préparation et exécution de Gnuplot pour l'affichage
        try {
            String gnuplotScript =
                    "set title 'Interpolation par Splines Cubiques'\n" +
                            "set xlabel 'x'\n" +
                            "set ylabel 'y'\n" +
                            "plot '" + splineCurveFile + "' with lines title 'Spline', \\\n" +
                            "     '" + splinePointsFile + "' with points pointtype 7 pointsize 1 title 'Points de support'\n" +
                            "pause -1 'Appuyez sur une touche pour fermer'\n";

            // Écrire le script dans un fichier temporaire
            String scriptFile = "plotSpline.gp";
            try (PrintWriter pwScript = new PrintWriter(new FileWriter(scriptFile))) {
                pwScript.print(gnuplotScript);
            }

            // Exécuter Gnuplot (assurez-vous que Gnuplot est installé et accessible via la commande "gnuplot")
            Process p = Runtime.getRuntime().exec("gnuplot " + scriptFile);
            p.waitFor();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution de Gnuplot : " + e.getMessage());
        }
    }
}
