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
		try {
			// Matrice triangulaire inférieure et second membre
			double[][] matData = {
					{2, 0, 0},
					{1, 3, 0},
					{4, 5, 6}};
			Matrice mat = new Matrice(matData);
			Vecteur b = new Vecteur(new double[]{2.0, 3.0, 7.0});

			// Afficher la matrice et le second membre
			System.out.println("Matrice du système :");
			System.out.println(mat);
			System.out.println("Second membre b :");
			System.out.println(b);

			// Créer une copie de la matrice et du second membre
			Matrice matCopie = new Matrice(mat.nbLigne(), mat.nbColonne());
			matCopie.recopie(mat);
			Vecteur bCopie = new Vecteur(b.getTaille());
			bCopie.recopie(b);

			// Résoudre le système
			SysTriangInf sys = new SysTriangInf(matCopie, bCopie);
			Vecteur x = sys.resolution();

			// Afficher la solution
			System.out.println("Solution x :");
			System.out.println(x);

			// Calculer Ax - b
			Vecteur Ax = new Vecteur(b.getTaille());
			for (int i = 0; i < b.getTaille(); i++) {
				double sum = 0.0;
				for (int j = 0; j < b.getTaille(); j++) {
					sum += mat.getCoef(i, j) * x.getCoef(j);
				}
				Ax.remplaceCoef(i, sum);
			}

			// Calculer le résidu Ax - b
			Vecteur residu = new Vecteur(b.getTaille());
			for (int i = 0; i < residu.getTaille(); i++) {
				residu.remplaceCoef(i, Ax.getCoef(i) - b.getCoef(i));
			}

			// Afficher le résidu et sa norme
			System.out.println("Résidu Ax - b :");
			System.out.println(residu);
			double norme = residu.normeLinf();
			System.out.println("Norme du résidu (L∞) : " + norme);

			// Vérifier la norme du résidu
			if (norme < Matrice.EPSILON) {
				System.out.println("Test SysTriangInf réussi !");
			} else {
				System.out.println("Test SysTriangInf échoué.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}