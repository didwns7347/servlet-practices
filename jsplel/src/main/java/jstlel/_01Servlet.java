package jstlel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/01")
public class _01Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int ival=10;
		long lval=10;
		float fval=3.14f;
		boolean bval=true;
		String sval = "가나다라마바사";
		Map<String , Object> map = new HashMap<>();
		map.put("ival", ival);
		map.put("fval", fval);
		map.put("sval", sval);
		map.put("bval", bval);
		request.setAttribute("ival", ival);
		request.setAttribute("lval", lval);
		request.setAttribute("fval", fval);
		request.setAttribute("bval", bval);
		request.setAttribute("sval", sval);
		UserVo userVo= new UserVo();
		userVo.setNo(10L);
		userVo.setName("양준수");
		request.setAttribute("vo", userVo);
		request.setAttribute("map", map);
		Object obj = null;
		request.setAttribute("obj", obj);
		request.getRequestDispatcher("/WEB-INF/views/01.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
