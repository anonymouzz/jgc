package hu.alphabox.jgc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ErrorPageController {

  public static String generateRedirectUrl(String title, String description) {
    return "/application-error?title=%s&description=%s".formatted(
        URLEncoder.encode(title, StandardCharsets.UTF_8),
        URLEncoder.encode(description, StandardCharsets.UTF_8)
    );
  }

  @RequestMapping("/application-error")
  String error(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description,
      RedirectAttributes redirectAttributes
  ) {
    if (title != null || description != null) {
      redirectAttributes.addFlashAttribute("title", title);
      redirectAttributes.addFlashAttribute("description", description);
      return "redirect:/application-error";
    }
    return "error-page";
  }
}
