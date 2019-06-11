package hr.fer.zemris.java.p12.servlets.voting;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;
import hr.fer.zemris.java.p12.servlets.util.VotingUtility;

/**
 * Servlet implementation class GlasanjeRezultatiServlet that reads the results
 * of the voting for poll options and stores results and winners in attributes
 * as a list.
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long pollId = VotingUtility.getCurrentPollId(request, response);
		if(pollId == -1) {
			return;
		}

		List<PollOption> pollOptions = DAOProvider.getDao().getAllPollOptions(pollId);

		request.setAttribute("results", pollOptions);

		// Option title and link
		Map<String, String> winners = new HashMap<>();

		long winnerVoteCount = -1;
		for (PollOption pollOption : pollOptions) {
			if (winnerVoteCount == -1) {
				winnerVoteCount = pollOption.getVotesCount();
				winners.put(pollOption.getOptionTitle(), pollOption.getOptionLink());
				continue;
			}

			// Are there multiple winners? If not, stop.
			if (pollOption.getVotesCount() != winnerVoteCount) {
				break;
			}

			winners.put(pollOption.getOptionTitle(), pollOption.getOptionLink());
		}

		request.setAttribute("winners", winners);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

}
