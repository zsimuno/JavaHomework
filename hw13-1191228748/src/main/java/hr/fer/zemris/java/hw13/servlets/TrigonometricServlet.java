package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.utils.TrigonometricValue;

/**
 * Servlet implementation class TrigonometricServlet that gets two given angles
 * and produces a list of cos and sin values of integer angles between the two
 * given ones. (if a is missing then a=0 ; if b is missing then b=360 ; if a > b
 * , swaps them; if b > a+720 , sets b to a+720 )
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int a = 0, b = 360;

		try {
			String param = request.getParameter("a");
			if (param != null) {
				a = Integer.valueOf(param);
			}
		} catch (Exception e) {
		}

		try {
			String param = request.getParameter("b");
			if (param != null) {
				b = Integer.valueOf(param);
			}
		} catch (Exception e) {
		}

		if (a > b) {
			int temp = b;
			b = a;
			a = temp;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		List<TrigonometricValue> values = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			values.add(new TrigonometricValue(i));
		}

		request.setAttribute("trigValues", values);

		request.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(request, response);
	}

}
