package conference.conference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.bson.Document;

import com.mongodb.client.MongoCursor;



public class Client {
	
	private static String name = "";
	final static DataBase db = new DataBase();
	public static void main(String[] args){
		try {
			
			ChatWindow window = new ChatWindow();
			JButton jbnButtonEnter=new JButton("Enter");//產生Jbutton
			JButton jbnButtonEnterName=new JButton("Enter");
			JTextField jtfInput=new JTextField(30);
			final JTextField nameInput=new JTextField(10);
			JTextArea textArea = new JTextArea(19,30);
			JTextArea onlinePeople = new JTextArea(30,6);
			final JTextField historicalMsg = new JTextField(10);
			JButton jbnButtonEnterDate=new JButton("Enter");
			JPanel jplNorthPanel = new JPanel();
			JPanel jplSouthPanel = new JPanel();
			JPanel jplCenterPanel = new JPanel();
			JPanel jplWestPanel = new JPanel();
			JPanel jplEastPanel = new JPanel();
			
			jplNorthPanel.setBorder(new TitledBorder("Enter your name:"));
			jplWestPanel.setBorder(new TitledBorder("online"));
			jplCenterPanel.setBorder(new TitledBorder("message"));
			jplSouthPanel.setBorder(new TitledBorder("Input message"));
			jplEastPanel.setBorder(new TitledBorder("historical Message"));
			
			textArea.setForeground(Color.blue);  
			jplSouthPanel.add(jtfInput);//將Input加進去Panel
			jplSouthPanel.add(jbnButtonEnter);//將button加進去panel
			jplCenterPanel.add(textArea);
			jplWestPanel.add(onlinePeople);
			jplNorthPanel.add(nameInput);
			jplNorthPanel.add(jbnButtonEnterName);
			jplEastPanel.add(historicalMsg);
			jplEastPanel.add(jbnButtonEnterDate);
			JScrollPane centerPane = new JScrollPane(jplCenterPanel);
			
			centerPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		        public void adjustmentValueChanged(AdjustmentEvent e) {  
		            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		        }
		    });
			
			window.getContentPane().add(jplNorthPanel, BorderLayout.NORTH);
			window.getContentPane().add(jplSouthPanel, BorderLayout.SOUTH);
			window.getContentPane().add(centerPane, BorderLayout.CENTER);
			window.getContentPane().add(jplWestPanel, BorderLayout.WEST);
			window.getContentPane().add(jplEastPanel, BorderLayout.EAST);
			window.setVisible(true);
			
			ArrayList<String> onlineList = new ArrayList<String>();
			
			
			//System.out.println("Enter your name");
			//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//String name = br.readLine();
			
			
			jbnButtonEnterDate.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String timeDate = historicalMsg.getText();
					System.out.println(timeDate);
					MongoCursor<Document> cursor = db.findOneDateMsg(timeDate);
					
					HistoryMsgWindow histMsgWindow = new HistoryMsgWindow();
					while(cursor.hasNext()){
						Document document = cursor.next();
						String name = document.getString("name");
						String content = document.getString("content");
						String msg = name + ":  " + content + "\n";
						System.out.println(msg);
						histMsgWindow.getTextArea().append(msg);
			        }
					
				}
				
			});
			jbnButtonEnterName.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					name = nameInput.getText();
					System.out.println(name);
					
					
				}
			});
			
			
			while(name.equals("")){
				System.out.println("No name");
			}
			
			
			
			Socket client = new Socket("localhost",8888);
			new Thread(new Send(client,name,jtfInput,jbnButtonEnter,window)).start();
			new Thread(new Receive(client,textArea,onlineList,onlinePeople)).start();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

