package mysite02;

import com.bitacademy.mysite.dao.UserDao;
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start");
		System.out.println(UpdateTest());

	}

}
