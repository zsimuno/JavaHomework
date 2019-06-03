package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet implementation class PowersExcelServlet that accepts three parameters
 * (a, b and n) and returns to the user appropriate excel document (or error
 * message if needed).
 * <p>
 * Given excel document format is:
 * </p>
 * <p>
 * Document with {@code n} pages. On page {@code i} there must be a table with
 * two columns. The first column should contain integer numbers from {@code a}
 * to {@code b}. The second column should contain {@code i}-th powers of these
 * numbers.
 * </p>
 */
@WebServlet("/powers")
public class PowersExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** File name of the excel table. */
	private final String fileName = "table.xls";

	/** Lower bound for a and b values. */
	private final int lowerABBound = -100;

	/** Upper bound for a and b values. */
	private final int upperABBound = 100;

	/** Lower bound for n value. */
	private final int lowerNBound = 1;

	/** Upper bound for n value. */
	private final int upperNBound = 5;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer a = null, b = null, n = null;
		try {
			a = Integer.parseInt(request.getParameter("a"));
			b = Integer.parseInt(request.getParameter("b"));
			n = Integer.parseInt(request.getParameter("n"));
		} catch (Exception e) {
			sendError("Invalid parameters.", request, response);
			return;
		}

		if (a < lowerABBound || a > upperABBound) {
			sendError("Parameter a is not within appropriate bounds [" + lowerABBound + ", " + upperABBound + "]",
					request, response);
			return;
		}

		if (b < lowerABBound || b > upperABBound) {
			sendError("Parameter b is not within appropriate bounds [" + lowerABBound + ", " + upperABBound + "]",
					request, response);
			return;
		}

		if (n < lowerNBound || n > upperNBound) {
			sendError("Parameter n is not within appropriate bounds [" + lowerNBound + ", " + upperNBound + "]",
					request, response);
			return;
		}

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();

			for (int i = 1; i <= n; i++) {
				HSSFSheet sheet = hwb.createSheet("sheet" + i);
				for (int j = a; j <= b; j++) {
					HSSFRow row = sheet.createRow(j - a);
					row.createCell(0).setCellValue(j);
					row.createCell(1).setCellValue(Math.pow(j, i));
				}

			}

			hwb.write(response.getOutputStream());
			hwb.close();

		} catch (Exception ex) {
			sendError("Can't generate the file!", request, response);
			return;
		}
	}

	/**
	 * Sets attribute error to given {@code message} and dispatches to
	 * powersError.jsp
	 * 
	 * @param message  error message
	 * @param request  an HttpServletRequest object that contains the request the
	 *                 client has made of the servlet
	 * @param response an HttpServletResponse object that contains the response the
	 *                 servlet sends to the client
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendError(String message, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("error", message);
		request.getRequestDispatcher("/WEB-INF/pages/powersError.jsp").forward(request, response);
	}

}
