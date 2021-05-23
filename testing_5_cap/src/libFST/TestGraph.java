package libFST;

import java.io.Console;

public class TestGraph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//org.graphwalker.cli.CLI.main(("convert -f JAVA -i model/test.json").split(" "));
		//org.graphwalker.cli.CLI.main(("convert -f GRAPHML -i model/test.json").split(" "));
		org.graphwalker.cli.CLI.main(("offline -m model/test.json random(edge_coverage(100))").split(" "));
	}
	
	
	
	private static void stringSplitter() {
		String text="";
		text=text.replace("{\"currentElementName\":\"", "");
		text=text.replace("\"}", "()");
		System.out.println(text);
	}
	
	-{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_exitTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_exitTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_right\"}\r\n" + 
			"{\"currentElementName\":\"v_rightRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_changeCurrRight\"}\r\n" + 
			"{\"currentElementName\":\"v_changeCurrDepth\"}\r\n" + 
			"{\"currentElementName\":\"e_changeToControlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_changePosFromRightWrong\"}\r\n" + 
			"{\"currentElementName\":\"v_changePosition\"}\r\n" + 
			"{\"currentElementName\":\"e_newChoiseFromChance\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_stopOrChoiceNull\"}\r\n" + 
			"{\"currentElementName\":\"v_fineNotCert\"}\r\n" + 
			"{\"currentElementName\":\"e_NewEdge\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_wrongAnswerMaxDepth\"}\r\n" + 
			"{\"currentElementName\":\"v_decrementChance\"}\r\n" + 
			"{\"currentElementName\":\"e_changePos\"}\r\n" + 
			"{\"currentElementName\":\"v_changePosition\"}\r\n" + 
			"{\"currentElementName\":\"e_newChoiseFromChance\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_stopOrChoiceNull\"}\r\n" + 
			"{\"currentElementName\":\"v_fineNotCert\"}\r\n" + 
			"{\"currentElementName\":\"e_NewEdge\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_exitTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_exitTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_wrong\"}\r\n" + 
			"{\"currentElementName\":\"v_wrongRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_changeCurrWrong\"}\r\n" + 
			"{\"currentElementName\":\"v_changeCurrDepth\"}\r\n" + 
			"{\"currentElementName\":\"e_changeToControlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_changePosFromRightWrong\"}\r\n" + 
			"{\"currentElementName\":\"v_changePosition\"}\r\n" + 
			"{\"currentElementName\":\"e_newChoiseFromChance\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_esciChoice\"}\r\n" + 
			"{\"currentElementName\":\"v_esci\"}\r\n" + 
			"{\"currentElementName\":\"e_exitFromTest\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}\r\n" + 
			"{\"currentElementName\":\"e_rifai\"}\r\n" + 
			"{\"currentElementName\":\"v_reset\"}\r\n" + 
			"{\"currentElementName\":\"e_startTest\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_richiestaPos\"}\r\n" + 
			"{\"currentElementName\":\"v_choise\"}\r\n" + 
			"{\"currentElementName\":\"e_posScelta\"}\r\n" + 
			"{\"currentElementName\":\"v_started\"}\r\n" + 
			"{\"currentElementName\":\"e_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_control\"}\r\n" + 
			"{\"currentElementName\":\"e_right\"}\r\n" + 
			"{\"currentElementName\":\"v_rightRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_changeCurrRight\"}\r\n" + 
			"{\"currentElementName\":\"v_changeCurrDepth\"}\r\n" + 
			"{\"currentElementName\":\"e_changeToControlRisp\"}\r\n" + 
			"{\"currentElementName\":\"v_controlRisp\"}\r\n" + 
			"{\"currentElementName\":\"e_fineCert\"}\r\n" + 
			"{\"currentElementName\":\"v_exit\"}"
}
