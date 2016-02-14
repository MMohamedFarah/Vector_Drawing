package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import model.Dessin;
import model.Dessinable;
import model.Ellipse;
import model.LigneBrisee;
import model.Rectangle;
import model.Texte;

public class Controller extends JPanel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dessin modele;
	private int nbDessin;
	private JComboBox<String> comboDessin;
	private JComboBox<String> comboSelect;
	private JComboBox<String> comboCalque;
	private JComboBox<String> comboTypePolice;
	private JComboBox<Integer> comboTaillePolice;
	private JComboBox<Float> comboEpaisseurLine;
	private JTextField titre;
	private JSpinner positX;
	private JSpinner positY;
	private JSpinner dimX;
	private JSpinner dimY;
	private JButton creation;
	private JButton modification;
	private JButton effacer;
	private JButton btnCouleur;
	private JButton calquer;
	private String forme ;
	private Color formColor = Color.BLACK; // couleur par défaut



	public Controller(Dessin m){
		this.modele=m;

		String[] listeDessin = {"Forme à créer","Rectangle","Ligne Brisée","Texte","Ellipse"};
		comboDessin = new JComboBox<String>(listeDessin);
		ComboDessinListener comboDessinListener = new ComboDessinListener();
		comboDessin.addActionListener(comboDessinListener);

		comboSelect = new JComboBox<String>();
		comboSelect.addItem("Modifier votre forme");
		ComboSelectListener comboSelectListener = new ComboSelectListener();
		comboSelect.addActionListener(comboSelectListener);

		titre = new JTextField("Saisissez votre texte",15);
		titre.setEnabled(false);

		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] listeTypePolice = e.getAvailableFontFamilyNames();
		comboTypePolice = new JComboBox<String>(listeTypePolice);

		Integer[] listeTaillePolice = {8,9,10,12,14,16,18,20,22,24,26,28,30,34,38,42,48,58,68,78,98,110,130};
		comboTaillePolice = new JComboBox<Integer>(listeTaillePolice);

		Float[] listeEpaisseurLine = {1.0f,1.5f,2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5.0f,5.5f,6.0f,6.5f,7.0f};
		comboEpaisseurLine = new JComboBox<Float>(listeEpaisseurLine);

		positX = new JSpinner();
		positX.setPreferredSize(new Dimension(50,20));
		positY = new JSpinner();
		positY.setPreferredSize(new Dimension(50,20));
		dimX = new JSpinner();
		dimX.setPreferredSize(new Dimension(50,20));
		dimX.setEnabled(false);
		dimY = new JSpinner();
		dimY.setPreferredSize(new Dimension(50,20));
		dimY.setEnabled(false);

		btnCouleur = new JButton("choix de la couleur");
		BtnCouleurListener btnCouleurListener = new BtnCouleurListener();
		btnCouleur.addActionListener(btnCouleurListener);

		creation = new JButton("Créer");
		CreationListener creationListener = new CreationListener();
		creation.addActionListener(creationListener);

		modification = new JButton("Modifier");
		ModifierListener redimensionListener = new ModifierListener();
		modification.addActionListener(redimensionListener);

		effacer = new JButton("Effacer");
		EffacerListener effacerListener = new EffacerListener();
		effacer.addActionListener(effacerListener);

		comboCalque = new JComboBox<String>();
		comboCalque.addItem("Déplacer dans calque n°");

		calquer = new JButton ("Calquer");
		CalqueListener calqueListener = new CalqueListener();
		calquer.addActionListener(calqueListener);

		this.add(titre);
		this.add(comboTypePolice);
		this.add(comboTaillePolice);
		this.add(new JLabel("Epaisseur de la ligne",JLabel.RIGHT));
		this.add(comboEpaisseurLine);
		this.add(new JLabel("X:",JLabel.RIGHT));
		this.add(positX);
		this.add(new JLabel("Y:",JLabel.RIGHT));
		this.add(positY);
		this.add(new JLabel("dimension X:",JLabel.RIGHT));
		this.add(dimX);
		this.add(new JLabel("dimension Y:",JLabel.RIGHT));
		this.add(dimY);
		this.add(comboDessin);
		this.add(btnCouleur);
		this.add(creation);
		this.add(comboSelect);
		this.add(modification);
		this.add(effacer);
		this.add(comboCalque);
		this.add(calquer);
	}

	class ComboDessinListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String item = (String) comboDessin.getSelectedItem();
			if (item.equals("Texte"))
				titre.setEnabled(true);
			else titre.setEnabled(false);
			if (item.equals("Rectangle") || item.equals("Ellipse")){
				dimX.setEnabled(true);
				dimY.setEnabled(true);
			}else{
				dimX.setEnabled(false);
				dimY.setEnabled(false);
			}
		}
	}

	class ComboSelectListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int index = comboSelect.getSelectedIndex();
			if ( index>0 ){
				Dessinable dess = modele.getDessinable(index-1);
				positX.setValue(new Integer(modele.getDessinable(index-1).getX()));
				positY.setValue(new Integer(modele.getDessinable(index-1).getY()));
				formColor = dess.getCouleur();
				if (dess instanceof Texte){
					Integer taille = ((Texte)dess).getTaille();
					String police = ((Texte)dess).getPolice();
					positY.setValue(new Integer((modele.getDessinable(index-1).getY() - (int)(taille*0.77))));
					titre.setEnabled(true);
					dimX.setEnabled(false);
					dimY.setEnabled(false);
					titre.setText(((Texte)dess).getTitre());
					comboTaillePolice.setSelectedItem(taille);
					comboTypePolice.setSelectedItem(police);
				}else if (dess instanceof Rectangle){
					titre.setEnabled(false);
					dimX.setEnabled(true);
					dimY.setEnabled(true);
					dimX.setValue(new Integer(((Rectangle)dess).getdimX()));
					dimY.setValue(new Integer(((Rectangle)dess).getdimY()));
				}else if (dess instanceof Ellipse){
					titre.setEnabled(false);
					dimX.setEnabled(true);
					dimY.setEnabled(true);
					dimX.setValue(new Integer(((Ellipse)dess).getdimX()));
					dimY.setValue(new Integer(((Ellipse)dess).getdimY()));
				}else if (dess instanceof LigneBrisee){
					float epLine = ((LigneBrisee)dess).getEpaisseurLine();
					comboEpaisseurLine.setSelectedItem(epLine);
				}
			}
		}
	}

	class BtnCouleurListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			formColor = JColorChooser.showDialog(null, "couleur de la forme", Color.BLACK);
		}
	}

	class CreationListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			forme = (String)comboDessin.getSelectedItem();
			try{
				int cordX = (Integer)positX.getValue();
				int cordY = (Integer)positY.getValue();
				int dX = (Integer)dimX.getValue();
				int dY = (Integer)dimY.getValue();
				if(forme != "Forme à créer"){
					if (forme == "Rectangle"){
						modele.ajouterDessin(new Rectangle(cordX,cordY,formColor,dX,dY));
					}
					else if (forme == "Texte"){
						String police = (String)comboTypePolice.getSelectedItem();
						int taille = (int) comboTaillePolice.getSelectedItem();
						modele.ajouterDessin(new Texte(cordX,cordY + (int)(taille*0.77),formColor,titre.getText(),police,taille));
					}else if (forme == "Ligne Brisée"){
						String mot = ((String) comboSelect.getSelectedItem());
						String[] tab = mot.split("-");
						if (tab[0] != "Modifier votre forme"){
							if (tab[1].equals("LigneBrisee")){
								LigneBrisee lb = (LigneBrisee) modele.getDessinable(comboSelect.getSelectedIndex()-1);
								lb.addLigne(cordX,cordY);
								modele.notifyDessinChanged();
							}
						}else{
							try{
								String point2 = JOptionPane.showInputDialog("Donner les coordonnées du 2ème point\"x,y\"\n e.g: \"100,110\"");
								String[] xy = point2.split(",");
								int cordX2 = Integer.parseInt(xy[0]);
								int cordY2 = Integer.parseInt(xy[1]);
								Float epLine = (Float)comboEpaisseurLine.getSelectedItem();
								modele.ajouterDessin(new LigneBrisee(cordX, cordY, cordX2, cordY2, formColor, epLine));
							}catch(ArrayIndexOutOfBoundsException aiobe){ throw new NumberFormatException();}
						}
					}else if(forme == "Ellipse") {
						modele.ajouterDessin(new Ellipse(cordX,cordY,formColor,dX,dY));
					}
				}
			}catch(NumberFormatException nfe){
				//Boîte du message d'erreur
				JOptionPane.showMessageDialog(null, "Utiliser des coordonnées entières et suiver les exemples", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class ModifierListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int index = comboSelect.getSelectedIndex();
			try{
				int cordX = (Integer)positX.getValue();
				int cordY = (Integer)positY.getValue();
				int dX = (Integer)dimX.getValue();
				int dY = (Integer)dimY.getValue();
				String police = (String)comboTypePolice.getSelectedItem();
				int taille = (int) comboTaillePolice.getSelectedItem();
				Float epLine = (Float)comboEpaisseurLine.getSelectedItem();
				if (comboDessin.getSelectedItem().equals("Texte"))
					modele.modifierDessin(index-1, cordX, cordY + (int)(taille*0.77), dX, dY, formColor, titre.getText(), police, taille, epLine);
				else modele.modifierDessin(index-1, cordX, cordY, dX, dY, formColor, titre.getText(), police, taille, epLine);
			}catch(NumberFormatException nfe){
				//Boîte du message d'erreur
				JOptionPane.showMessageDialog(null, "Utiliser des coordonnées entières", null, JOptionPane.ERROR_MESSAGE);
			}catch(IndexOutOfBoundsException ioobe){
				JOptionPane.showMessageDialog(null, "Sélectionner une forme à modifier", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class EffacerListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				int index = comboSelect.getSelectedIndex();// récupère l'index de la forme selectionner 
				modele.supprimerDessin(index-1);
			}catch(IndexOutOfBoundsException ioobe){
				JOptionPane.showMessageDialog(null, "Sélectionner une forme à effacer", null, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class CalqueListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (comboCalque.getSelectedItem() != ("Déplacer dans calque n°")){
				try{
					int index = comboSelect.getSelectedIndex();
					int numOrdre = comboCalque.getSelectedIndex();
					modele.ordreDessin(index-1,numOrdre-1);
				}catch(Exception ee){System.out.println(ee.getMessage());}
			}
		}
	}

	public void  updateCombo(){
		int index;
		String nomModel;
		comboSelect.removeAllItems();
		comboCalque.removeAllItems();
		comboSelect.addItem("Modifier votre forme");
		comboCalque.addItem("Déplacer dans calque n°");
		for (int i=0; i<nbDessin; i++){
			index = i+1;
			nomModel = modele.getDessinable(i).getClass().getName().substring(6); // ex : dans "model.Rectangle" j'aurais que "Rectangle"
			comboSelect.addItem(index +"-"+nomModel);
			comboCalque.addItem(""+index);
		}
	}

	public void update(Observable source, Object sizeDessin) {
		if ((source instanceof Dessin) && (sizeDessin != null) && (sizeDessin instanceof Integer)){
			this.modele = (Dessin)source;
			this.nbDessin = (Integer)sizeDessin;
			this.updateCombo();
		}
	}
}