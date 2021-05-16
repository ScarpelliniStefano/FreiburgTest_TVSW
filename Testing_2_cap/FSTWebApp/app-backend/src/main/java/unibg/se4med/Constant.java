package unibg.se4med;

/** some constants for the connection to the DB*/
public class Constant {
	
	//DB url DA AGGIORNARE
	//public static String url = "jdbc:mysql://localhost:3306/se4med";
	public static String url = "jdbc:mysql://localhost:3306/se4med?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";

	//DB user
	public static String user = "rootFR";
	//public static String user = "root";
	//DB pw
	public static String password = "rootfreiburg"; 
	//public static String password = "CVnXeGxr"; //Server: CVnXeGxr
										//Silvia: prova
	//Change PW also in pom.xml in se4med-backend project
	public static String languageFile = "MessagesBundle"; //file name containing the translation into different languages

	public static String urlMed = "jdbc:mysql://125.156.12.31:8636/AIMOmed?useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";

	//DB user
	public static String userMed = "FRTest";

	//DB pw
	public static String passwordMed = "Ghdgsi748bnsh"; 
	//public static String password = "CVnXeGxr"; //Server: CVnXeGxr
}
