package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

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
			HttpSession session = request.getSession();
			UserVo authUser=(UserVo)session.getAttribute("authUser");
			if(authUser==null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
		
		}
		else if("view".equals(action)) {
			WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
		
		}
		else if("add".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser=(UserVo)session.getAttribute("authUser");
			if(authUser==null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}

		
			BoardVo vo= new BoardVo();
			long no=authUser.getNo();
			String title=request.getParameter("title");
			String content=request.getParameter("content");
			String writer=authUser.getName();
			long gno = Long.parseLong(request.getParameter("g_no"));
			int depth=Integer.parseInt(request.getParameter("depth"));
			
			//request.getAttributeNames().toString()
			vo.setTitle(title);
			vo.setContents(content);
			vo.setWriter(writer);
			vo.setG_no(no);
			vo.setG_no(gno);
			vo.setDepth(depth);
			System.out.println(vo.toString());
			new BoardDao().insert(vo);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		
		}
		else {
			List<BoardVo> list = new BoardDao().findAll();
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/index.jsp", request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
