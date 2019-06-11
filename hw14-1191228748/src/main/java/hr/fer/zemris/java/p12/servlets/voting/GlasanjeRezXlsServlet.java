package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * Servlet that shows the results of the voting in an excel table.
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeRezXlsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Name of the file of the results. */
	private final String fileName = "results.xls";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		long pollId = 0; // TODO kako primiti?
		List<PollOption> pollOptions = DAOProvider.getDao().getAllPollOptions(pollId); // TODO exception?

		try {
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("results");

			HSSFRow headRow = sheet.createRow(0);
			headRow.createCell(0).setCellValue("Option title");
			headRow.createCell(1).setCellValue("Votes count");

			int i = 1;
			for (PollOption option : pollOptions) {
				HSSFRow row = sheet.createRow(i++);
				row.createCell(0).setCellValue(option.getOptionTitle());
				row.createCell(1).setCellValue(option.getVotesCount());
			}

			hwb.write(response.getOutputStream());
			hwb.close();

		} catch (Exception ex) {
		}
	}
}
