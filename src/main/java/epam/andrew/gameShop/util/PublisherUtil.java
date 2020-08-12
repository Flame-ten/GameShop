package epam.andrew.gameShop.util;

import epam.andrew.gameShop.entity.Image;
import epam.andrew.gameShop.entity.Publisher;
import epam.andrew.gameShop.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Properties;

public class PublisherUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PublisherUtil.class);
    private static final String INVALID_CONTENT_TYPE = "Invalid content type - {}";
    private static final String PROPERTIES = "Invalid content type - {}";
    private static final String IMAGE_PATH = "imagePath";
    private static final String RELATIVE_IMAGE_PATH = "relativeImagePath";

    private Validate validation = new Validate();
    private String name;
    private String email;
    private String address;
    private String phone;
    private String country;
    private Part imagePart;


    private void setPublisherParams(HttpServletRequest req) {
        name = req.getParameter(Constant.NAME);
        email = req.getParameter(Constant.EMAIL);
        address = req.getParameter(Constant.ADDRESS);
        phone = req.getParameter(Constant.PHONE);
        country = req.getParameter(Constant.COUNTRY);

    }

    public Publisher getFilledPublisher(HttpServletRequest req) {
        setPublisherParams(req);
        Publisher publisher = new Publisher();
        publisher.setName(name);
        publisher.setEmail(email);
        publisher.setAddress(address);
        publisher.setPhone(phone);
        publisher.setCountry(country);
        return publisher;
    }

    public Image getFilledImage(Image image, HttpServletRequest req)
            throws IOException, ServletException {
        Properties prop = new Properties();
        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("database.properties");
        prop.load(is);

        final String path = prop.getProperty(IMAGE_PATH);
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
        image.setPath(prop.getProperty(IMAGE_PATH));
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

