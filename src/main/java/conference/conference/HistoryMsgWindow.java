package conference.conference;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class HistoryMsgWindow extends JFrame{
	JTextArea textArea;
	JPanel jplCenterPanel;
	JScrollPane centerPane;
	public JTextArea getTextArea(){
		return this.textArea;
	}
	public HistoryMsgWindow(){
		setTitle("History Message");
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		textArea = new JTextArea(20,40);
		jplCenterPanel = new JPanel();
		jplCenterPanel.add(textArea);
		centerPane = new JScrollPane(jplCenterPanel);
		
		centerPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    });
		jplCenterPanel.setBorder(new TitledBorder("message"));
		this.getContentPane().add(centerPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	public static void main(String[] args){
		HistoryMsgWindow window = new HistoryMsgWindow();
	}

}
