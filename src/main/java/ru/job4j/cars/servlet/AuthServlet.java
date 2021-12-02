package ru.job4j.cars.servlet;

import ru.job4j.cars.model.User;
import ru.job4j.cars.store.AdRepository;

import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * - @WebServlet(urlPatterns = "/auth.do")
 * Страница Login.jsp
 * Авторизация и регистрацию
 * Сервлет проверяет почту и пароль, если они совпадают, то переходит на страницу вакансий.
 * Если нет, то возвращает обратно на страницу Login.
 * Регистрация пользователя.
 *
 * @author SlartiBartFast-art
 */
public class AuthServlet extends HttpServlet {

    /**
     * Если пользователь ввел верную почту и пароль, то мы записываем в HttpSession
     * детали этого пользователя.
     * HttpSession sc = req.getSession();
     * User admin = new User();
     * admin.setName("Admin");
     * admin.setEmail(email);
     * sc.setAttribute("user", admin);
     * Теперь эти данные можно получить на другой странице.
     * <p>
     * Регистрация пользователя.
     * Авторизация и регистрацию
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = AdRepository.instOf().findByEmail(email).get(0);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/candidate.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
