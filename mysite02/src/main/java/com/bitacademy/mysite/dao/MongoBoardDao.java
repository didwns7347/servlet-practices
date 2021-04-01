package com.bitacademy.mysite.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import com.bitacademy.mysite.vo.BoardVo;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoBoardDao {
	MongoDatabase db = null;

	public MongoDatabase init() {
		final String combineUrl = "mongodb://localhost:27017";
		ConnectionString connString = new ConnectionString(combineUrl);
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		return mongoClient.getDatabase("webdb");
	}

	public boolean newinsert(BoardVo vo) {
		db = init();
		MongoCollection<Document> collection = db.getCollection("board");
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    	Date time = new Date();
		long no = this.getNextNo();
		Document doc = new Document("no", no)
				.append("title", vo.getTitle()).append("writer", vo.getTitle())
				.append("g_no", vo.getG_no()).append("depth", vo.getDepth())
				.append("gorder", vo.getGorder()).append("parent", vo.getParent())
				.append("content",vo.getContents()).append("reg_date",format1.format(time));
		collection.insertOne(doc);
		return true;

	}

	public long getNextNo() {
		db = this.init();
		long res = 0;
		MongoCollection<Document> collection = db.getCollection("board");
		MongoCursor<Document> it = collection.find().iterator();
		while (it.hasNext()) {
			Document doc = it.next();

			long no = doc.getLong("no");
			if (res < no) {
				res = no;
			}
		}
		return res + 1;
	}
	

	public BoardVo findByNo(long no) {
		db=this.init();
    	MongoCollection<Document> collection = db.getCollection("board");
    	BoardVo vo =null;
    	Document doc= collection.find(eq("no",no)).first();
    	//System.out.println(doc.toJson());
    	if(doc==null) {
    		return vo;
    	}
    	vo=new BoardVo();
    	vo.setTitle(doc.getString("title"));
    	vo.setContents(doc.getString("content"));
    	vo.setG_no(doc.getLong("g_no"));
    	vo.setDepth(doc.getInteger("depth"));
    	vo.setNo(doc.getLong("no"));
    	vo.setGorder(doc.getLong("gorder"));
    	return vo;
	}

	

	public long getGorderByP(long parent) {
		long res=0;
		db=this.init();
    	MongoCollection<Document> collection = db.getCollection("board");
    	MongoCursor<Document> it = collection.find(eq("parent",parent)).iterator();
    	while(it.hasNext()) {
    		Document doc = it.next();
    		long no = doc.getLong("gorder");
    		if(no>res)
    			res=no;
    	}
		return res;
	}

	public void deleteV2(long no) {
		// TODO Auto-generated method stub
		db=this.init();
    	MongoCollection<Document> collection = db.getCollection("board");
    	collection.updateOne(eq("no",no),set("title",new BoardVo().getDel()));
    	collection.updateOne(eq("no",no),set("content",""));
    
	}
	public List<BoardVo> findPage(int page){
		db=this.init();
    	MongoCollection<Document> collection = db.getCollection("board");
    	List<BoardVo> list = new ArrayList<>();
    	Document order=new Document("g_no",-1).append("gorder", 1);
    	MongoCursor<Document> it = collection.find().sort(order).skip((page-1)*10).limit(10).iterator();
    	while(it.hasNext()) {
    		BoardVo vo=new BoardVo();
    		Document doc=it.next();
    		vo.setTitle(doc.getString("title"));
    		vo.setNo(doc.getLong("no"));
    		vo.setContents(doc.getString("content"));
    		vo.setDate(doc.getString("reg_date"));
    		vo.setDepth(doc.getInteger("depth"));
    		vo.setG_no(doc.getLong("g_no"));
    		list.add(vo);
    	}
    	return list;
    	
	}
	public boolean before(long gno, long gorder) {
		db=init();
		MongoCollection<Document> collection = db.getCollection("board");
		collection.updateMany(and(eq("g_no",gno),gte("gorder",gorder)), inc("gorder",1));
    	return true;
	}
	public boolean reinsert(BoardVo vo) {
		// TODO Auto-generated method stub
		db=init();
		MongoCollection<Document> collection = db.getCollection("board");
		this.before(vo.getG_no(),vo.getGorder());
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
    	Date time = new Date();
		long no = this.getNextNo();
		Document doc = new Document("no", no).append("title", vo.getTitle()).append("writer", vo.getTitle())
				.append("g_no", vo.getG_no()).append("depth", vo.getDepth()).append("gorder", vo.getGorder())
				.append("parent", vo.getParent()).append("reg_date",format1.format(time));
		collection.insertOne(doc);
		return true;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		int cnt=0;
		db=init();
		MongoCollection<Document> collection = db.getCollection("board");
		MongoCursor<Document> it=collection.find().iterator();
		while(it.hasNext()) {
			cnt++;
			it.next();
		}
			
		return cnt;
	}
}
