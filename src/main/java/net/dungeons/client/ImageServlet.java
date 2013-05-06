package net.dungeons.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ImageServlet", urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    String image = request.getParameter("image");
    String file = getFile("/res/chars/", image);
    if (file == null) {
      file = "/res/chars/evilLawn1.png";
    }
    try (InputStream in = getServletContext().getResourceAsStream(file);
            OutputStream out = response.getOutputStream()) {
      byte[] buffer = new byte[1024];
      int read;
      while ((read = in.read(buffer)) > 0) {
        out.write(buffer, 0, read);
      }
    }
  }

  private String getFile(String reschars, String image) {
    Set<String> files = getServletContext().getResourcePaths(reschars);
    for (String file : files) {
      if (file.endsWith("/")) {
        String f = getFile(file, image);
        if (f != null) {
          return f;
        }
      } else if (file.contains(image)) {
        return file;
      }
    }
    return null;

  }
}
