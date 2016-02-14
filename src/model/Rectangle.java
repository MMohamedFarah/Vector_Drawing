package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Rectangle extends FormeAvecDimension implements Dessinable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Rectangle(int x, int y, Color c, int dX, int dY){
		super(x, y, c, dX, dY);
	}
	public String toString(){
		return "Rectangle (col :#"+  Integer.toHexString(getCouleur().getRGB()) +",PosX:"+ getX() +",PosY:"+ getY() +",dimX:"+ this.getdimX() +",dimY:"+ this.getdimY()+")";
	}
	
	public void peindre(Graphics g){
		g.setColor(getCouleur());
		g.fillRect(getX(), getY(), getdimX(), getdimY());
	}
}
