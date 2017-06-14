package conference.conference;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class DataBase {
	private MongoClient mongoClient;
	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> recordCollection;
	public DataBase(){
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		MongoDatabase mongoDatabase = mongoClient.getDatabase("conference");
		System.out.println("Connect to database successfully");
		try{
			mongoDatabase.createCollection("record");
			System.out.println("集合创建成功");
		}
		catch(Exception e){
			System.out.println("record collection already exists");
		}
		recordCollection = mongoDatabase.getCollection("record");
	}
	public void insertOneMsg(String name, String content){
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd");
		String timeString = ft.format(date);
		
		Document oneDocument = new Document("name", name).append("content", content).append("time", timeString);
		recordCollection.insertOne(oneDocument);
		
	}
	public MongoCursor<Document> findOneDateMsg(String timeString){
		BasicDBObject query = new BasicDBObject();  
		query.put("time", timeString);
		System.out.printf("\t[Info] Query=%s :\n", query);  
		FindIterable<Document> findIterable = recordCollection.find(query);  
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        /*while(mongoCursor.hasNext()){  
           System.out.println(mongoCursor.next());  
        }*/
        return mongoCursor;
        
	}
	
	public static void main(String[] args){
		DataBase db = new DataBase();
		db.insertOneMsg("jason","hello");
		db.insertOneMsg("mary","hi");
		db.findOneDateMsg("2017/06/11");
		
	}

}
