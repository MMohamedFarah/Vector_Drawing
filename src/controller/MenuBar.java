package controller;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import view.ViewGraphique;

import model.Dessin;
import model.Dessinable;

public class MenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Dessin modele;
	private ViewGraphique viewGraphique;
	private String lastPath;
	MenuListener menuListener;

	private JMenuItem nouveau, ouvrir, sauvegarder, fondCouleur, fondImage;

	/**
	 * constructeur permetant la création d'une bar de menu
	 */
	public MenuBar(Dessin m, ViewGraphique view) {
		this.modele = m;
		this.viewGraphique = view;
		menuListener = new MenuListener();

		// Création du Menu Fichier
		JMenu fichierMenu = new JMenu("Fichier");
		nouveau = new JMenuItem("Nouveau");
		ouvrir = new JMenuItem("Ouvrir");
		sauvegarder = new JMenuItem("Sauvegarder", new ImageIcon(getClass().getResource("icons/sauvegarde.png")));

		nouveau.addActionListener(menuListener);
		ouvrir.addActionListener(menuListener);
		sauvegarder.addActionListener(menuListener);

		nouveau.setAccelerator(KeyStroke.getKeyStroke('N', ActionEvent.CTRL_MASK));
		ouvrir.setAccelerator(KeyStroke.getKeyStroke('O', ActionEvent.CTRL_MASK));
		sauvegarder.setAccelerator(KeyStroke.getKeyStroke('S', ActionEvent.CTRL_MASK));

		fichierMenu.add(nouveau);	
		fichierMenu.add(ouvrir);
		fichierMenu.add(sauvegarder);


		// Creation du Menu Editer
		JMenu editerMenu = new JMenu("Editer");
		fondCouleur = new JMenuItem("Changer le fond en une couleur unie");
		fondImage = new JMenuItem("Changer le fond en une image");

		fondCouleur.addActionListener(menuListener);
		fondImage.addActionListener(menuListener);

		fondCouleur.setAccelerator(KeyStroke.getKeyStroke('F', ActionEvent.CTRL_MASK));
		fondImage.setAccelerator(KeyStroke.getKeyStroke('F', ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));

		editerMenu.add(fondCouleur);
		editerMenu.add(fondImage);

		// ajout des Menus à MenuBar
		this.add(fichierMenu);
		this.add(editerMenu);
	}

	public class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Nouveau")){
				if (modele.getDessin().size()!=0 || viewGraphique.getFondCouleur()!=null || viewGraphique.getFondImage()!=null){
					int option = JOptionPane.showConfirmDialog(null, "Voulez-vous enregistrer votre travail actuel", "Nouvel espace de travail", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (option == JOptionPane.YES_OPTION)
						menuListener.actionPerformed(new ActionEvent(menuListener,0,"Sauvegarder"));
					else if (option == JOptionPane.NO_OPTION){
						modele.getDessin().removeAll(modele.getDessin());
						modele.notifyDessinChanged();
						viewGraphique.setFond(Color.WHITE,null);
					}
				}

			}else if(e.getActionCommand().equals("Sauvegarder")){
				JFileChooser dialogue;
				File sauveTravail = null;
				File sauveFond = null;
				try {
					if (lastPath != null){
						dialogue = new JFileChooser(new File(lastPath));
					}else{
						dialogue = new JFileChooser(new File("."));
					}
					if (dialogue.showSaveDialog(null)== JFileChooser.APPROVE_OPTION){
						sauveTravail = dialogue.getSelectedFile();
						// sauvegarder le modele 
						ObjectOutputStream flotWrite = new ObjectOutputStream(new FileOutputStream(sauveTravail));
						flotWrite.writeObject(modele);
						lastPath = sauveTravail.getParent();
						String name = "."+sauveTravail.getName();
						sauveFond = new File(lastPath,name);
						if (viewGraphique.getFondCouleur() == null){
							viewGraphique.setFond(null, null);
							flotWrite.writeObject(viewGraphique);
							if (new File(".",".tempImage").exists()){
								if (new File(lastPath,name+"Img").exists())
									new File(lastPath,name+"Img").delete();
								Files.copy(new File(".",".tempImage").toPath(), new File(lastPath,name+"Img").toPath());
								viewGraphique.setFond(null, ImageIO.read(new File(lastPath,name+"Img")));
								new File(".",".tempImage").delete();
							}else
								viewGraphique.setFond(null, ImageIO.read(new File(lastPath,name+"Img")));
						}else
							flotWrite.writeObject(viewGraphique);
						flotWrite.close();
						flotWrite.flush();
						JOptionPane.showMessageDialog(null, "Votre fichier est enregistré", null, JOptionPane.INFORMATION_MESSAGE);
					}
				}catch (IOException ioe){
					sauveTravail.delete();
					sauveFond.delete();
					JOptionPane.showMessageDialog(null, "Oups....Choisissez un autre nom", null, JOptionPane.ERROR_MESSAGE);
				}

			}else if(e.getActionCommand().equals("Ouvrir")){
				try {
					JFileChooser dialogue;
					if (lastPath != null){
						dialogue = new JFileChooser(new File(lastPath));
					}else{
						dialogue = new JFileChooser(new File("."));
					}
					if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
						File fichierPersistant = dialogue.getSelectedFile();
						ObjectInputStream flotRead = new ObjectInputStream(new FileInputStream(fichierPersistant));
						LinkedList<Dessinable> listeDessin = ((Dessin)flotRead.readObject()).getDessin();
						modele.updateDessin(listeDessin);
						lastPath = fichierPersistant.getParent();
						String name = "."+fichierPersistant.getName();
						try{
							ViewGraphique vg = (ViewGraphique)flotRead.readObject();
							Color fondCouleur = (Color) vg.getFondCouleur();
							if(fondCouleur != null){
								viewGraphique.setFond(fondCouleur, null);
							}else{
								viewGraphique.setFond(null, ImageIO.read(new File(lastPath,name+"Img")));
								if (new File(".",".tempImage").exists()){
									new File(".",".tempImage").delete();
								}
								Files.copy( new File(lastPath,name+"Img").toPath(), new File(".",".tempImage").toPath());
							}
							flotRead.close();
							JOptionPane.showMessageDialog(null, "Votre fichier enregistré est ouvert", null, JOptionPane.INFORMATION_MESSAGE);
						}catch(IOException ioe2){
							ioe2.getStackTrace();
						}catch(ClassNotFoundException cnfe){}
					}
				}catch (IOException ioe){
					JOptionPane.showMessageDialog(null, "Sélectionner un fichier de sauvegarde valide", null, JOptionPane.ERROR_MESSAGE);
				}catch (ClassNotFoundException cnfe){}

			}else if(e.getActionCommand().equals("Changer le fond en une couleur unie")){
				Color couleur = JColorChooser.showDialog(null, "Choisir la couleur de fond", Color.BLACK);
				viewGraphique.setFond(couleur,null);

			}else if(e.getActionCommand().equals("Changer le fond en une image")){
				JFileChooser dialogue;
				if (lastPath != null){
					dialogue = new JFileChooser(new File(lastPath));
				}else{
					dialogue = new JFileChooser(new File("."));
				}
				if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
					File file = dialogue.getSelectedFile();
					lastPath = file.getParent();
					try {
						if (new File(".",".tempImage").exists()){
							new File(".",".tempImage").delete();
						}
						Files.copy(file.toPath(), new File(".",".tempImage").toPath());
						viewGraphique.setFond(null,ImageIO.read(new File(".",".tempImage")));
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null, "Sélectionner une image valide", null, JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}