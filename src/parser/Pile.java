package parser;
public class Pile <T> {
	
	private Maillon<T> top;
	
	public Pile(Maillon<T> top) {
		this.top = top;
	}
	
	public Pile() {
		this.top = null;
	}

	public Pile(Pile<T> p) {
		Maillon<T> m = p.top;
		while(m!=null) {
			this.empiler(m.renvoyerValeur());
			m = m.renvoyerSuivant();
		}
	}
	
	public void empiler(T data) {
		if(top==null)
			top = new Maillon<T>(data);
		else
			top = new Maillon<T>(data,top);
	}
	
	public T depiler() throws PileVideException{
		if(top==null)
			throw new PileVideException();
		T tmp = top.renvoyerValeur();
		top = top.renvoyerSuivant();
		return tmp;
	}
	
	@Override
	public String toString() {
		return "Pile [ " + top + "]";
	}
	

	public T renvoyerSommet() throws PileVideException{
		if(top==null)
			throw new PileVideException(); 
		return top.renvoyerValeur();
	}
	
	public boolean estVide() {
		return renvoyerHauteur() <= 0;
	}
	
	public void afficher() {
		System.out.println(this);
	}
	
	public int renvoyerHauteur() {
		return top==null?0:1+new Pile<T>(top.renvoyerSuivant()).renvoyerHauteur();
	}
	
	public void vider() {
		while(true)
			try {
				depiler();
			} catch (PileVideException e) {
				break;
			}
	}
	
	
	
	
	public static void main(String[] args) throws PileVideException {
		Pile<String> p = new Pile<String>();
		System.out.println(p);
		p.empiler("5");
		p.empiler("78");
		Pile<String> p2 = new Pile<String>(p);
		System.out.println(p2.renvoyerHauteur());
		System.out.println(p2);
		System.out.println(p);
		p.depiler();
		System.out.println(p);
		p.depiler();
		System.out.println(p);
		p2.afficher();
		p2.vider();
		p2.afficher();
		//p.depiler();
		
	}

	
	
}
