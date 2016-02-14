package model;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Observable;

public class Dessin extends Observable implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Dessinable> dessin = new LinkedList<Dessinable>();// Liste des Objets à déssiner


	public void ajouterDessin(Dessinable d){
		dessin.add(d);
		notifyDessinChanged();
	}

	public void supprimerDessin(int index){
		dessin.remove(index);
		notifyDessinChanged();
	}

	public void ordreDessin(int index ,int numOrdre){
		Dessinable remplaceur = dessin.get(index);
		dessin.remove(index); // efface le dessinable de sa position ancienne
		dessin.add(numOrdre,remplaceur); // ajoute le dessinable dans la position numOrdre
		notifyDessinChanged();
	}

	public void modifierDessin(int index, int x, int y, int dx, int dy, Color c, String tit, String p, int t, float epLine){
		dessin.get(index).setColor(c); // Modification de la couleur
		if(dessin.get(index) instanceof LigneBrisee){
					((LigneBrisee)dessin.get(index)).deplacer(x,y); // Déplace la position d'une ligneBrisee
					((LigneBrisee)dessin.get(index)).setEpaisseurLine(epLine);
		}else{
			if (dessin.get(index) instanceof Texte)
				((Texte)dessin.get(index)).modifieTexte(tit,p,t);
			else
				((FormeAvecDimension) dessin.get(index)).setDimension(dx,dy); // Modification de la dimension
			dessin.get(index).deplacer(x,y); // // Déplace la position d'une ligneBrisee
		}
		notifyDessinChanged();
	}
	
	public LinkedList<Dessinable> getDessin(){
		return dessin;
	}

	public void notifyDessinChanged(){
		setChanged(); 
		notifyObservers(new Integer(dessin.size()));
	}

	public void updateDessin(LinkedList<Dessinable> d){
		this.dessin = d;
		notifyDessinChanged();
	}

	public Dessinable getDessinable(int index){
		return dessin.get(index);
	}
}
