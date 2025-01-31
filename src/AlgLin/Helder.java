package AlgLin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Helder extends SysLin {
    private boolean isFactorized = false; // Indique si la matrice a été factorisée

    public Helder(Matrice matrice, Vecteur secondMembre) throws IrregularSysLinException {
        super(matrice, secondMembre);
    }

    /**
     * Factorise la matrice du système en LDR.
     */
    public void factorLDR() throws IrregularSysLinException {
        int n = getOrdre();
        Matrice A = this.matriceSystem;

        for (int k = 0; k < n; k++) {
            // Calcul de D[k][k]
            double d = A.getCoef(k, k);
            for (int m = 0; m < k; m++) {
                d -= A.getCoef(k, m) * A.getCoef(m, m) * A.getCoef(m, k);
            }
            A.remplaceCoef(k, k, d);

            // Calcul des coefficients de L (partie inférieure)
            for (int i = k + 1; i < n; i++) {
                double l = A.getCoef(i, k);
                for (int m = 0; m < k; m++) {
                    l -= A.getCoef(i, m) * A.getCoef(m, m) * A.getCoef(m, k);
                }
                A.remplaceCoef(i, k, l / A.getCoef(k, k));
            }

            // Calcul des coefficients de R (partie supérieure)
            for (int j = k + 1; j < n; j++) {
                double r = A.getCoef(k, j);
                for (int m = 0; m < k; m++) {
                    r -= A.getCoef(k, m) * A.getCoef(m, m) * A.getCoef(m, j);
                }
                A.remplaceCoef(k, j, r / A.getCoef(k, k));
            }
        }
        isFactorized = true; // Marque la matrice comme factorisée
    }

    /**
     * Résout le système linéaire en utilisant la factorisation LDR.
     */
    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        if (!isFactorized) {
            factorLDR(); // Factorise la matrice si ce n'est pas déjà fait
        }
        return resolutionPartielle(); // Résout le système factorisé
    }

    /**
     * Résout le système linéaire en supposant que la matrice est déjà factorisée.
     */
    public Vecteur resolutionPartielle() throws IrregularSysLinException {
        // Extraction des matrices L, D, R
        Matrice L = extractL();
        Matrice D = extractD();
        Matrice R = extractR();

        // Résolution Ly = b
        SysTriangInfUnite sysL = new SysTriangInfUnite(L, secondMembre);
        Vecteur y = sysL.resolution();

        // Résolution Dz = y
        SysDiagonal sysD = new SysDiagonal(D, y);
        Vecteur z = sysD.resolution();

        // Résolution Rx = z
        SysTriangSupUnite sysR = new SysTriangSupUnite(R, z);
        return sysR.resolution();
    }

    /**
     * Extrait la matrice L (triangulaire inférieure à diagonale unité).
     */
    private Matrice extractL() {
        int n = getOrdre();
        Matrice L = new Matrice(n, n);
        for (int i = 0; i < n; i++) {
            L.remplaceCoef(i, i, 1.0); // Diagonale unité
            for (int j = 0; j < i; j++) {
                L.remplaceCoef(i, j, matriceSystem.getCoef(i, j));
            }
        }
        return L;
    }

    /**
     * Extrait la matrice D (diagonale).
     */
    private Matrice extractD() {
        int n = getOrdre();
        Matrice D = new Matrice(n, n);
        for (int i = 0; i < n; i++) {
            D.remplaceCoef(i, i, matriceSystem.getCoef(i, i));
        }
        return D;
    }

    /**
     * Extrait la matrice R (triangulaire supérieure à diagonale unité).
     */
    private Matrice extractR() {
        int n = getOrdre();
        Matrice R = new Matrice(n, n);
        for (int i = 0; i < n; i++) {
            R.remplaceCoef(i, i, 1.0); // Diagonale unité
            for (int j = i + 1; j < n; j++) {
                R.remplaceCoef(i, j, matriceSystem.getCoef(i, j));
            }
        }
        return R;
    }

    /**
     * Modifie le second membre du système.
     */
    public void setSecondMembre(Vecteur newSecondMembre) {
        this.secondMembre = newSecondMembre;
    }

    // Ajouter une méthode pour résoudre A² x = b directement
    public static Vecteur resolutionA2xB(Matrice A, Vecteur b) throws IrregularSysLinException {
        Matrice A2 = Matrice.produit(A, A); // Calcul de A²
        Helder system = new Helder(A2, b);
        return system.resolution();
    }

    // Mettre à jour la méthode main
    public static void main(String[] args) {
        try {
            // ================== Lecture de la matrice A et du second membre b depuis des fichiers ==================
            String fichierMatrice = "src/AlgLin/resources/matrice1.txt"; // Chemin du fichier de la matrice
            String fichierVecteur = "src/AlgLin/resources/vecteur1.txt"; // Chemin du fichier du second membre

            System.out.println("=== Lecture de la matrice A depuis le fichier ===");
            Matrice A = new Matrice(fichierMatrice);

            System.out.println("\n=== Lecture du second membre b depuis le fichier ===");
            Vecteur b = new Vecteur(fichierVecteur);

            // Afficher la matrice A
            System.out.println("\n=== Matrice A ===");
            System.out.println(A);

            // Afficher le second membre b
            System.out.println("\n=== Second membre b ===");
            System.out.println(b);

            // ================== Résolution du système A y = b ==================
            System.out.println("\n=== Résolution du système A y = b ===");
            Matrice ACopie1 = new Matrice(A.nbLigne(), A.nbColonne());
            ACopie1.recopie(A);
            Vecteur bCopie1 = new Vecteur(b.getTaille());
            bCopie1.recopie(b);

            Helder system1 = new Helder(ACopie1, bCopie1);
            Vecteur y = system1.resolution();

            // Afficher la solution y
            System.out.println("\n=== Solution y ===");
            System.out.println("Solution calculée : " + y);
            System.out.println("Solution attendue : [22/9, 3, 11/9]");

            // ================== Résolution du système A x = y ==================
            System.out.println("\n=== Résolution du système A x = y ===");
            Matrice ACopie2 = new Matrice(A.nbLigne(), A.nbColonne());
            ACopie2.recopie(A);
            Vecteur yCopie = new Vecteur(y.getTaille());
            yCopie.recopie(y);

            Helder system2 = new Helder(ACopie2, yCopie);
            Vecteur x = system2.resolution();

            // Afficher la solution x
            System.out.println("\n=== Solution x ===");
            System.out.println("Solution calculée : " + x);

            // ================== Résolution directe de A² x = b ==================
            System.out.println("\n=== Résolution directe de A² x = b ===");
            Vecteur xDirect = resolutionA2xB(A, b);
            System.out.println("Solution directe : " + xDirect);

            // ================== Vérification de la solution A² x = b ==================
            System.out.println("\n=== Vérification de la solution A² x = b ===");
            Matrice A2 = Matrice.produit(A, A); // Calcul de A²
            Vecteur A2x = new Vecteur(b.getTaille());
            for (int i = 0; i < b.getTaille(); i++) {
                double sum = 0.0;
                for (int j = 0; j < b.getTaille(); j++) {
                    sum += A2.getCoef(i, j) * x.getCoef(j);
                }
                A2x.remplaceCoef(i, sum);
            }

            // Calculer le résidu A²x - b
            Vecteur residu = new Vecteur(b.getTaille());
            for (int i = 0; i < residu.getTaille(); i++) {
                residu.remplaceCoef(i, A2x.getCoef(i) - b.getCoef(i));
            }

            // Afficher le résidu et sa norme
            System.out.println("\n=== Résidu A²x - b ===");
            System.out.println(residu);
            double norme = residu.normeLinf();
            System.out.println("\n=== Norme du résidu (L∞) ===");
            System.out.println(norme);

            // ================== Conclusion du test ==================
            System.out.println("\n=== Résultat du test ===");
            if (norme < Matrice.EPSILON) {
                System.out.println("✅ Test Helder réussi !");
            } else {
                System.out.println("❌ Test Helder échoué.");
            }

            // ================== Fin du programme ==================
            System.out.println("\n==============================================");
            System.out.println("         FIN DU TEST DE LA CLASSE HELDER");
            System.out.println("==============================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}