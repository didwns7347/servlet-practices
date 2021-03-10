package com.bitacademy.guestbookdao.test;

import java.util.List;

import com.bitacademy.guestbook.dao.GuestbookDao;
import com.bitacademy.guestbook.vo.GuestbookVo;


public class GuestbookDaoTest {
	public static void testFindAll() {
		List<GuestbookVo> list = new GuestbookDao().findAll();
		for(GuestbookVo vo  : list) {
			System.out.println(vo.getName());
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testFindAll();
	}

}
