package com.addstones.sg.katas.tennis.methode2.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;

/**
 * Cette classe repr�sente le moteur du jeu: les actions et les mouvements
 */

public class GameEngine implements GameProperties, MouseMotionListener,
		Runnable, KeyListener {

	TennisStadium stadiumGraph;

	private int playerRacket_Y = RAQUETTE_Y;
	private int computerRacket_Y = RAQUETTE_ORDI_Y;

	private int playerScore;
	private int computerScore;

	private int playerSet;
	private int computerSet;

	private boolean duos;
	private boolean aventage_Joueur;
	private boolean aventage_Ordi;

	private int balle_X;
	private int balle_Y;
	private int deplacement_Vertical;

	private volatile boolean balle_Service = false;
	private boolean deplacement_Gauche = true;

	private JOptionPane infoQuit;

	public GameEngine(TennisStadium terrain) {

		stadiumGraph = terrain;

		/*
		 * cr�ation d'un thread qui va assurer le d�roulement du jeu
		 */
		Thread jeu = new Thread(this);
		jeu.start();
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

		/* r�cup�ration de la position du pointeur de la souris */
		int posSourisY = e.getY();

		/*
		 * si le pointeur est au dessus de la raquette du joueur et que celle-ci
		 * n'a pas d�pass� la limite de la table
		 */
		if (posSourisY < playerRacket_Y && playerRacket_Y > HAUT_TABLE) {

			/* déplacement de la raquette vers le haut de la table */
			playerRacket_Y = playerRacket_Y - INCR_RAQUETTE;
		}
		/* sinon déplacement de la raquette vers le bas de la table */
		else if (playerRacket_Y < BAS_TABLE) {

			playerRacket_Y = playerRacket_Y + INCR_RAQUETTE;
		}

		/*
		 * Transmisson de la nouvelle position de la raquette à la table (à la
		 * classe TerrainTennis plus précisement)
		 */
		stadiumGraph.mouvementRaquetteJoueur(playerRacket_Y);

	}

	public void run() {

		boolean rebondBalle = false;

		while (true) {

			/* si la balle est en jeu (en mouvement) */
			if (this.balle_Service) {

				/* si celle-ci se déplace vers la gauche */
				if (this.deplacement_Gauche && this.balle_X > BALLE_X_MIN) {

					rebondBalle = (this.balle_Y >= this.computerRacket_Y
							&& this.balle_Y < (this.computerRacket_Y + LONGUEUR_RAQUETTE) ? true
							: false);

					/* mise à jour de la position de la balle sur la table */
					this.balle_X -= INCR_BALLE;
					this.balle_Y -= this.deplacement_Vertical;
					stadiumGraph.positionBalle(this.balle_X, this.balle_Y);

					/* si la balle rebondie */
					if (this.balle_X <= RAQUETTE_ORDI_X && rebondBalle) {

						this.deplacement_Gauche = false;
					}
				}

				/* si celle-ci se déplace vers la droite */
				if (!this.deplacement_Gauche && this.balle_X <= BALLE_X_MAX) {

					rebondBalle = (this.balle_Y >= this.playerRacket_Y
							&& this.balle_Y < (this.playerRacket_Y + LONGUEUR_RAQUETTE) ? true
							: false);

					/* mise à jour de la position de la balle sur la table */
					this.balle_X += INCR_BALLE;

					stadiumGraph.positionBalle(this.balle_X, this.balle_Y);

					/* si la balle rebondie */
					if (this.balle_X >= stadiumGraph.getPlace_Raquette()
							&& rebondBalle) {

						this.deplacement_Gauche = true;
					}
				}

				/*
				 * d�placement de la raquette de l'ordinateur pour taper la
				 * balle
				 */

				double mouvement = Math.random() * 3;
				if (mouvement > 1) {
					if (this.computerRacket_Y < this.balle_Y
							&& this.computerRacket_Y < BAS_TABLE) {

						/* d�placement vers le haut */
						this.computerRacket_Y += INCR_RAQUETTE;
					} else if (this.computerRacket_Y > HAUT_TABLE) {

						/* d�placement vers le bas */
						this.computerRacket_Y -= INCR_RAQUETTE;
					}
				}

				/* mise � jour de la position de la raquette de l'ordinateur */
				stadiumGraph.mouvementRaquetteOrdi(this.computerRacket_Y);

				/* mise à jour du score lors d'un service */
				if (balleEnJeu()) {

					if (this.balle_X > BALLE_X_MAX) {
						ajusterScoreOrdinateur();
					} else if (this.balle_X < BALLE_X_MIN) {

						ajusterScoreJoueur();

					}
				}
			}

			/*
			 * ralentissement du thread en cas d'ordinateur puissant
			 */
			try {

				Thread.sleep(stadiumGraph.getVitesse_JEU());
			} catch (InterruptedException exception) {

				exception.printStackTrace();
			}
		}
	}

	private void ajusterScoreJoueur() {
		if (this.playerScore == 40) {

			if (this.computerScore == 40) {

				if (this.aventage_Joueur) {
					this.playerSet++;
					remisePointszero();

				} else if (this.aventage_Ordi) {
					this.aventage_Ordi = false;
				} else {
					this.aventage_Joueur = true;
				}

			} else {
				this.playerSet++;
				remisePointszero();
			}

		} else if (this.playerScore == 30) {
			this.playerScore = this.playerScore + 10;
		} else {
			this.playerScore = this.playerScore + 15;
		}
		affichageScore();
	}

	private void ajusterScoreOrdinateur() {
		if (this.computerScore == 40) {

			if (this.playerScore == 40) {

				if (this.aventage_Ordi) {
					this.computerSet = this.computerSet + 1;
					remisePointszero();

				} else if (this.aventage_Joueur) {
					this.aventage_Joueur = false;
				} else {
					this.aventage_Ordi = true;
				}

			} else {
				this.computerSet = this.computerSet + 1;
				remisePointszero();
			}

		} else if (this.computerScore == 30) {
			this.computerScore += 10;
		} else {
			this.computerScore += 15;
		}
		affichageScore();
	}

	private void remisePointszero() {
		this.playerScore = 0;
		this.computerScore = 0;
		this.aventage_Joueur = false;
		this.aventage_Ordi = false;
		this.duos = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		char touche = e.getKeyChar();

		if ('n' == touche || 'N' == touche) {

			nouvellePartie();
		} else if ('q' == touche || 'Q' == touche) {

			finJeu();
		} else if ('s' == touche || 'S' == touche) {

			serviceJeu();
		}

	}

	public void nouvellePartie() {

		this.playerScore = 0;
		this.computerScore = 0;

		this.playerSet = 0;
		this.computerSet = 0;

		this.duos = false;
		this.aventage_Joueur = false;
		this.aventage_Ordi = false;

		stadiumGraph.messagesJeu("Scores - Ordinateur : 0  " + " Joueur : 0",
				"0", "0", "0", "0");
		serviceJeu();

	}

	/**
	 * Méthode pour quitter le jeu à partir de la touche 'q' du clavier
	 */
	public void finJeu() {

		infoQuit = new JOptionPane();
		@SuppressWarnings("static-access")
		int choix = infoQuit.showConfirmDialog(null,
				"Voulez-vous vraiment quitter l'application ?", "Confirmation",
				JOptionPane.YES_NO_OPTION);

		if (choix == JOptionPane.YES_OPTION) {

			System.exit(0);
		}
	}

	/**
	 * Méthode permettant au joueur de servir en 1 er dans le jeu
	 */
	private void serviceJeu() {

		this.balle_Service = true;
		this.balle_X = stadiumGraph.getPlace_Raquette() - 1;
		this.balle_Y = playerRacket_Y;

		if (this.balle_Y > HAUTEUR_TABLE / 2) {
			this.deplacement_Vertical = 1;
		} else {
			this.deplacement_Vertical = -1;
		}

		stadiumGraph.positionBalle(balle_X, balle_Y);
		stadiumGraph.mouvementRaquetteJoueur(playerRacket_Y);
	}

	/**
	 * Méthode gérant l'affichage du score du jeu
	 */
	private void affichageScore() {

		this.balle_Service = false;
		String gagnant = getSetScore();

		/* si l'ordinateur atteint le score de 21 points */
		if (ORDINATEUR_GAGNANT.equals(gagnant)) {

			stadiumGraph.messagesJeu("Victoire de l'ordinateur " + computerSet
					+ ":" + playerSet, "0", "0", "" + computerSet, ""
					+ playerSet);
		}
		/* si c'est le joueur qui atteint le score de 21 points */
		else if (JOUEUR_GAGNANT.equals(gagnant)) {

			stadiumGraph.messagesJeu("Vous avez gagné " + playerSet + ":"
					+ computerSet, "0", "0", "" + computerSet, "" + playerSet);
		} else {

			stadiumGraph.messagesJeu("Ordinateur : " + computerScore
					+ " Joueur : " + playerScore, "" + computerScore, ""
					+ playerScore, "" + computerSet, "" + playerSet);

		}
	}

	/**
	 * Méthode pour indiquer si la balle est sortie de la table ou non
	 * 
	 * @return true : balle sortie, false : balle en jeu
	 */
	private boolean balleEnJeu() {

		if (this.balle_Y >= BALLE_Y_MIN && this.balle_Y <= BALLE_Y_MAX) {
			return true;
		} else {
			return false;
		}
	}

	public String getSetScore() {

		if (this.playerSet >= 5 && this.computerSet >= 5) {
			if (Math.abs(this.playerSet - this.computerSet) >= 2) {
				return getLeadPlayer();
			}
		} else if (this.playerSet == 6) {
			return "Joueur";
		} else if (computerSet == 6) {
			return "Ordianateur";
		}
		return "";
	}

	public String getLeadPlayer() {
		return (this.playerSet > this.computerSet) ? "Joueur" : "Ordinateur";
	}

	public TennisStadium getStadiumGraph() {
		return stadiumGraph;
	}

	public void setStadiumGraph(TennisStadium stadiumGraph) {
		this.stadiumGraph = stadiumGraph;
	}

	public int getPlayerRacket_Y() {
		return playerRacket_Y;
	}

	public void setPlayerRacket_Y(int playerRacket_Y) {
		this.playerRacket_Y = playerRacket_Y;
	}

	public int getComputerRacket_Y() {
		return computerRacket_Y;
	}

	public void setComputerRacket_Y(int computerRacket_Y) {
		this.computerRacket_Y = computerRacket_Y;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getComputerScore() {
		return computerScore;
	}

	public void setComputerScore(int computerScore) {
		this.computerScore = computerScore;
	}

	public int getPlayerSet() {
		return playerSet;
	}

	public void setPlayerSet(int playerSet) {
		this.playerSet = playerSet;
	}

	public int getComputerSet() {
		return computerSet;
	}

	public void setComputerSet(int computerSet) {
		this.computerSet = computerSet;
	}

	public boolean isDuos() {
		return duos;
	}

	public void setDuos(boolean duos) {
		this.duos = duos;
	}

	public boolean isAventage_Joueur() {
		return aventage_Joueur;
	}

	public void setAventage_Joueur(boolean aventage_Joueur) {
		this.aventage_Joueur = aventage_Joueur;
	}

	public boolean isAventage_Ordi() {
		return aventage_Ordi;
	}

	public void setAventage_Ordi(boolean aventage_Ordi) {
		this.aventage_Ordi = aventage_Ordi;
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

	public int getDeplacement_Vertical() {
		return deplacement_Vertical;
	}

	public void setDeplacement_Vertical(int deplacement_Vertical) {
		this.deplacement_Vertical = deplacement_Vertical;
	}

	public boolean isBalle_Service() {
		return balle_Service;
	}

	public void setBalle_Service(boolean balle_Service) {
		this.balle_Service = balle_Service;
	}

	public boolean isDeplacement_Gauche() {
		return deplacement_Gauche;
	}

	public void setDeplacement_Gauche(boolean deplacement_Gauche) {
		this.deplacement_Gauche = deplacement_Gauche;
	}

	public JOptionPane getInfoQuit() {
		return infoQuit;
	}

	public void setInfoQuit(JOptionPane infoQuit) {
		this.infoQuit = infoQuit;
	}

}
