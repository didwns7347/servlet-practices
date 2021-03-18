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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");
		System.out.println(action);
		if ("writeform".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);

		} else if ("view".equals(action)) {
			String no = request.getParameter("no");
			BoardVo vo = new BoardDao().findByNo(Integer.parseInt(no));
			request.setAttribute("vo", vo);
			WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);

		} else if ("add".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}

			BoardVo vo = new BoardVo();

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = authUser.getName();
			long gno = new BoardDao().getAuto();
			int depth = Integer.parseInt(request.getParameter("depth"));

			// request.getAttributeNames().toString()
			if (depth == 0) {
				vo.setTitle(title);
				vo.setContents(content);
				vo.setWriter(writer);
				vo.setG_no(gno + 1);
				vo.setGorder(0);
				vo.setDepth(depth);
				vo.setParent(0l);

				new BoardDao().newinsert(vo);

			} else {

			}
			WebUtil.redirect(request.getContextPath() + "/board", request, response);

		} else if ("readdform".equals(action)) {
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}
			String gno = request.getParameter("g_no");
			String depth = request.getParameter("depth");
			String parent = request.getParameter("parent");
			System.out.println("gno"+gno+"depth"+depth);
			request.setAttribute("g_no", gno);
			request.setAttribute("depth", depth);
			request.setAttribute("parent", parent);
			WebUtil.forward("/WEB-INF/views/board/rewrite.jsp", request, response);

		} else if ("readd".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}

			BoardVo vo = new BoardVo();

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = authUser.getName();
			long gno = Long.parseLong(request.getParameter("g_no"));
			int depth = Integer.parseInt(request.getParameter("depth")) + 1;
			long parent = Long.parseLong(request.getParameter("parent"));
			long gorder=new BoardDao().getGorderRe(parent);
			gorder=Math.max(gorder, new BoardDao().getGorderByP(parent)+1);
			// request.getAttributeNames().toString()
			
			vo.setTitle(title);
			vo.setContents(content);
			vo.setWriter(writer);
			vo.setG_no(gno);
			vo.setDepth(depth);
			vo.setGorder(gorder+1);
			vo.setParent(parent);
			System.out.println("vo=>"+vo.toString());
			if(new BoardDao().reinsert(vo)) {
				WebUtil.redirect(request.getContextPath() + "/board", request, response);
				System.out.println("faill");
			}
			else {
				System.out.println("asdf asdf");
			}
		}else if("deleteform".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			if (authUser == null) {
				WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
				return;
			}
			request.setAttribute("no", request.getParameter("no"));
			WebUtil.forward("/WEB-INF/views/board/deleteform.jsp", request, response);
		}else if("delete".equals(action)) {
			String no =request.getParameter("no");
			new BoardDao().deleteV2(Long.parseLong(no));
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
		}

		else {
			String page;
			if(request.getParameter("page")==null) {
				page="0";
			}
			else {
				page=request.getParameter("page");
			}
			int total = new BoardDao().getCount();
			List<BoardVo> list = new BoardDao().findPage(Integer.parseInt(page));
			int fin=0;
			if(total%10==0)
				fin=total/10;
			else {
				fin=total/10+1;
			}
			request.setAttribute("list", list);
			WebUtil.forward("/WEB-INF/views/board/index.jsp?page="+page+"&total="+total+"&fin="+fin, request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
