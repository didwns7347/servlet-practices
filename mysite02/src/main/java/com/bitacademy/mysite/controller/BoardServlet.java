package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.WebUtil;


public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		System.out.println(action);
		if("writeform".equals(action)) {
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
		
		}
		else if("view".equals(action)) {
			WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
		
		}
		else if("add".equals(action)) {
			HttpSession session = request.getSession();
			UserVo uvo=(UserVo)session.getAttribute("authUser");
			BoardVo vo= new BoardVo();
			vo.setTitle(request.getParameter("title"));
			vo.setContents(request.getParameter("content"));
			vo.setWriter(uvo.getName());
			vo.setG_no(Long.parseLong(request.getParameter("g_no")));
			vo.setDepth(Integer.parseInt(request.getParameter("depth")));
			
			new BoardDao().insert(vo);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		
		}
		else {
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
