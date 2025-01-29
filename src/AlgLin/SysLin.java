package AlgLin;

public abstract class SysLin {
	private int ordre;
	protected Matrice matriceSystem;
	protected Vecteur secondMembre;
	
    public SysLin(Matrice m,Vecteur v) throws IrregularSysLinException {
    	if(m.nbLigne()!= m.nbColonne() ) {
    		throw new IrregularSysLinException("La matrice doit être carrée.");
    	}
    	
    	if(v.getTaille()!=m.nbLigne()) {
    		throw new IrregularSysLinException("La taille du second membre doit correspondre à la taille de la matrice.");
    	}
    	
    	this.ordre= m.nbLigne();
    	this.matriceSystem=m;
    	this.secondMembre=v;
    	
    }
    
    public int getOrdre() {
    	return ordre;
    }
    
    public Matrice getMatriceSystem() {
    	return matriceSystem;
    }
    
    public Vecteur getSecondMembre() {
    	return secondMembre;
    }
    
    // Méthode abstraite pour résoudre le système
    public abstract Vecteur resolution() throws IrregularSysLinException;
}
