package com.addstones.sg.katas.tennis.methode2.swing;


/**
 * Interface regroupant toutes les variables du jeu (dimension table, raquette...)
 *
 */
public interface GameProperties {

    /* toutes les dimensions sont exprimées en pixels */

    /* dimension de la table */
    public final int LARGEUR_TABLE = 320;
    public final int LARGEUR_TAERRAIN = 550;
    public final int HAUTEUR_TABLE = 220;
    public final int HAUTEUR_TERRAIN = 420;
    public final int HAUT_TABLE = 12;
    public final int BAS_TABLE = 180;

    /* définie la position de départ des raquettes ainsi que leurs dimension */
    public final int RAQUETTE_Y = 100;
    public final int RAQUETTE_X = 300;
    public final int RAQUETTE_ORDI_Y = 100;
    public final int RAQUETTE_ORDI_X = 15;
    public final int LONGUEUR_RAQUETTE = 30;
    public final int LARGEUR_RAQUETTE = 5;

    /* rapidité de mouvement de la raquette (en pixels) */
    public final int INCR_RAQUETTE = 2;
    
    /* rapidité de mouvement de la balle (en pixels) */
    public final int INCR_BALLE = 4;
    
    /* définition de l'espace de déplacement de la balle */
    public final int BALLE_Y_MIN = 1 + INCR_BALLE;
    public final int BALLE_X_MIN = 1 + INCR_BALLE;
    public final int BALLE_Y_MAX = HAUTEUR_TABLE - INCR_BALLE;
    public final int BALLE_X_MAX = LARGEUR_TABLE - INCR_BALLE;


    /* définition de la position de départ de la balle au lancement d'une partie */
    public final int BALLE_Y_DEPART = HAUTEUR_TABLE / 2;
    public final int BALLE_X_DEPART = LARGEUR_TABLE /2;

    public final String JOUEUR_GAGNANT = "Joueur";
    public final String ORDINATEUR_GAGNANT = "Ordianateur";

    /* pour ralentir le jeu en cas d'ordinateur très rapide (millisecondes) */
    public final int RALENTIE_JEU = 10;
    
	public final int LARGE_LINE = 300;
	public final int LONG_LINE = 200;
	public final int X1 = 160;
	public final int X2 = 160;
	public final int LINE_MEDIUM = 210;
	public final int PLACE_RAQUETTE = 300;

}
