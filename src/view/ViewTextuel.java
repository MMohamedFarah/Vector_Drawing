package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Dessin;

public class ViewTextuel extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dessin dessiner;
	private int nbDessin;
	
	public void update(Observable source, Object sizeDessin) {
		if ((source instanceof Dessin) && (sizeDessin != null) && (sizeDessin instanceof Integer)){
			dessiner = (Dessin)source;
			nbDessin = (Integer)sizeDessin;
			repaint();
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createRaisedBevelBorder());

		for (int i=0; i<nbDessin; i++){
			g.setFont(new Font("SANS_SERIF", Font.PLAIN, 13));
			g.drawString(i+1+"-"+dessiner.getDessin().get(i).toString(),0,i*15+15); // i*15+15 est pour decaler entre les écritures 
		}
	}

}
