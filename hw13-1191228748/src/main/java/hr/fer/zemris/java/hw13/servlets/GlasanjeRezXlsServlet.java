package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.utils.Band;

/**
 * Servlet implementation class GlasanjeRezultatiServlet
 */
@WebServlet("/glasanje-xls")
public class GlasanjeRezXlsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Name of the file of the results. */
	private final String fileName = "results.xls";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("results");

			Map<Band, Integer> results = Band.readResultsWithBands(request);

			HSSFRow headRow = sheet.createRow(0);
			headRow.createCell(0).setCellValue("Band");
			headRow.createCell(1).setCellValue("Votes count");

			int i = 1;
			for (Map.Entry<Band, Integer> res : results.entrySet()) {
				HSSFRow row = sheet.createRow(i++);
				row.createCell(0).setCellValue(res.getKey().getName());
				row.createCell(1).setCellValue(res.getValue());

			}

			hwb.write(response.getOutputStream());
			hwb.close();

		} catch (Exception ex) {
		}
	}
}
