package libFST;

import org.graphwalker.core.condition.*;
import org.graphwalker.core.generator.*;
import org.graphwalker.core.machine.*;
import org.graphwalker.core.model.*;

public class test_freiburg {

  public final class ModelTestContext extends ExecutionContext {
  }

  public static void main(String... aArgs) {
    test_freiburg modeltest = new test_freiburg();
    modeltest.run();
  }

  void run() {
    Vertex v_reset = new Vertex().setName("v_reset").setId("03f41f90-b943-11eb-b3fa-3bf87734ccf2");
Vertex v_started = new Vertex().setName("v_started").setId("051ac540-b943-11eb-b3fa-3bf87734ccf2");
Vertex v_choise = new Vertex().setName("v_choise").setId("05f37200-b943-11eb-b3fa-3bf87734ccf2");
Vertex v_exit = new Vertex().setName("v_exit").setId("04762b60-b944-11eb-b3fa-3bf87734ccf2");
Vertex v_control = new Vertex().setName("v_control").setId("5facb1c0-b944-11eb-b3fa-3bf87734ccf2");
Vertex v_esci = new Vertex().setName("v_esci").setId("812ff2d0-b944-11eb-b3fa-3bf87734ccf2");
Vertex v_decrementChance = new Vertex().setName("v_decrementChance").setId("c184e980-b944-11eb-b3fa-3bf87734ccf2");
Vertex v_changePosition = new Vertex().setName("v_changePosition").setId("0c71d200-b945-11eb-b3fa-3bf87734ccf2");
Vertex v_fineNotCert = new Vertex().setName("v_fineNotCert").setId("44798a30-b945-11eb-b3fa-3bf87734ccf2");
Vertex v_rightRisp = new Vertex().setName("v_rightRisp").setId("91807910-b945-11eb-b3fa-3bf87734ccf2");
Vertex v_wrongRisp = new Vertex().setName("v_wrongRisp").setId("9225e0d0-b945-11eb-b3fa-3bf87734ccf2");
Vertex v_changeCurrDepth = new Vertex().setName("v_changeCurrDepth").setId("b58d4b30-b945-11eb-b3fa-3bf87734ccf2");
Vertex v_controlRisp = new Vertex().setName("v_controlRisp").setId("f48e2390-b945-11eb-b3fa-3bf87734ccf2");


    Model model = new Model();
    model.addEdge( new Edge().setSourceVertex(v_reset).setTargetVertex(v_started).setName("e_startTest").setId("e29b0650-b943-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_reset).setTargetVertex(v_exit).setName("e_exitTest").setId("10d3c200-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_exit).setTargetVertex(v_reset).setName("e_rifai").setId("1a2c7d10-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_started).setTargetVertex(v_choise).setName("e_richiestaPos").setId("55410010-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_started).setTargetVertex(v_control).setName("e_controlRisp").setId("6660b2a0-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_choise).setTargetVertex(v_started).setName("e_posScelta").setId("71012ff0-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_choise).setTargetVertex(v_esci).setName("e_esciChoice").setId("8a152b90-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_esci).setTargetVertex(v_exit).setName("e_exitFromTest").setId("94386290-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_control).setTargetVertex(v_decrementChance).setName("e_wrongAnswerMaxDepth").setId("cc8476c0-b944-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_decrementChance).setTargetVertex(v_changePosition).setName("e_changePos").setId("18a5c090-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_changePosition).setTargetVertex(v_choise).setName("e_newChoiseFromChance").setId("21174ff0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_control).setTargetVertex(v_fineNotCert).setName("e_stopOrChoiceNull").setId("609ce8b0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_fineNotCert).setTargetVertex(v_exit).setName("e_NewEdge").setId("7f30f910-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_control).setTargetVertex(v_rightRisp).setName("e_right").setId("9f654bf0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_control).setTargetVertex(v_wrongRisp).setName("e_wrong").setId("a05948e0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_rightRisp).setTargetVertex(v_changeCurrDepth).setName("e_changeCurrRight").setId("e20bfad0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_wrongRisp).setTargetVertex(v_changeCurrDepth).setName("e_changeCurrWrong").setId("e3305690-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_changeCurrDepth).setTargetVertex(v_controlRisp).setName("e_changeToControlRisp").setId("f99007a0-b945-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_controlRisp).setTargetVertex(v_exit).setName("e_fineCert").setId("300b0550-b946-11eb-b3fa-3bf87734ccf2"));
model.addEdge( new Edge().setSourceVertex(v_controlRisp).setTargetVertex(v_changePosition).setName("e_changePosFromRightWrong").setId("4980d000-b946-11eb-b3fa-3bf87734ccf2"));


    Context context = new ModelTestContext();
    context.setModel(model.build()).setPathGenerator(new RandomPath(new EdgeCoverage(100)));
    context.setNextElement(context.getModel().findElements("v_reset").get(0));

    Machine machine = new SimpleMachine(context);
    while (machine.hasNextStep()) {
      machine.getNextStep();
      System.out.println(context.getCurrentElement().getName());
    }
  }
}
