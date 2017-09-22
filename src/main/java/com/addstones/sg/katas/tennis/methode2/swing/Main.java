package com.addstones.sg.katas.tennis.methode2.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Main extends JFrame implements GameProperties, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1599088323585283291L;
	public JMenuBar barreMenu;
	public JMenu file;
	public JMenuItem newGame;
	public JMenuItem leave;
	public JOptionPane infoQuit ; // infoTerrain;

	GameEngine tennisEngine;

	TennisStadium table;

	/**
	 * methods to intialisate all graphic interface compositions
	 */
	private void initComposants() {

		barreMenu = new JMenuBar();
		file = new JMenu();
		newGame = new JMenuItem();
		leave = new JMenuItem();


		this.setTitle("Jeu de Tennis");

		file.setText("Fichier");
		file.setMnemonic('F');
		newGame.setText("Nouvelle Partie");
		leave.setText("Quitter");


		/* raccourci clavier pour l'option "Nouvelle partie" (ctrl + n) */
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				KeyEvent.CTRL_MASK));

		/* raccourci clavier pour l'option "Quitter" (ctrl + q) */
		leave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_MASK));

		/* Ecouteurs d'�vnements sur les options du menu */
		newGame.addActionListener(this);
		leave.addActionListener(this);


		/*
		 * ajout de l'option "Nouvelle Partie" et "Quitter" dans l'onglet
		 * "Fichier"
		 */
		file.add(newGame);
		file.add(leave);


		/* ajout de l'onglet "Fichier" */
		barreMenu.add(file);

		/* ajout de la barre de menu à l'interface */
		this.setJMenuBar(barreMenu);

	}

	public Main() {

		initComposants();

		/*
		 * évènements lors de la fermeture de l'application par le croix en
		 * haut à droite
		 */
		this.addWindowListener(new WindowAdapter() {

			/**
			 * Méthode appelée lors de la fermeture de l'application
			 */
			@Override
			public void windowClosing(WindowEvent e) {

				/*
				 * Demande de confirmation à l'utilisateur pour quitter
				 * l'application
				 */
				infoQuit = new JOptionPane();
				@SuppressWarnings("static-access")
				int choix = infoQuit.showConfirmDialog(null,
						"Voulez-vous vraiment quitter le jeu ?",
						"Confirmation", JOptionPane.YES_NO_OPTION);

				if (choix == JOptionPane.YES_OPTION) {

					System.exit(0);
				} else {

					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
			}

		});

		table = new TennisStadium();
		table.ajoutInterface(this.getContentPane());
		tennisEngine = new GameEngine(table);

		this.pack();
		this.setBounds(0, 0, LARGEUR_TAERRAIN , HAUTEUR_TERRAIN ); // LARGEUR_TABLE + 90 ,HAUT_TABLE + 80);

		/* Place au milieu de l'�cran la fen�tre d'application */
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/**
	 * méthode gérant les évènements sur les éléments graphiques
	 * 
	 * @param e
	 *            l'élément en question
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == newGame) {

			tennisEngine.nouvellePartie();
		}

		if (e.getSource() == leave) {

			tennisEngine.finJeu();

		}


	}

	public static void main(String[] args) {

		new Main();

	}



}
