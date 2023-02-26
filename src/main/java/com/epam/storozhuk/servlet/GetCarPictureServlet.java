package com.epam.storozhuk.servlet;

import com.epam.storozhuk.constant.Const;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/getCarPicture")
public class GetCarPictureServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetCarPictureServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String picture = request.getParameter(Const.CAR_PICTURE_PARAMETER);
        String mimeType;
        Path fPath = Paths.get(Const.IMAGES_CARS_PATH + picture);
        if (fPath.toFile().exists()) {
            mimeType = getServletContext().getMimeType(Const.IMAGES_CARS_PATH + picture);
        } else {
            mimeType = getServletContext().getMimeType(Const.IMAGES_CARS_DEFAULT);
            fPath = Paths.get(Const.IMAGES_CARS_DEFAULT);
        }
        response.setContentType(mimeType);
        try {
            response.getOutputStream().write(Files.readAllBytes(fPath));
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
    }

}
