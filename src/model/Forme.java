package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

public class Forme implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Point p;
	private Color formeCouleur;
	
	public Forme(int x, int y, Color c){
		this.p = new Point(x,y);
		this.formeCouleur = c;
	}
	
	public void deplacer(int x, int y){
		this.p.setLocation(x,y);
	}
	
	public void setColor(Color c){this.formeCouleur = c;}
	
	public int getX(){return (int)p.getX();}
	public int getY(){return (int)p.getY();}
	
	public Color getCouleur(){return formeCouleur;}
}
