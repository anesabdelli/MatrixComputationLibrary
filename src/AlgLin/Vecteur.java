package AlgLin;

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
        System.out.println("*** Début des tests de la classe Vecteur ***\n");

        // ================== Test des constructeurs ==================
        System.out.println("===== Test des constructeurs =====");
        // Constructeur avec tableau
        Vecteur v1 = new Vecteur(new double[]{1, -2, 3});
        System.out.println("[OK] Constructeur avec tableau : " + v1);

        // Constructeur avec taille
        Vecteur v2 = new Vecteur(3);
        v2.remplaceCoef(0, 4);
        v2.remplaceCoef(1, 5);
        v2.remplaceCoef(2, 6);
        System.out.println("[OK] Constructeur avec taille : " + v2);

        // Constructeur avec fichier valide
        try {
            Vecteur vFichier = new Vecteur("src/AlgLin/resources/vecteur1.txt");
            System.out.println("[OK] Constructeur fichier valide : " + vFichier);
        } catch (Exception e) {
            System.out.println("[ERREUR] Constructeur fichier valide : " + e.getMessage());
        }

        // Constructeur avec fichier invalide
        Vecteur vInvalide = new Vecteur("fichier_inexistant.txt");
        System.out.println("[TEST] Constructeur fichier invalide : " + vInvalide + "\n");

        // ================== Test des accesseurs ==================
        System.out.println("===== Test des accesseurs =====");
        System.out.println("Taille de v1 : " + v1.getTaille() + " (attendu : 3)");
        System.out.println("Coeff [0] de v1 : " + v1.getCoef(0) + " (attendu : 1.0)");
        v1.remplaceCoef(1, 10);
        System.out.println("Après remplacement : " + v1 + " (attendu : 1.0 10.0 3.0)\n");


        // ================== Test des normes ==================
        System.out.println("===== Test des normes =====");
        System.out.println("Norme L1 de v1 : " + v1.normeL1() + " (attendu : 14.0)");
        System.out.println("Norme L2 de v1 : " + v1.normeL2() + " (attendu : ~10.488)");
        System.out.println("Norme Linf de v1 : " + v1.normeLinf() + " (attendu : 10.0)\n");


        // ================== Test des produits scalaires ==================
        System.out.println("===== Test des produits scalaires =====");
        // Cas standard
        try {
            System.out.println("Produit scalaire validé : " + produitScalaire(v1, v2) + " (attendu : 52.0)");
        } catch (Exception e) {
            System.out.println("[ERREUR] " + e.getMessage());
        }

        // Cas avec vérification d'erreur
        try {
            Vecteur v3 = new Vecteur(2);
            System.out.println("Produit scalaire incompatible : " + produitScalaire(v1, v3));
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Erreur détectée : " + e.getMessage());
        }

        // Cas sans vérification (comportement risqué)
        try {
            Vecteur v4 = new Vecteur(new double[]{1, 2});
            System.out.println("Produit non vérifié : " + produitScalaireSansVerification(v1, v4));
        } catch (Exception e) {
            System.out.println("[ERREUR] " + e.getMessage());
        }

        // ================== Test cas particuliers ==================
        System.out.println("\n===== Test cas particuliers =====");
        // Vecteur vide
        try {
            Vecteur vVide = new Vecteur(0);
            System.out.println("Test vecteur vide : " + vVide);
        } catch (Exception e) {
            System.out.println("[OK] Vecteur vide impossible : " + e.getMessage());
        }

        // Vecteur avec valeurs extrêmes
        Vecteur vExtreme = new Vecteur(new double[]{Double.MAX_VALUE, -Double.MAX_VALUE});
        System.out.println("Norme Linf extreme : " + vExtreme.normeLinf());

        System.out.println("\n*** Fin des tests ***");
    }
}