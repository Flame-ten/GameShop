package epam.andrew.gameShop.util;

import epam.andrew.gameShop.action.ActionException;
import epam.andrew.gameShop.entity.Game;
import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.validator.Validate;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Properties;

public class GameUtil {
    private static final Logger LOG = LoggerFactory.getLogger(GameUtil.class);
    private static final String INVALID_CONTENT_TYPE = "Invalid content type - {}";
    private static final String PROPERTIES = "Invalid content type - {}";
    private static final String IMAGE_PATH = "imagePath";
    private static final String RELATIVE_IMAGE_PATH = "relativeImagePath";

    private Validate validation = new Validate();
    private String name;
    private String genre;
    private String price;
    private String amount;
    private Part imagePart;

    public boolean validateMoney(HttpServletRequest req) throws IOException, ServletException, ActionException {
        setGameParams(req);
        boolean invalid = false;
        if (validation.checkMoney(req, price)) {
            req.setAttribute(Constant.ERROR_MONEY, Constant.TRUE);
            invalid = true;
        }
        return invalid;
    }

    private void setGameParams(HttpServletRequest req) {
        name = req.getParameter(Constant.NAME);
        genre = req.getParameter(Constant.GENRE_ID);
        price = req.getParameter(Constant.PRICE);
        amount = req.getParameter(Constant.AMOUNT);

    }

    public Game getFilledGame(HttpServletRequest req) {
        setGameParams(req);
        Game filledGame = new Game();
        filledGame.setName(name);
        filledGame.getGenre().setId(Integer.valueOf(req.getParameter(Constant.GENRE_ID)));
        ;
        filledGame.setPrice(Money.parse(Constant.KZT + price));
        filledGame.setAmount(0);
        return filledGame;
    }

    public Image getFilledImage(Image image, HttpServletRequest req)
            throws IOException, ServletException {

        Properties properties = new Properties();
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("database.properties");
        properties.load(is);

        final String path = properties.getProperty(IMAGE_PATH);
        final Part filePart = req.getPart(Constant.IMAGE);
        final String fileName = getFileName(filePart);
        OutputStream out = null;
        InputStream filecontent = null;


        try {
            File imagePath = new File(path);
            if (!imagePath.exists()) {
                imagePath.mkdirs();
            }

            File img = new File(path + File.separator
                    + fileName);

            out = new FileOutputStream(img);
            filecontent = filePart.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            System.out.println("Image not found");
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }

        image.setName(fileName);
        image.setPath(properties.getProperty(IMAGE_PATH));
        return image;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    public boolean validateImage(HttpServletRequest req) throws IOException, ServletException {
        boolean invalid = false;
        if (checkImagePart(req)) {
            req.setAttribute(Constant.ERROR_IMAGE, Constant.TRUE);
            LOG.error(INVALID_CONTENT_TYPE);
            invalid = true;
        }
        return invalid;
    }

    private boolean checkImagePart(HttpServletRequest req) throws IOException, ServletException {

        imagePart = req.getPart(Constant.IMAGE);
        if (imagePart.getSize() != 0) {
            return !imagePart.getContentType().startsWith(Constant.IMAGE);
        }
        LOG.info(INVALID_CONTENT_TYPE);
        return true;
    }
}
