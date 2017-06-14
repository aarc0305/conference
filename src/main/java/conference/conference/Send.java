package conference.conference;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTextField;



/*
 * 發送訊息
 * 
 * */
public class Send implements Runnable{
	
	private BufferedReader console;
	private DataOutputStream dos;
	private boolean isRunning = true;
	private String name;
	
	private JButton jbnButtonEnter;
	private JTextField jtfInput;
	private ChatWindow window;
	public Send(){
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	public Send(Socket client, String name,JTextField jtfInput,JButton jbnButtonEnter, ChatWindow window){
		this();
		try {
			this.jbnButtonEnter = jbnButtonEnter;
			this.jtfInput = jtfInput;
			this.window = window;
			dos = new DataOutputStream(client.getOutputStream());
			this.name=name;
			sendName(this.name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isRunning = false;
		}
	}
	public void send(String msg){
		try {
			
			
			if(msg!=null && !msg.equals("")){
				dos.writeUTF(msg);
				dos.flush();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendName(String name){
		try {
			dos.writeUTF(name);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run() {
		// TODO Auto-generated method stub
		/*while(true){
			String msg;
			try {
				this.jbnButtonEnter.addActionListener(new Listener());
				msg = console.readLine();
				send(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		this.jbnButtonEnter.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String input = jtfInput.getText();
				send(input);
				
		}});
		
	}
	
}
