package unibg.se4med;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectJoinStep;
import static se4med.jooq.tables.User.*;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Test {
	/** Frame di visualizzazione  */
    static JFrame frame=new JFrame("Freiburg Test");
	public static void main(String[] args) {
		FSTdatabase.IniziaConn();
		DSLContext db = FSTdatabase.DatabaseOperator();
		SelectJoinStep<Record1<String>> risultato=db.select(USER.EMAIL).from(USER);
		JTable jt=new JTable(0,0);
		DefaultTableModel model = (DefaultTableModel) jt.getModel();
		model.addColumn("selezione");
		for(Record1<String> r:risultato) {
			model.setRowCount(model.getRowCount()+1);
			model.setValueAt(r.component1(),model.getRowCount() - 1, 0);
		}

		frame.add(jt);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
