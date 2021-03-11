package com.bitacademy.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.guestbook.dao.GuestbookDao;
import com.bitacademy.guestbook.vo.GuestbookVo;
import com.bitacademy.web.mvc.WebUtil;


public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		System.out.println(action);
		if("delete".equals(action)) {
			String str=request.getParameter("no");
			long num=-1l;
			if(str!=null && str.matches("\\d*"))
			{
				num=Long.parseLong(str);
			}
			request.setAttribute("num", num);
			WebUtil.forward("/WEB-INF/views/deleteform.jsp",request,response);
		}
		else if("add".equals(action)) {
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String contents = request.getParameter("contents");

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(contents);
			new GuestbookDao().insert(vo);
			//response.sendRedirect("/guestbook01/index.jsp");
			//System.out.println("asdf여기까지는됨 37라인");
			WebUtil.redirect(request.getContextPath()+"/gb", request, response);
		}
		else if("deleteform".equals(action)) {
			String str=request.getParameter("no");
			String pw=request.getParameter("password");
			long no=Long.parseLong(str);
			new GuestbookDao().delete(pw,no);
			WebUtil.redirect(request.getContextPath()+"/gb", request, response);
		}
		else {
			List<GuestbookVo> list = new GuestbookDao().findAll();
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/index.jsp", request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
