package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.dao.MongoBoardDao;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
import com.bitacademy.web.mvc.WebUtil;

/**
 * Servlet implementation class MongoBoardServlet
 */
public class MongoBoardServlet extends HttpServlet {
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
			BoardVo vo = new MongoBoardDao().findByNo(Long.parseLong(no));
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
			long gno = new MongoBoardDao().getNextNo();
			int depth = Integer.parseInt(request.getParameter("depth"));

			// request.getAttributeNames().toString()
			if (depth == 0) {
				vo.setTitle(title);
				vo.setContents(content);
				vo.setWriter(writer);
				vo.setG_no(gno);
				vo.setGorder(0);
				vo.setDepth(depth);
				vo.setParent(0l);

				new MongoBoardDao().newinsert(vo);

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
			String gorder= request.getParameter("gorder");
			System.out.println("gno"+gno+"depth"+depth+"gorder"+gorder);
			request.setAttribute("g_no", gno);
			request.setAttribute("gorder", gorder);
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
			//System.out.println("여기ㄱ까지 됨"+request.getParameter("gorder"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String writer = authUser.getName();
			long gno = Long.parseLong(request.getParameter("g_no"));
			int depth = Integer.parseInt(request.getParameter("depth")) + 1;
			long gorder = Long.parseLong(request.getParameter("gorder"))+1l;
			long parent = Long.parseLong(request.getParameter("parent"));
			//System.out.println("여기ㄱ까지 됨"+request.getParameter("gorder"));
			//System.out.println("GOREDER="+gorder);
			long child = new MongoBoardDao().getGorderByP(parent)+1;
			gorder = Math.max(gorder, child);
			// request.getAttributeNames().toString()
			//System.out.println("GOREDER="+gorder);
			vo.setTitle(title);
			vo.setContents(content);
			vo.setWriter(writer);
			vo.setG_no(gno);
			vo.setDepth(depth);
			vo.setGorder(gorder);
			vo.setParent(parent);
			//System.out.println("vo=>"+vo.toString());
			if(new MongoBoardDao().reinsert(vo)) {
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
			new MongoBoardDao().deleteV2(Long.parseLong(no));
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
		}

		else {
			int page;
			int idx;
			int total = new MongoBoardDao().getCount();
			int lastidx=(total%50==0)? total/50+1:total/50+2;
			if(request.getParameter("page")==null) {
				page=1;
			}
			else {
				page=Integer.parseInt(request.getParameter("page"));
			}
			if(request.getParameter("index")==null) {
				idx=1;
			}
			else {
				idx=Integer.parseInt(request.getParameter("index"));
				System.out.println(idx);
				if(idx<1)
					idx=1;
				else if(idx>lastidx)
					idx=lastidx;
			}
			if(request.getParameter("go")!=null && "u".equals(request.getParameter("go"))) {
				if(page%5==0)
					page++;
				else {
					int tmp=(page+5)/5;
					page=page*tmp+1;
				}
				if(page>total) {
					page=total;
				}
			}
			if(request.getParameter("go")!=null && "d".equals(request.getParameter("go"))) {
				
				
				if(page-5%5==0) {
					page=page-5;
				}
				else {
					int tmp=(page-5)/5+1;
					page=tmp*5;
				}
				
				if(page>total) {
					page=1;
				}
			}
			
			
			List<BoardVo> list = new MongoBoardDao().findPage(page-1);
			
			int totalpage=(total%10==0)? total/10:total/10+1;
			request.setAttribute("index",idx);
			request.setAttribute("list", list);
			request.setAttribute("page", page);
			request.setAttribute("total", total);
			request.setAttribute("totalpage", totalpage);
			request.setAttribute("lastidx", lastidx);
			WebUtil.forward("/WEB-INF/views/board/index.jsp",  request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
