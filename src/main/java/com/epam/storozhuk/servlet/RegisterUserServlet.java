package com.epam.storozhuk.servlet;

import com.epam.storozhuk.captcha.strategy.CaptchaStrategy;
import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dto.UserDTO;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.UserService;
import com.epam.storozhuk.service.UserSessionService;
import com.epam.storozhuk.validator.ValidatorUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;

@WebServlet("/registerUser")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15,
        location = "C:\\Users\\Oleksandr_Storozhuk\\git_task\\pre_prod_java\\pre_prod_java_q4q1_2019\\java_tasks\\WebShop\\profile_images"
)
public class RegisterUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterUserServlet.class);
    private UserService userService;
    private CartService cartService;
    private CaptchaStrategy captchaStrategy;

    public void init() {
        userService = (UserService) getServletContext().getAttribute(Const.USER_SERVICE);
        captchaStrategy = (CaptchaStrategy) getServletContext().getAttribute(Const.STRATEGY_TYPE);
        cartService = (CartService) getServletContext().getAttribute(Const.CART_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Initializing captcha value");
        captchaStrategy.initCaptchaValue(request, response);
        try {
            request.getRequestDispatcher(Const.REGISTER_PAGE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.debug("Couldn't call forward method because of the next error: " + e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Creating userDTO");
        UserDTO userDTO = createUserDTO(request);
        User user = validateUser(request, userDTO);
        if (user == null || !checkCaptcha(request, response)) {
            LOGGER.debug("Error on user/captcha validation. Saving parameters");
            request.setAttribute(Const.USER_DATA_PARAMETER, userDTO);
            doGet(request, response);
            return;
        }
        if (!userService.registerUser(user)) {
            LOGGER.debug("Couldn't register user");
            request.setAttribute(Const.LOGIN_ERROR, Const.LOGIN_EXISTS_ERROR);
            request.setAttribute(Const.USER_DATA_PARAMETER, userDTO);
            doGet(request, response);
        } else {
            try {
                LOGGER.debug("User register done successfully. Writing userState to context.");
                UserSessionService userSessionService = (UserSessionService) request.getServletContext().getAttribute(Const.USER_SESSION_SERVICE);
                userSessionService.saveUser(request.getSession(), user, cartService);
                savePicture(request);
                response.sendRedirect(Const.MAIN_PAGE);
            } catch (IOException e) {
                LOGGER.debug("Couldn't call redirect because of exception: " + e.getMessage());
            }
        }
    }

    private void savePicture(HttpServletRequest request) {
        try {
            Part part = request.getPart(Const.AVATAR_PARAMETER);
            LOGGER.debug("Found picture in request. Saving.");
            String fileName = getAvatarFileName(part);
            part.write(fileName);
        } catch (IOException | ServletException e) {
            LOGGER.debug("Picture hasn't been saved because of exception: " + e.getMessage());
        }
    }

    private User validateUser(HttpServletRequest request, UserDTO userDTO) {
        Map<String, String> mapForErrors = ValidatorUtil.validateRegisterFields(userDTO);
        if (!mapForErrors.isEmpty()) {
            LOGGER.debug("User fields aren't valid");
            saveErrors(request, mapForErrors);
            return null;
        }
        return new User(userDTO);
    }

    private boolean checkCaptcha(HttpServletRequest request, HttpServletResponse response) {
        if (!captchaStrategy.isValidCaptcha(request)) {
            LOGGER.debug("Captcha isn't valid");
            return false;
        }
        LOGGER.debug("Captcha checked. Returning true");
        return true;
    }

    private void saveErrors(HttpServletRequest request, Map<String, String> errors) {
        LOGGER.debug("Saving errors");
        for (String key : errors.keySet()) {
            request.setAttribute(key, errors.get(key));
        }
    }

    private UserDTO createUserDTO(HttpServletRequest request) {
        String login = request.getParameter(Const.LOGIN_PARAMETER);
        String password = request.getParameter(Const.PASSWORD_PARAMETER);
        String email = request.getParameter(Const.EMAIL_PARAMETER);
        String firstName = request.getParameter(Const.FIRSTNAME_PARAMETER);
        String lastName = request.getParameter(Const.LASTNAME_PARAMETER);
        String news = request.getParameter(Const.NEWS_PARAMETER);
        boolean wantReceiveNews = news != null && !news.isEmpty();
        String avatar = null;
        try {
            avatar = getAvatarFileName(request.getPart(Const.AVATAR_PARAMETER));
        } catch (IOException | ServletException e) {
            LOGGER.debug("Exception on receiving avatar part: " + e.getMessage());
        }
        if (avatar == null || avatar.isEmpty()) {
            avatar = Const.IMAGE_PROFILES_DEFAULT;
        } else {
            avatar = "profile_images/" + avatar;
        }
        LOGGER.debug("Created UserDTO");
        return new UserDTO(login, password, email, firstName, lastName, wantReceiveNews, avatar);
    }

    private String getAvatarFileName(Part avatarPart) {
        String fileName = "";
        if (avatarPart != null) {
            LOGGER.debug("Found picture in request. Saving.");
            fileName = avatarPart.getHeader(Const.HEADER_CONTENT_DISPOSITION);
            fileName = fileName.substring(fileName.indexOf("filename=\"") + "filename=\"".length(), fileName.length() - 1);
        }
        return fileName;
    }
}
