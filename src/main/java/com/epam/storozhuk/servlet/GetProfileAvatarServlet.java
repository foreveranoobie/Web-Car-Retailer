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

@WebServlet("/getAvatar")
public class GetProfileAvatarServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(GetProfileAvatarServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(Const.MIME_IMAGE_JPEG);
        String imagePath = (String) request.getSession().getAttribute(Const.USER_AVATAR_ATTRIBUTE);
        Path fPath = Paths.get(imagePath);
        if (!Files.exists(fPath)) {
            LOGGER.debug("Profile image doesn't exist. Returning default image");
            fPath = Paths.get(Const.IMAGE_PROFILES_DEFAULT);
        }
        try {
            response.getOutputStream().write(Files.readAllBytes(fPath));
        } catch (IOException e) {
            LOGGER.debug("Couldn't avatar because of IO exception: " + e.getMessage());
        }
    }
}
