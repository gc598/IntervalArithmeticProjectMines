package parser;
import java.io.Serializable;

// ----------------------------------------------
//    Algorithmique et programmation en Java
// Ecole Nationale Supérieure des Mines de PARIS
//      Cours d'informatique -  1ère année
// ----------------------------------------------
//     Exercice sur les structures récursives
// ----------------------------------------------

public class Maillon <T> implements Serializable{
  

  private static final long serialVersionUID = 5502228173178423057L;
// ATTRIBUTS ////////////////////////////////////////////////

  private T valeur;
  private Maillon<T> suivant;

  // CONSTRUCTEURS ////////////////////////////////////////////

  // Constructeur permettant de créer un Maillon isolé
  public Maillon(T x) {
    valeur = x;
    // Quand on le crée, le Maillon est isolé, donc il n'a pas de "suivant".
    suivant = null; 
  }
  
  public Maillon(T valeur, Maillon<T> suivant) {
	this.valeur = valeur;
	this.suivant = suivant;
  }
  
  // METHODES /////////////////////////////////////////////////
  
  public void selfReference(){
    this.suivant = this;
  }
  

// Retourne la valeur contenue dans le maillon.  
  // REMARQUE : c'est la seule méthode, avec le constructeur, qui dépend  
  //            du type des éléments stockés contenu des maillons.
  public T renvoyerValeur() {
    return valeur;
  }
 
   
  // Retourne une référence vers le Maillon suivant de la chaîne.
  public Maillon<T> renvoyerSuivant() {
    return suivant;
  }
  
  // "Chaîne" le Maillon en stockant la référence vers le Maillon suivant
  public void lierAvecSuivant(Maillon<T> leSuivant) {
    suivant = leSuivant;
  }

@Override
public String toString() {
	Maillon<T> m = this;
	String res = "";
	while(m!=null) {
		res+=m.valeur+"  ";
		m = m.suivant;
	}
	return res;
}
  
}
