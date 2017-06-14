package conference.conference;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Receive implements Runnable{
	private DataInputStream dis;
	private JTextArea textArea;
	private boolean isRunning = true;
	private ArrayList<String> onlineList;
	private JTextArea onlinePeople;
	public Receive(){
		
	}
	
	public Receive(Socket client, JTextArea textArea, ArrayList<String> onlineList, JTextArea onlinePeople){
		try {
			dis = new DataInputStream(client.getInputStream());
			this.textArea = textArea;
			//this.textArea.setText("hello");
			this.onlineList = onlineList;
			this.onlinePeople = onlinePeople;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isRunning = false;
		}
	}
	
	public String receive(){
		String msg = "";
		try {
			msg = dis.readUTF();
			
			//加入新人
			if(msg.startsWith("#")){
				
				String newName = msg.substring(5);
				//System.out.println(newName);
				this.onlineList.add(newName);
				this.onlinePeople.append(newName + "\n");
				msg = newName+" come in";
			}
			//自己剛加入 要將已經存在的會員載入
			if(msg.startsWith("*")){
				
				String newName = msg.substring(5);
				this.onlineList.add(newName);
				this.onlinePeople.append(newName + "\n");
				return "no display";
			}
			
			//
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isRunning = false;
		}
		return msg;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning){
			//System.out.println(receive());
			String msg = receive();
			if(!(msg=="no display")){
				this.textArea.append(msg + "\n");
			}
			
		}
		
	}

}

