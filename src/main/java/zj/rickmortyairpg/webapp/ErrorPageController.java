package zj.rickmortyairpg.webapp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpSession session) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String reason = HttpStatus.valueOf(Integer.parseInt(status.toString())).getReasonPhrase();
        session.setAttribute("status", status + " " + reason);
        return "error.html";
    }

}
