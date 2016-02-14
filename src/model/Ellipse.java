package model;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Ellipse extends FormeAvecDimension implements Dessinable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ellipse(int x, int y, Color c, int dX, int dY){
		super(x, y, c, dX, dY);
	}
	
	public String toString(){
		return "Ellipse (col :#"+  Integer.toHexString(getCouleur().getRGB()) +",PosX:"+ getX() +",PosY:"+ getY() +",dimX:"+ this.getdimX() +",dimY:"+ this.getdimY()+")";
	}

	public void peindre(Graphics g){
		g.setColor(getCouleur());
		g.fillOval(getX(),getY(),getdimX(),getdimY());
	}
}
