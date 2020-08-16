package epam.andrew.gameShop.web.servlet;

import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.service.GameService;
import epam.andrew.gameShop.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GameImageServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(GameImageServlet.class);
    private static final int DEFAULT_SIZE = 1024;
    private static final String GAME_ID_IMAGE_LOADED = "Game (id = {}) image loaded. {}";
    private static final String CANNOT_LOAD_IMAGE = "Cannot load image";
    private static final String IMAGES = "images";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String pathInfo = req.getPathInfo();
        final byte[] buffer = new byte[DEFAULT_SIZE];
        int length;
        String imagePath;
        try (ServletOutputStream sos = resp.getOutputStream()) {
            GameService gameService = new GameService();
            String gameId = pathInfo.substring(1);
            Image gameImage = gameService.getGamePreviewImage(gameId);

            if (gameImage.getPath().startsWith(IMAGES)) {
                imagePath = gameImage.getPath();
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
                while ((length = inputStream.read(buffer)) != -1) {
                    sos.write(buffer, 0, length);
                }
            } else {
                imagePath = gameImage.getPath() + "/img" +
                        gameImage.getName();
                try (FileInputStream content = new FileInputStream(imagePath)) {
                    while ((length = content.read(buffer)) != -1) {
                        sos.write(buffer, 0, length);
                    }
                }
            }

            LOG.debug(GAME_ID_IMAGE_LOADED, gameId, gameImage);
        } catch (ServiceException | IOException e) {
            throw new ServletException(CANNOT_LOAD_IMAGE, e);
        }

    }
}
