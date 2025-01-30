package AlgLin;

import java.io.File;
import java.io.IOException;

public class Vecteur extends Matrice {

    // Constructeur qui crée un vecteur avec une seule colonne et n lignes
    public Vecteur(int nbligne) {
        super(nbligne, 1);
    }

    // Constructeur qui initialise le vecteur avec un tableau à une dimension
    public Vecteur(double[] tableau) {
        super(new double[tableau.length][1]);
        for (int i = 0; i < tableau.length; i++) {
            this.coefficient[i][0] = tableau[i];
        }
    }

    // Norme L1 : Somme des valeurs absolues
    public double normeL1() {
        double sum = 0;
        for (int i = 0; i < getTaille(); i++) {
            sum += Math.abs(getCoef(i));
        }
        return sum;
    }

    // Norme L2 : Racine de la somme des carrés
    public double normeL2() {
        double sum = 0;
        for (int i = 0; i < getTaille(); i++) {
            sum += Math.pow(getCoef(i), 2);
        }
        return Math.sqrt(sum);
    }

    // Norme Linfini : Valeur absolue maximale
    public double normeLinf() {
        double max = 0;
        for (int i = 0; i < getTaille(); i++) {
            max = Math.max(max, Math.abs(getCoef(i)));
        }
        return max;
    }

    // Constructeur qui initialise le vecteur à partir d'un fichier
    public Vecteur(String fichier) {
        super(fichier);
        if (coefficient != null) {
            if (this.nbColonne() != 1) {
                System.err.println("Erreur : Le fichier ne contient pas un vecteur valide (nombre de colonnes != 1).");
                coefficient = null;
            }
        }
    }

    // Méthode pour obtenir la taille du vecteur
    public int getTaille() {
        return this.nbLigne();
    }

    // Méthode pour obtenir un coefficient du vecteur
    public double getCoef(int index) {
        return super.getCoef(index, 0);
    }

    // Méthode pour remplacer un coefficient du vecteur
    public void remplaceCoef(int index, double value) {
        super.remplaceCoef(index, 0, value);
    }

    // Surcharge de la méthode toString pour afficher un vecteur
    @Override
    public String toString() {
        if (coefficient == null) {
            return "Vecteur invalide";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.nbLigne(); i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(this.getCoef(i));
        }
        return sb.toString();
    }


    // Méthode statique pour calculer le produit scalaire de deux vecteurs avec vérification
    public static double produitScalaire(Vecteur v1, Vecteur v2) {
        if (v1.getTaille() != v2.getTaille()) {
            throw new IllegalArgumentException("Les vecteurs n'ont pas la meme taille !");
        }
        double produit = 0.0;
        for (int i = 0; i < v1.getTaille(); i++) {
            produit += v1.getCoef(i) * v2.getCoef(i);
        }
        return produit;
    }

    // Méthode statique pour calculer le produit scalaire de deux vecteurs sans vérification
    public static double produitScalaireSansVerification(Vecteur v1, Vecteur v2) {
        double produit = 0.0;
        for (int i = 0; i < v1.getTaille(); i++) {
            produit += v1.getCoef(i) * v2.getCoef(i);
        }
        return produit;
    }

    // Méthode principale pour tester toutes les fonctionnalités
    public static void main(String[] args) {
        try {
            // Test constructors
            Vecteur v1 = new Vecteur(new double[]{1, 2, 3});
            System.out.println("Vecteur v1: " + v1);

            Vecteur v2 = new Vecteur(3);
            v2.remplaceCoef(0, 4);
            v2.remplaceCoef(1, 5);
            v2.remplaceCoef(2, 6);
            System.out.println("Vecteur v2: " + v2);

            // Test dot product
            double ps = produitScalaire(v1, v2);
            System.out.println("Produit scalaire de v1 et v2: " + ps);

            // Test with exception
            Vecteur v3 = new Vecteur(2);
            v3.remplaceCoef(0, 1);
            v3.remplaceCoef(1, 2);
            try {
                System.out.println("Produit scalaire vérifié: " + produitScalaire(v1, v3));
            } catch (Exception e) {
                System.out.println("Exception attrapée: " + e.getMessage());
            }

            // Test file constructor
            Vecteur v4 = new Vecteur("src/AlgLin/resources/vecteur1.txt");
            System.out.println("Vecteur v4: " + v4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}