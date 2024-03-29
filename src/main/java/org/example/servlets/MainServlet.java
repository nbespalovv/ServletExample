package org.example.servlets;

import org.example.domain.FileDescription;
import org.example.domain.UserProfile;
import org.example.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    private AccountService service = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String sessionId = req.getSession().getId();
        UserProfile user = service.getUserBySessionId(sessionId);
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }
        String parameter = req.getParameter("path");

        String defaultPath = System.getProperty("user.home");

        defaultPath = defaultPath + System.getProperty("file.separator") + user.getLogin();
        File userDefaultPath = new File(defaultPath);
        if (!userDefaultPath.exists())
            userDefaultPath.mkdirs();

        String openPath;
        if (parameter == null || parameter.isEmpty()) {
            openPath = defaultPath;
        } else {
            openPath = parameter;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        File absoluteFile = new File(openPath);
        if (!absoluteFile.getCanonicalPath().contains(defaultPath))
            openPath = defaultPath;

        File file = new File(openPath);

        File[] files = file.listFiles();
        if (files == null) {
            resp.sendError(404, "File not found");
            return;
        }

        SimpleDateFormat fileFormat = new SimpleDateFormat("d/M/yy, K:mm:ss a");
        List<FileDescription> fileDescriptionList = new ArrayList<>();

        for (File tempFile : files) {
            fileDescriptionList.add(new FileDescription(tempFile.getName(),
                    openPath + System.getProperty("file.separator") + tempFile.getName(),
                    String.valueOf(tempFile.length()),
                    fileFormat.format(new Date(tempFile.lastModified())),
                    tempFile.isDirectory()));
        }

        req.setAttribute("date", LocalDateTime.now().format(formatter));
        req.setAttribute("back", file.getParent());
        req.setAttribute("name", openPath);
        req.setAttribute("files", fileDescriptionList);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);

    }
}
