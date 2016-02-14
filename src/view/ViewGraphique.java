package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Dessin;

public class ViewGraphique extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dessin modele;
	private int nbDessin;
	public Color fondCouleur = Color.WHITE;
	public Image fondImage = null;

	public void update(Observable source, Object sizeDessin) {
		if ((source instanceof Dessin) && (sizeDessin != null) && (sizeDessin instanceof Integer)){
			modele = (Dessin)source;
			nbDessin = (Integer)sizeDessin;
			repaint();
		}
	}

	public void paint(Graphics g){
		super.paint(g);
		if(fondCouleur != null)
			this.setBackground(fondCouleur);
		else if (fondImage != null)
			g.drawImage(fondImage, 0, 0, this.getWidth(), this.getHeight(), null);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		for (int i=0; i<nbDessin; i++){
			modele.getDessin().get(i).peindre(g);
		}	
	}
	
	public void setFond(Color c,Image img){
		fondCouleur = c;
		fondImage = img;
		repaint();
	}
	
	public Color getFondCouleur(){
		return fondCouleur;
	}
	
	public Image getFondImage(){
		return fondImage;
	}
}
