package unibg.se4med;

import org.json.JSONObject;

import se4med.json.AlienRunJson;
import se4med.json.BreakoutJson;
import se4med.json.RuneyeJson;
import se4med.json.SuppressionTestJson;
import se4med.json.TetrisAnaglyphJson;

public class DefaultAppSettings {


	// BREAKOUT JSON EXAMPLE
	// {"name":"user11","difficulty":"0","colored":"1100","rightLensCol":"0","eye":"0","colorChange":"50",
	// "maxColor":255,"sound":"0","effects":"0","startingColor":"0.25","endingColor":"0.75"}
	public static String breakoutSettings = new JSONObject().put(BreakoutJson.difficulty, new String("0"))
			.put(BreakoutJson.startingColor, new String("0")).put(BreakoutJson.endingColor, new String("1"))
			.put(BreakoutJson.colored, new String("1000"))// Only ball is colored
			.put(BreakoutJson.lensesAmblCol, new String("B")).put(BreakoutJson.lensesNonAmblCol, new String("R"))
			.put(BreakoutJson.eye, new String("0")).put(BreakoutJson.colorChange, new String("50"))
			.put(BreakoutJson.maxColor, new String("255")).put(BreakoutJson.effects, new String("0"))
			.put(BreakoutJson.sound, new String("0")).toString();

	public static String alienRunSettings = new JSONObject().put(AlienRunJson.startingColor, new String("0"))
			.put(AlienRunJson.endingColor, new String("1")).put(AlienRunJson.colored, new String("100"))// Only ball is
																										// colored
			.put(AlienRunJson.lensesAmblCol, new String("B")).put(AlienRunJson.lensesNonAmblCol, new String("R"))
			.put(AlienRunJson.eye, new String("0")).put(AlienRunJson.colorChange, new String("50"))
			.put(AlienRunJson.maxColor, new String("255")).put(AlienRunJson.effects, new String("0")).toString();

	public static String tetrisAnaglyphSettings = new JSONObject()
			.put(TetrisAnaglyphJson.startingColor, new String("0")).put(TetrisAnaglyphJson.endingColor, new String("1"))
			.put(TetrisAnaglyphJson.lensesAmblCol, new String("B"))
			.put(TetrisAnaglyphJson.lensesNonAmblCol, new String("R")).put(TetrisAnaglyphJson.eye, new String("0"))
			.put(TetrisAnaglyphJson.maxColor, new String("255")).put(TetrisAnaglyphJson.linesThreshold, new String("0"))
			.put(TetrisAnaglyphJson.helpLinesValue, new String("1")).put(TetrisAnaglyphJson.speedUp, new String("0"))
			.toString();

	public static String teamRacingSettings = "";

	public static String runeyeSettings = new JSONObject().put(RuneyeJson.difficulty, new String("0"))
			.put(RuneyeJson.eye, new String("0")).toString();

	public static String suppTestSettings = new JSONObject().put(SuppressionTestJson.left_red, 255)
			.put(SuppressionTestJson.left_blue, 0).put(SuppressionTestJson.left_green, 0)
			.put(SuppressionTestJson.right_red, 0).put(SuppressionTestJson.right_blue, 255)
			.put(SuppressionTestJson.right_green, 0).toString();

	public String getDefaultSettings(String id) {
		switch (id) {
		case "Breakout":
			return breakoutSettings;
		case "TeamRacing":
			return teamRacingSettings;
		case "AlienRun":
			return alienRunSettings;
		case "TetrisA":
			return tetrisAnaglyphSettings;
		case "Runeye":
			return runeyeSettings;
		case "SuppTest":
			return suppTestSettings;
		default:
			return "";
		}
	}
}
