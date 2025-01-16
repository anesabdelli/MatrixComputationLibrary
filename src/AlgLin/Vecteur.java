package AlgLin;

import java.io.File;

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
    public Vecteur(String fichier) {
        super(fichier);
        if (this.coefficient[0].length != 1) {
            throw new IllegalArgumentException("Le fichier ne définit pas un vecteur (une seule colonne attendue).");
        }
    }
    
    // Méthode pour obtenir la taille du vecteur
    public int getTaille(){
        return this.nbLigne();
    }

    // Méthode pour obtenir un coefficient du vecteur
    public double getCoef(int index){
        return super.getCoef(index, 0);
    }
    
    // Méthode pour remplacer un coefficient du vecteur
    public void remplaceCoef(int index, double value){
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

    // Méthode statique pour calculer le produit scalaire de deux vecteurs
    public static double produitScalaire(Vecteur v1, Vecteur v2) {
        if (v1.getTaille() != v2.getTaille()) {
            throw new IllegalArgumentException("Les vecteurs n'ont pas la même taille !");
        }
        double produit = 0.0;
        for (int i = 0; i < v1.getTaille(); i++) {
            produit += v1.getCoef(i) * v2.getCoef(i);
        }
        return produit;
    }

    // Méthode principale pour tester toutes les fonctionnalités
    public static void main(String[] args) {
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

        // Test du constructeur avec fichier (chemin à adapter selon votre système)
        File fichier = new File("Vecteur.txt");
        if (fichier.exists()) {
            Vecteur v3 = new Vecteur("Vecteur.txt");
            System.out.println("Test du constructeur avec fichier :");
            System.out.println(v3);
        } else {
            System.out.println("Le fichier 'vecteur.txt' n'existe pas.");
        }

        // Test de toString
        System.out.println("Test de toString :");
        System.out.println("Vecteur 1 :");
        System.out.println(v1);

        // Test d'erreur (vecteurs de tailles différentes)
        Vecteur v4 = new Vecteur(new double[]{1.0, 2.0});
        System.out.println("Produit scalaire entre v1 et v4 (devrait générer une exception) :");
        System.out.println(produitScalaire(v1, v4));
    }

}
