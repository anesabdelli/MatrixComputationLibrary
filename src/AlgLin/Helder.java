/*
package AlgLin;

public class Helder extends SysLin {
    private boolean isFactorized = false;

    public Helder(Matrice matrice, Vecteur secondMembre) throws IrregularSysLinException {
        super(matrice, secondMembre);
    }

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
        isFactorized = true;
    }

    @Override
    public Vecteur resolution() throws IrregularSysLinException {
        if (!isFactorized) factorLDR();
        return resolutionPartielle();
    }

    public Vecteur resolutionPartielle() throws IrregularSysLinException {
        // Récupération de L, D, R depuis la matrice factorisée
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

    private Matrice extractD() {
        int n = getOrdre();
        Matrice D = new Matrice(n, n);
        for (int i = 0; i < n; i++) {
            D.remplaceCoef(i, i, matriceSystem.getCoef(i, i));
        }
        return D;
    }

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

    public void setSecondMembre(Vecteur newSecondMembre) {
        this.secondMembre = newSecondMembre;
    }

    public static void main(String[] args) {
        try {
            // Initialisation de la matrice originale
            double[][] matriceAData = {
                    {2.0, 1.0, 0.0},
                    {0.0, 3.0, 1.0},
                    {0.0, 0.0, 4.0}
            };
            Matrice originalA = new Matrice(matriceAData);

            // Créer des COPIES indépendantes pour chaque résolution
            Matrice A1 = new Matrice(originalA.nbLigne(), originalA.nbColonne());
            A1.recopie(originalA);
            Matrice A2 = new Matrice(originalA.nbLigne(), originalA.nbColonne());
            A2.recopie(originalA);

            Vecteur b = new Vecteur(new double[]{2.0, 3.0, 4.0});

            // Résoudre A y = b avec A1
            Helder system1 = new Helder(A1, b);
            Vecteur y = system1.resolution();

            // Résoudre A x = y avec A2 (non modifié)
            Helder system2 = new Helder(A2, y);
            system2.setSecondMembre(y);
            Vecteur x = system2.resolutionPartielle();

            // Calculer A² avec la matrice originale
            Matrice A2Matrice = Matrice.produit(originalA, originalA);
            Vecteur A2x = convertToVecteur(Matrice.produit(A2Matrice, x));

            // Calculer le résidu A²x - b
            Vecteur residu = new Vecteur(b.getTaille());
            for (int i = 0; i < residu.getTaille(); i++) {
                residu.remplaceCoef(i, A2x.getCoef(i) - b.getCoef(i));
            }

            // Vérifier la norme
            double norme = residu.normeLinf();
            if (norme < Matrice.EPSILON) {
                System.out.println("Solution validée !");
            } else {
                System.out.println("Erreur : Norme du résidu = " + norme);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Vecteur convertToVecteur(Matrice m) {
        Vecteur v = new Vecteur(m.nbLigne());
        for (int i = 0; i < m.nbLigne(); i++) {
            v.remplaceCoef(i, m.getCoef(i, 0));
        }
        return v;
    }
}

 */