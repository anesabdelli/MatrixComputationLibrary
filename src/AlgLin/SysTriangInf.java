package AlgLin;

public class SysTriangInf extends SysLin {

	public SysTriangInf(Matrice m, Vecteur v) throws IrregularSysLinException {
		super(m, v);
	}

	@Override
	public Vecteur resolution() throws IrregularSysLinException {
		Vecteur solution = new Vecteur(getOrdre());
		
		// Itération sur chaque ligne pour résoudre chaque équation
	    for (int i = 0; i < getOrdre(); i++) {
	        
	        double coeff = getMatriceSystem().getCoef(i, i); // Récupérer le coefficient diagonal
	        if (coeff == 0) { // Si le coefficient diagonal est nul, lever une exception
	            throw new IrregularSysLinException("Coefficient nul trouvé sur la diagonale.");
	        }

	        // Calcul du second membre pour la variable Xi
	        double sum = getSecondMembre().getCoef(i);

	        // Effectuer la substitution avant avec les variables déjà résolues
	        for (int j = 0; j < i; j++) {
	            sum -= getMatriceSystem().getCoef(i, j) * solution.getCoef(j);
	        }

	        // Calcul de la solution pour x_i
	        solution.remplaceCoef(i, sum / coeff);
	    }
		
		return solution;
	}
	
	// Méthode principale pour tester la classe
    public static void main(String[] args) {
        // Exemple de matrice triangulaire inférieure et vecteur
        double mat[][] = {{2, 0, 0}, {1, 3, 0}, {4, 5, 6}};
        Matrice m = new Matrice(mat);
        Vecteur v = new Vecteur(new double[]{2.0, 3.0, 7.0});
        
        try {
            // Création de l'objet SysTriangInf et résolution
            SysTriangInf triangInf = new SysTriangInf(m, v);
            Vecteur solution = triangInf.resolution();
            
            // Affichage de la solution
            System.out.println("Solution du système triangulaire inférieur :");
            for (int i = 0; i < solution.getTaille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            // Gestion des erreurs
            System.out.println(e);
        }
    }

}
