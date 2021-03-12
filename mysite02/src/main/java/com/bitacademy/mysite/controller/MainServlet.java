package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.web.mvc.WebUtil;


public class MainServlet extends HttpServlet {
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init() called");
		String configPath = getServletConfig().getInitParameter("config");
		System.out.println("init() called- "+configPath);
		super.init();
	}
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("service() called");
		super.service(req, resp);
	}


	


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy() called!!!!!!!!");
		super.destroy();
	}





	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet() called");
		int visitCount=0;
		//getServletContext().setAttribute(getServletName(), response);
		//쿠키 읽기
		Cookie[] cookies=request.getCookies();
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie:cookies) {
				if("visitCount".equals(cookie.getName())) {
					visitCount=Integer.parseInt(cookie.getValue());
				}
					
			}
		}
		System.out.println(visitCount);
		//쿠키 쓰기
		
		visitCount++;
		Cookie cookie=new Cookie("visitCount",String.valueOf(visitCount));
		cookie.setPath(request.getContextPath());
		cookie.setMaxAge(24*60*60); //1day
		
		response.addCookie(cookie);
		WebUtil.forward("/WEB-INF/views/main/index.jsp", request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
