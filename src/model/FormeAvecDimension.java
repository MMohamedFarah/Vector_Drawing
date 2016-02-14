package model;

import java.awt.Color;

public class FormeAvecDimension extends Forme{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int dimX,dimY;

	public FormeAvecDimension(int x, int y, Color c, int dX, int dY){
		super(x, y, c);
		this.dimX = dX;
		this.dimY = dY;
	}
	
	/*Modification des dimmensions*/
	public void setDimension(int dx, int dy){
		this.dimX = dx;
		this.dimY = dy;
	}
	
	public int getdimX(){return dimX;}
	public int getdimY(){return dimY;}

}
