package org.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // тип данных, которые вы отправляете
        // например application/pdf, text/plain, text/html, image/jpg
        String parameter = req.getParameter("path");
        String contentType = Files.probeContentType(Path.of(parameter));
        resp.setContentType(contentType);
        String fileName = Paths.get(parameter).getFileName().toString();

        resp.setHeader("Content-disposition","attachment; filename=" + fileName);

        // файл, который вы отправляете
        File my_file = new File(parameter);

        // отправить файл в response
        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }

        // освободить ресурсы
        in.close();
        out.flush();
    }
}