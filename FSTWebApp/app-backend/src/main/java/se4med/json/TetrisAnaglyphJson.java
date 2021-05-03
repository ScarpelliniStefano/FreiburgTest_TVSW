package se4med.json;

public class TetrisAnaglyphJson {
	
	public static String score = "score"; //final score
	public static String completedLines = "completedLines"; //lives remaning
	public static String colors = "colors"; //final lens color
	public static String initColor = "initColor"; //initial lens color
	

	// table USERAPP -> settings
	public static String startingColor = "startingColor"; //select the starting level
	public static String endingColor = "endingColor"; //select the final level

	public static String lensesAmblCol = "lensesAmblCol";// color of lens in fronto of  amblyopic eye
	public static String lensesNonAmblCol = "lensesNonAmblCol"; // color of lens in fronto of non amblyopic eye
	public static String eye = "eye"; // amblyopic Eye
	public static String maxColor = "maxColor"; // select color
	public static String linesThreshold = "linesThreshold"; //Ogni quante righe cancellate cambiare colore
	public static String speedUp = "speedUp";  // Aumento (in ms) della velocità dei blocchi ogni volta che i blocchi cambiano colore
	public static String blockSizeValue = "blockSizeValue"; // Dimensione dei blocchi
	public static String helpLinesValue = "helpLinesValue"; // Mostra linee guida/griglia
	public static String gameTime = "gameTime"; // Game time

	
	
}