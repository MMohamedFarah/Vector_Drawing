package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

public class LigneBrisee extends Forme implements Dessinable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Point> ligneBrisee = new LinkedList<Point>() ;
	private float epaisseurLine ;

	public LigneBrisee (int x1, int y1, int x2, int y2, Color c, float el){
		super(x1,y1,c);
		ligneBrisee.add(new Point(x1,y1));
		ligneBrisee.add(new Point(x2,y2));
		epaisseurLine = el;
	}
	
	public LinkedList<Point> getLigneBrisee(){
		return ligneBrisee;
	}
	
	public float getEpaisseurLine(){
		return epaisseurLine;
	}
	public void setEpaisseurLine(float epLine){
		this.epaisseurLine = epLine;
	}
	
	public void deplacer(int x, int y){
		int newX, newY, varX,varY;
		varX = x - ((int)ligneBrisee.get(0).getX());
		varY = y - ((int)ligneBrisee.get(0).getY());
		super.deplacer(x, y);
		ligneBrisee.get(0).setLocation(x,y);
		for (int i=1; i<ligneBrisee.size(); i++){
			newX = ((int)ligneBrisee.get(i).getX()) + varX ;
			newY = ((int)ligneBrisee.get(i).getY()) + varY ;
			ligneBrisee.get(i).setLocation(newX, newY);
		}
	}
	
	public void addLigne(int x, int y){ // Ajouter les points à la liste et on choisit la couleur
		ligneBrisee.add(new Point(x,y));
	}

	public String toString(){
		return "Ligne Brisée (col:#"+  Integer.toHexString(getCouleur().getRGB()) +",PosX:"+ getX() +",PosY:"+ getY()+")"; 
	}

	public void peindre(Graphics g) {
		Graphics2D g1 = (Graphics2D) g;
		g1.setColor(getCouleur());
		BasicStroke line = new BasicStroke(epaisseurLine);
		g1.setStroke(line); 
		try{
			for (int i=0; i<ligneBrisee.size(); i++)
				g1.drawLine((int)ligneBrisee.get(i).getX(),(int)ligneBrisee.get(i).getY(),(int)ligneBrisee.get(i+1).getX(),(int)ligneBrisee.get(i+1).getY());
		}catch(Exception e){}
	}
}