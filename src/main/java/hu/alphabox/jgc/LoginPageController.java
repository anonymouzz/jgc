package hu.alphabox.jgc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginPageController {

  @GetMapping("/login/token")
  String loginPage() {
    return "login-token";
  }

}
