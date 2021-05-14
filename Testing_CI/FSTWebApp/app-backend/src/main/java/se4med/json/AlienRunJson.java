package se4med.json;

public class AlienRunJson {
	
	public static String points = "points"; //final score
	public static String lives = "lives"; //lives remaning
	public static String colors = "colors"; //final lens color
	public static String initColor = "initColor"; //initial lens color
	
	// table USERAPP -> settings
	public static String startingColor = "startingColor"; //select the starting level
	public static String endingColor = "endingColor"; //select the final level
	public static String colored="colored"; //is a sequence of 0 and 1
	/*
	 * 1^ elem: Lumache is colored? 1:true 0:false
	 * 2^ elem: Mosche is colored? 1:true 0:false
	 * 3^ elem: Monete is colored? 1:true 0:false
	 */
	public static String lensesAmblCol = "lensesAmblCol";// color of lens in fronto of  amblyopic eye
	public static String lensesNonAmblCol = "lensesNonAmblCol"; // color of lens in fronto of non amblyopic eye
	public static String eye = "eye"; // amblyopic Eye
	public static String colorChange = "colorChange"; // every X points change color
	public static String maxColor = "maxColor"; // select color
	public static String effects = "effects"; // select music on/off

	
	
	
}