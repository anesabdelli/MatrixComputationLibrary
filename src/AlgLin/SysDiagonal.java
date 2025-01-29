package AlgLin;

public class SysDiagonal extends SysLin {

	public SysDiagonal(Matrice m, Vecteur v) throws IrregularSysLinException {
		super(m, v);	
	}

	@Override
	public Vecteur resolution() throws IrregularSysLinException {
		Vecteur solution = new Vecteur(getOrdre());
		
		for(int i=0;i<getOrdre();i++) {
			
		   double coeff = matriceSystem.getCoef(i, i);
		   solution.remplaceCoef(i, secondMembre.getCoef(i) / coeff);
		   
		}
		return solution;
	}

	public static void main(String[] args) {
        // Exemple de matrice et vecteur
        double mat[][] = {{2, 0, 0}, {0, 3, 0}, {0, 0, 4}};
        Matrice m = new Matrice(mat);
        Vecteur v = new Vecteur(new double[]{2.0, 3.0, 4.0});
        
        try {
            // Création de l'objet SysDiagonal et résolution
            SysDiagonal diag = new SysDiagonal(m, v);
            Vecteur solution = diag.resolution();
            
            // Affichage de la solution
            System.out.println("Solution du système :");
            for (int i = 0; i < solution.getTaille(); i++) {
                System.out.println("x" + i + " = " + solution.getCoef(i));
            }
        } catch (IrregularSysLinException e) {
            // Gestion des erreurs
            System.out.println(e);
        }
    }
	
}
