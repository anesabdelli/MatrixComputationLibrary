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

    // Constructeur qui initialise le vecteur à partir d'un fichier
    public Vecteur(String fichier) throws IOException {
        super(fichier);
        if (this.coefficient[0].length != 1) {
            throw new IllegalArgumentException("Le fichier ne définit pas un vecteur (une seule colonne attendue).");
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.getTaille(); i++) {
            sb.append(this.getCoef(i)).append("\n");
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
    public static void main(String[] args) throws IOException {
        // Test du constructeur avec tableau
        Vecteur v1 = new Vecteur(new double[]{1.0, 2.0, 3.0});
        System.out.println("Test du constructeur avec tableau :");
        System.out.println(v1);

        // Test du constructeur avec taille
        Vecteur v2 = new Vecteur(3);
        v2.remplaceCoef(0, 4.0);
        v2.remplaceCoef(1, 5.0);
        v2.remplaceCoef(2, 6.0);
        System.out.println("Test du constructeur avec taille :");
        System.out.println(v2);

        // Test du produit scalaire
        double produit = produitScalaire(v1, v2);
        System.out.println("Produit scalaire entre v1 et v2 : " + produit);

        // Test du constructeur avec fichier 
        File fichier = new File("src/AlgLin/resources/vecteur1.txt");
        if (fichier.exists()) {
            Vecteur v3 = new Vecteur("src/AlgLin/resources/vecteur1.txt");
            System.out.println("Test du constructeur avec fichier :");
            System.out.println(v3);
        } else {
            System.out.println("Le fichier 'vecteur1.txt' n'existe pas.");
        }

        // Test de toString
        System.out.println("Test de toString :");
        System.out.println("Vecteur 1 :");
        System.out.println(v1.toString());

        // Test d'erreur (vecteurs de tailles différentes)
        Vecteur v4 = new Vecteur(new double[]{1.0, 2.0});
        System.out.println("Produit scalaire entre v1 et v4 (devrait generer une exception) :");
        try {
            double produitErreur = produitScalaire(v1, v4);
            System.out.println("Produit scalaire entre v1 et v4 : " + produitErreur);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

}
