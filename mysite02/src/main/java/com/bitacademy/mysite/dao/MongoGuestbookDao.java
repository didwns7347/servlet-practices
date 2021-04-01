package com.bitacademy.mysite.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.bitacademy.mysite.vo.GuestbookVo;
import com.bitacademy.mysite.vo.UserVo;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
public class MongoGuestbookDao {
	MongoDatabase db=null;
	//연결 초기화
    public MongoDatabase init() {
        final String combineUrl = "mongodb://localhost:27017";
        ConnectionString connString = new ConnectionString(combineUrl);		    
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .retryWrites(true)
            .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("webdb");
    }
    
    public boolean insert(GuestbookVo vo) {
    	db= this.init();
    	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    	Date time = new Date();
        MongoCollection<Document> collection = db.getCollection("guestbook");
        long no=this.getNextNo();
        Document doc= new Document("no",no).append("name", vo.getName()).append("content", vo.getContents())
        		.append("password", vo.getPassword()).append("reg_date",format1.format(time) );
        collection.insertOne(doc);
        return true;
    }
    public long getNextNo() {
    	db=this.init();
    	long res=0;
    	MongoCollection<Document> collection = db.getCollection("guestbook");
    	MongoCursor<Document> it =collection.find().iterator();
    	while(it.hasNext()) {
    		Document doc=it.next();
    		//System.out.println(doc.get("no").getClass().getName());
    		long no =doc.getLong("no");
    		if(res<no) {
    			res=no;
    		}
    	}
    	return res+1;
    }
    public boolean delete(String pw, long no) {
    	db=this.init();
    	MongoCollection<Document> collection = db.getCollection("guestbook");
    	collection.deleteOne(and(eq("password", pw),eq("no",no)));
    	return true;
    }
    public List<GuestbookVo> findAll(){
    	db=this.init();
    	MongoCollection<Document> collection = db.getCollection("guestbook");
    	List<GuestbookVo> list = new ArrayList<>();
    	MongoCursor<Document> it =collection.find().iterator();
    	while(it.hasNext()) {
    		Document doc=it.next();
    		//System.out.println(doc.get("no").getClass().getName());
    		GuestbookVo vo =new GuestbookVo();
    		vo.setContents(doc.getString("content"));
    		vo.setDate(doc.getString("reg_date"));
    		vo.setName(doc.getString("name"));
    		vo.setNo(doc.getLong("no"));
    		list.add(vo);
    	}
    	return list;
    	
    }

}
