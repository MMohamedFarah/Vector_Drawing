package model;

import java.awt.Color;
import java.awt.Graphics;

public interface Dessinable {
	public String toString();
	public void peindre(Graphics g);
	public void deplacer(int x,int y);
	public int getX();
	public int getY();
	public void setColor(Color c);
	public Color getCouleur();
}
