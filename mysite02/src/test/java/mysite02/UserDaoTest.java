package mysite02;

import com.bitacademy.mysite.dao.MongoUserDao;

import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.dao.MongoBoardDao;
import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;

public class UserDaoTest {
	public static boolean UpdateTest() {
		UserVo test= new UserVo();
		test.setName("김english");
		test.setEmail("english@eng.com");
		test.setPassword("1234");
		test.setGender("male");
		
		UserDao dao=new UserDao();
		return dao.update(test);
	}
	public static void MongoTest() {
		MongoUserDao db= new MongoUserDao();
		UserVo test= new UserVo();
		test.setEmail("master");
		test.setPassword("1");
		System.out.println(db.findByEmailAndPassword(test));
		test.setEmail("test1");
		test.setPassword("1");
		System.out.println(db.findByEmailAndPassword(test));
	}
	public static void MongoBoardTest() {
		MongoBoardDao db= new MongoBoardDao();
	
		for(BoardVo vo :db.findPage(1)) {
			System.out.println(vo.toString());
		}
	}
	public static void BoardDaoTest() {
		BoardDao dao = new BoardDao();
		List<BoardVo> arr= dao.findAll();
		for(BoardVo vo : arr) {
			System.out.println(vo.toString());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BoardDaoTest();
		

	}

}
