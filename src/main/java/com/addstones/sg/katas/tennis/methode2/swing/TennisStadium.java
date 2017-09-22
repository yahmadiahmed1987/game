package com.addstones.sg.katas.tennis.methode2.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

//import java.awt.Point;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Cette classe se charge de dessiner le terrain de Tennis
 * 
 */
public class TennisStadium extends JPanel implements GameProperties {

	/**
	 * 
	 */
	private static final long serialVersionUID = -908399231986643246L;

	private JLabel label;
	private JLabel setUser;
	private JLabel setOrdi;
	private JLabel pointsUser;
	private JLabel pointsOrdi;
	private JLabel joueur;
	private JLabel ordinateur;
	private JLabel set;
	private JLabel points;
	private JLabel separartion;

	private int vitesse_JEU = RALENTIE_JEU;
	private int raquetteOrdi_Y = RAQUETTE_ORDI_Y;
	private int raquetteJoueur_Y = RAQUETTE_Y;
	private int place_Raquette = RAQUETTE_X;

	private int balle_X = BALLE_X_DEPART;
	private int balle_Y = BALLE_Y_DEPART;

	Dimension tailleTable = new Dimension(LARGEUR_TABLE, HAUTEUR_TABLE);

	public TennisStadium() {

		GameEngine moteur = new GameEngine(this);
		addMouseMotionListener(moteur);
		/* Ecouteur pour récupérer les commandes du clavier (n, s, et q) */
		addKeyListener(moteur);

	}

	public void ajoutInterface(Container conteneur) {

		/* constitution de l'interface de jeu (table, affichage coordonnées) */
		conteneur.setLayout(new BoxLayout(conteneur, BoxLayout.Y_AXIS));
		conteneur.add(this);

		JPanel scorePanel = new JPanel(new GridLayout(3, 3, 1, 1));

		this.setUser = new JLabel("0");
		this.setOrdi = new JLabel("0");
		this.pointsUser = new JLabel("0");
		this.pointsOrdi = new JLabel("0");
		this.joueur = new JLabel("JOUEUR :");
		this.ordinateur = new JLabel("ORDINATEUR :");
		this.set = new JLabel("SETS");
		this.points = new JLabel("POINTS");
		this.separartion = new JLabel("");

		/*
		 * création du JLabel pour l'affichage de la position de la souris sur
		 * l'interface de jeu
		 */
		label = new JLabel(
				"n : nouvelle partie, s : servir, q : quitter (acc�es par le menu �galement)");
		scorePanel.add(joueur);
		scorePanel.add(setUser);
		scorePanel.add(pointsUser);
		scorePanel.add(separartion);
		scorePanel.add(set);
		scorePanel.add(points);
		scorePanel.add(ordinateur);
		scorePanel.add(setOrdi);
		scorePanel.add(pointsOrdi);
		conteneur.add(scorePanel);
		conteneur.add(label);
	}

	/**
	 * M�thode affectant la taille de la table au cadre de jeu
	 * 
	 * @return la taille de la table de jeu
	 */
	public Dimension getPreferredSize() {

		return tailleTable;
	}

	/**
	 * @param graphisme
	 *            conteneur de tous les �l�ments
	 */
	public void paintComponent(Graphics graphisme) {

		super.paintComponent(graphisme);

		/* la table en forme de rectangle */
		graphisme.setColor(Color.GREEN);
		graphisme.fillRect(0, 0, LARGEUR_TABLE, HAUTEUR_TABLE);

		/* la 1 �re raquette en forme de rectangle */
		graphisme.setColor(Color.RED);
		graphisme.fillRect(this.place_Raquette, this.raquetteJoueur_Y,
				LARGEUR_RAQUETTE, LONGUEUR_RAQUETTE);

		/* la 2 �me raquette en forme de rectangle */
		graphisme.setColor(Color.BLUE);
		graphisme.fillRect(RAQUETTE_ORDI_X, this.raquetteOrdi_Y,
				LARGEUR_RAQUETTE, LONGUEUR_RAQUETTE);

		/* la balle en forme de cercle */
		graphisme.setColor(Color.WHITE);
		graphisme.fillOval(this.balle_X, this.balle_Y, 10, 10);

		/* les lignes de la table */
		graphisme.setColor(Color.WHITE);
		graphisme.drawRect(10, 10, LARGE_LINE, LONG_LINE);
		graphisme.drawLine(X1, 10, X2, LINE_MEDIUM);

		this.requestFocus();
	}

	/**
	 * m�thode permettant de connaitre la position de la raquette sur la table
	 * de jeu
	 * 
	 * @return la position de la raquette du joueur
	 */
	public int positionRaquetteJoueur() {

		return raquetteJoueur_Y;
	}

	/**
	 * m�thode permettant de déplacer la raquette du joueur
	 * 
	 * @param deplacement
	 *            indicateur de déplacement
	 */
	public void mouvementRaquetteJoueur(int deplacement) {

		this.raquetteJoueur_Y = deplacement;
		this.repaint();
	}

	/**
	 * méthode permettant de déplacer la raquette de l'ordinateur
	 * 
	 * @param deplacement
	 *            indicateur de déplacement
	 */
	public void mouvementRaquetteOrdi(int deplacement) {

		this.raquetteOrdi_Y = deplacement;
		this.repaint();
	}

	/**
	 * méthode gérant les messages du jeu
	 * 
	 * @param mess
	 *            message à afficher
	 */
	public void messagesJeu(String mess, String score_Ordi,
			String score_Joueur, String set_Ordi, String set_Joueur) {

		this.label.setText(mess);

		this.pointsOrdi.setText(score_Ordi);
		this.pointsUser.setText(score_Joueur);

		this.setOrdi.setText(set_Ordi);
		this.setUser.setText(set_Joueur);

		this.repaint();
	}

	/**
	 * m�thode d�finie la position de la balle
	 * 
	 * @param x
	 *            abcisse de la balle
	 * @param y
	 *            ordonnée de la balle
	 */
	public void positionBalle(int x, int y) {

		this.balle_X = x;
		this.balle_Y = y;
		this.repaint();
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JLabel getSetUser() {
		return setUser;
	}

	public void setSetUser(JLabel setUser) {
		this.setUser = setUser;
	}

	public JLabel getSetOrdi() {
		return setOrdi;
	}

	public void setSetOrdi(JLabel setOrdi) {
		this.setOrdi = setOrdi;
	}

	public JLabel getPointsUser() {
		return pointsUser;
	}

	public void setPointsUser(JLabel pointsUser) {
		this.pointsUser = pointsUser;
	}

	public JLabel getPointsOrdi() {
		return pointsOrdi;
	}

	public void setPointsOrdi(JLabel pointsOrdi) {
		this.pointsOrdi = pointsOrdi;
	}

	public JLabel getJoueur() {
		return joueur;
	}

	public void setJoueur(JLabel joueur) {
		this.joueur = joueur;
	}

	public JLabel getOrdinateur() {
		return ordinateur;
	}

	public void setOrdinateur(JLabel ordinateur) {
		this.ordinateur = ordinateur;
	}

	public JLabel getSet() {
		return set;
	}

	public void setSet(JLabel set) {
		this.set = set;
	}

	public JLabel getPoints() {
		return points;
	}

	public void setPoints(JLabel points) {
		this.points = points;
	}

	public JLabel getSeparartion() {
		return separartion;
	}

	public void setSeparartion(JLabel separartion) {
		this.separartion = separartion;
	}

	public int getVitesse_JEU() {
		return vitesse_JEU;
	}

	public void setVitesse_JEU(int vitesse_JEU) {
		this.vitesse_JEU = vitesse_JEU;
	}

	public int getRaquetteOrdi_Y() {
		return raquetteOrdi_Y;
	}

	public void setRaquetteOrdi_Y(int raquetteOrdi_Y) {
		this.raquetteOrdi_Y = raquetteOrdi_Y;
	}

	public int getRaquetteJoueur_Y() {
		return raquetteJoueur_Y;
	}

	public void setRaquetteJoueur_Y(int raquetteJoueur_Y) {
		this.raquetteJoueur_Y = raquetteJoueur_Y;
	}

	public int getPlace_Raquette() {
		return place_Raquette;
	}

	public void setPlace_Raquette(int place_Raquette) {
		this.place_Raquette = place_Raquette;
	}

	public int getBalle_X() {
		return balle_X;
	}

	public void setBalle_X(int balle_X) {
		this.balle_X = balle_X;
	}

	public int getBalle_Y() {
		return balle_Y;
	}

	public void setBalle_Y(int balle_Y) {
		this.balle_Y = balle_Y;
	}

	public Dimension getTailleTable() {
		return tailleTable;
	}

	public void setTailleTable(Dimension tailleTable) {
		this.tailleTable = tailleTable;
	}

}
