package hr.fer.zemris.gallery.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet we use to display pictures.
 * 
 * @author Zvonimir Šimunović
 *
 */
@WebServlet("/servlets/pictures")
public class PicturesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fileName = request.getParameter("name");

		if (fileName == null) {
			// TODO sto?
		}

		String thumbnail = request.getParameter("thumbnail");
		response.setContentType("image/jpg");
		Path picturePath = null;

		if (thumbnail == null || !thumbnail.equals("yes")) {
			picturePath = Paths.get(request.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));
			if(!Files.exists(picturePath)) {
				// TODO what to do if no picture?
			}
			
		} else {
			picturePath = Paths.get(request.getServletContext().getRealPath("/WEB-INF/thumbnails/" + fileName));
			
			if(!Files.exists(picturePath.getParent())) {
				Files.createDirectories(picturePath.getParent());
			}
			
			if(!Files.exists(picturePath)) {
				Path original = Paths.get(request.getServletContext().getRealPath("/WEB-INF/slike/" + fileName));
				BufferedImage originalImage = ImageIO.read(original.toFile());
				
				BufferedImage resizedImage = new BufferedImage(150, 150, originalImage.getType());
			    Graphics2D g = resizedImage.createGraphics();
			    g.drawImage(originalImage, 0, 0, 150, 150, null);
			    g.dispose();

				ImageIO.write(resizedImage, "jpg", picturePath.toFile());
			}
		}

		BufferedImage bim = ImageIO.read(picturePath.toFile());
		ImageIO.write(bim, "jpg", response.getOutputStream());

	}

}
