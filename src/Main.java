import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import controller.Controller;
import controller.MenuBar;

import view.ViewGraphique;
import view.ViewTextuel;

import model.Dessin;


public class Main extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Dessin modele = new Dessin();
	public Main(){
		super("Dessin Vectoriel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultLookAndFeelDecorated(true);
		ViewGraphique graphicView = new ViewGraphique();
		ViewTextuel textuelView = new ViewTextuel();
		Controller controller = new Controller(modele);
		MenuBar menuBar = new MenuBar(modele, graphicView);

		modele.addObserver(graphicView);
		modele.addObserver(textuelView);
		modele.addObserver(controller);

		JPanel fenetre = new JPanel(new BorderLayout());
		JSplitPane dessinFenetre = new JSplitPane(JSplitPane.VERTICAL_SPLIT, graphicView, textuelView);
		dessinFenetre.setOneTouchExpandable(true);
		dessinFenetre.setResizeWeight(0.9);
		JSplitPane splitFenetre = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controller, dessinFenetre);
		splitFenetre.setDividerLocation(100);
		splitFenetre.setDividerSize(0);
		fenetre.add(splitFenetre, BorderLayout.CENTER);

		fenetre.setPreferredSize(new Dimension(800,600));
		this.setMinimumSize(new Dimension(250, 200));
		this.setJMenuBar(menuBar);
		this.getContentPane().add(fenetre);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				new Main();
			}
		});   
	}
}
