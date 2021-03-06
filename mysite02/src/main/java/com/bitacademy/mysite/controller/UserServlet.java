package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.WebUtil;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		System.out.println(action);
		if ("joinform".equals(action)) {
			WebUtil.forward("/WEB-INF/views/user/joinform.jsp", request, response);

		} else if ("joinsuccess".equals(action)) {

			WebUtil.forward("/WEB-INF/views/user/joinsuccess.jsp", request, response);
		} else if ("join".equals(action)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo();
			userVo.setName(name);
			userVo.setEmail(email);
			userVo.setPassword(password);
			userVo.setGender(gender);

			new UserDao().insert(userVo);
			WebUtil.redirect(request.getContextPath() + "/user?a=joinsuccess", request, response);

		} else if ("loginform".equals(action)) {
			WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
		} else if ("login".equals(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			UserVo vo = new UserVo();
			vo.setEmail(email);
			vo.setPassword(password);

			UserVo authUser = new UserDao().findByEmailAndPassword(vo);
			if (authUser == null) {
				request.setAttribute("authResult", "fail");
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}

			// ?????? ??????
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*60);
			session.setAttribute("authUser", authUser);

			// ??????
			WebUtil.redirect(request.getContextPath(), request, response);
		} else if ("logout".equals(action)) {
			HttpSession session = request.getSession();

			// ???????????? ??????
			if (session != null && session.getAttribute("authUser") != null) {
				session.removeAttribute("authUser");
				session.invalidate();
			}
			WebUtil.redirect(request.getContextPath(), request, response);
		} else if ("updateform".equals(action)) {
			// Access control
			HttpSession session = request.getSession();
			if (session == null) {
				WebUtil.redirect(request.getContextPath(), request, response);
				return;
			}
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.redirect(request.getContextPath(), request, response);
				return;
			}
			Long no = authUser.getNo();
			UserVo userVo = new UserDao().findByNo(no);
			request.setAttribute("userVo", userVo);
			System.out.println(userVo.toString());
			WebUtil.forward("/WEB-INF/views/user/updateform.jsp", request, response);
		} else if ("update".equals(action)) {
			String email = request.getParameter("email");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			String password = request.getParameter("password");
			UserVo vo = new UserVo();
			vo.setEmail(email);
			vo.setName(name);
			vo.setGender(gender);
			vo.setPassword(password);
			System.out.println(vo.toString());
			if (new UserDao().update(vo)) {
				WebUtil.redirect(request.getContextPath() + "/user?a=updatesuccess", request, response);
			}
			else {
				WebUtil.forward("/WEB-INF/views/user/updateform.jsp", request, response);
			}
		} else if ("updatesuccess".equals(action)) {
			WebUtil.forward("/WEB-INF/views/user/updatesuccess.jsp", request, response);
		}

		else {
			WebUtil.redirect(request.getContextPath(), request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
