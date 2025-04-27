package jeu;

/**
 * Enumération représentant les directions possibles de sortie dans le jeu.
 *
 * Les directions disponibles sont : Nord, Sud, Est, Ouest, Haut et Bas.
 *
 * Elles permettent de se déplacer d'une zone à une autre dans l'univers du jeu.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public enum Sortie {
	/** Sortie vers le Nord. */
	NORD,

	/** Sortie vers le Sud. */
	SUD,

	/** Sortie vers l'Est. */
	EST,

	/** Sortie vers l'Ouest. */
	OUEST,

	/** Sortie vers le haut (ex: monter un étage). */
	HAUT,

	/** Sortie vers le bas (ex: descendre un étage). */
	BAS;
}
