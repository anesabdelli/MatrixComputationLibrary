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

    // Méthode principale pour tester toutes les fonctionnalités
    public static void main(String[] args) {}

}
