package conference.conference;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Server {
	private static List<Mychannel> all = new ArrayList<Mychannel>();
	public static void main(String[] args){
		try {
			ServerSocket server = new ServerSocket(8888);
			
			while(true){
				Socket client = server.accept();
				System.out.println("one client");
				Mychannel mychannel = new Mychannel(client);
				all.add(mychannel);
				new Thread(mychannel).start();
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//One client one thread
	private static class Mychannel implements Runnable{
		private DataBase db;
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;
		private String name;
		public Mychannel(Socket client){
			try {
				db = new DataBase();
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				this.name = dis.readUTF();
				System.out.println(this.name);
				send("welcome to the chatroom");
				//要將已經存在的會員傳回去
				for(Mychannel channel : all){
					send("*****"+channel.name);
				}
				//將自己的名字傳回
				send("*****"+this.name);
				//將自己的名字傳給其他人
				sendOthers("#####"+this.name);
						
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				isRunning= false;
				all.remove(this);
				
			}
		}
		public String getName(){
			return this.name;
		}
		private String receive(){
			String msg = "";
			try {
				msg = dis.readUTF();
				db.insertOneMsg(getName(), msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isRunning= false;
				all.remove(this);
			}
			return msg;
		}	
		private void send(String msg){
			if(msg == null || msg.equals("")){
				return;
			}
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				isRunning= false;
				all.remove(this);
			}
			
		}
		private void sendOthers(String msg){
			if(msg == null || msg.equals("")){
				return;
			}
			
			for(Mychannel other : all){
				if(other == this){
					continue;
				}
				other.send(msg);
			}
		}
		public void run() {
			
			while(isRunning){
				
				System.out.println("is running");
				String rec = receive();
				//send("Welcome: " + receive());
				send(this.name + " :   " + rec);
				sendOthers(this.name + " :  " + rec);
				
			}
		}
		
	}
	
	
}