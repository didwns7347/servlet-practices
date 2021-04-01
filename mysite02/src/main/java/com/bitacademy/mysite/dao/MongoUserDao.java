package com.bitacademy.mysite.dao;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
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
public class MongoUserDao {
	MongoDatabase db=null;
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
    

    public boolean insert(UserVo vo) {
    	db= this.init();
    	SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    	Date time = new Date();
        MongoCollection<Document> collection = db.getCollection("user");
        long no=this.getNextNo();
        Document doc= new Document("no",no).append("name", vo.getName()).append("email", vo.getEmail())
        		.append("password", vo.getPassword()).append("gender", vo.getGender()).append("join_date",format1.format(time) );
        collection.insertOne(doc);
        return true;
    }
    
    public long getNextNo() {
    	db=this.init();
    	long res=0;
    	MongoCollection<Document> collection = db.getCollection("user");
    	MongoCursor<Document> it =collection.find().iterator();
    	while(it.hasNext()) {
    		Document doc=it.next();
    		
    		long no =doc.getLong("no");
    		if(res<no) {
    			res=no;
    		}
    	}
    	return res+1;
    }
    
    public UserVo findByEmailAndPassword(UserVo invo) {
    	db=this.init();
    	UserVo vo =null;
    	MongoCollection<Document> collection = db.getCollection("user");
    	String email=invo.getEmail();
    	String pw = invo.getPassword();
    	Document doc= collection.find(and(eq("password",pw), eq("email",email))).first();
    	//System.out.println(doc.toJson());
    	if(doc==null) {
    		return vo;
    	}
    	vo=new UserVo();
    	
    	vo.setNo(doc.getLong("no"));
    	
    	vo.setName(doc.getString("name"));
    	
    	
    	return vo;
    }
    public UserVo findByNo(Long no) {
    	db=this.init();
    	UserVo vo =null;
    	MongoCollection<Document> collection = db.getCollection("user");
    	Document doc= collection.find(eq("no",no)).first();
    	//System.out.println(doc.toJson());
    	if(doc==null) {
    		return vo;
    	}
    	vo=new UserVo();
    	vo.setEmail(doc.getString("email"));
    	vo.setNo(doc.getLong("no"));
    	vo.setName(doc.getString("name"));
    	vo.setGender(doc.getString("gender"));
    	vo.setPassword(doc.getString("password"));
    	
    	
    	return vo;
    }
    public boolean update(UserVo vo) {
    	db=this.init();
    	MongoCollection<Document> collection = db.getCollection("user");
    	collection.updateOne(eq("email",vo.getEmail()),set("name",vo.getName()));
    	collection.updateOne(eq("email",vo.getEmail()),set("gender",vo.getGender()));
    	collection.updateOne(eq("email",vo.getEmail()),set("password",vo.getPassword()));
    	collection.updateOne(eq("email",vo.getEmail()),set("email",vo.getEmail()));
    	return true;
    	
    }
}