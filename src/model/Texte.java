package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

public class Texte extends Forme implements Dessinable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titre;
	private String police;
	private int taille;

	public Texte(int x, int y, Color c, String t, String p, int tail){
		super(x, y, c);
		this.titre = t;
		this.police = p;
		this.taille = tail;
	}

	public void modifieTexte(String tit, String p, int t){
		this.titre = tit;
		this.police = p;
		this.taille = t;
		}

	public String getTitre(){return this.titre;}
	public String getPolice(){return this.police;}
	public int getTaille(){return this.taille;}

	public String toString(){
		return "Texte (col:#"+  Integer.toHexString(getCouleur().getRGB()) +",Titre:"+ getTitre() +",Police:"+ getPolice() +",Taille:"+ getTaille() +")"; 
	}

	public void peindre(Graphics g){
		g.setColor(getCouleur());
		g.setFont(new Font(getPolice(), Font.BOLD, getTaille()));
		g.drawString(this.titre, getX(), getY()); 
	}
}
