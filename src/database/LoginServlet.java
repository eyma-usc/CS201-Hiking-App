package database;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import at.favre.lib.crypto.bcrypt.BCrypt;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    	resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
    	resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject json = new JSONObject(sb.toString());
        String email = json.getString("email");
        String password = json.getString("password");

        resp.setContentType("application/json");

        User user = userRepository.findByEmail(email);
        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
            return;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPasswordHash());
        if (!result.verified) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"error\":\"Invalid credentials\"}");
            return;
        }

        String token = JwtUtil.generateToken(user.getId());
        resp.getWriter().write("{\"token\":\"" + token + "\"}");
    }
}
