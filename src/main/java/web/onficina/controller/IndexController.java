package web.onficina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@GetMapping(value = {"/", "/index.html"} )
	public String index() {
		//return "index";
		return "boasVindas";
	}
	
	@GetMapping("/painel")
	public String painel(HttpSession session) {
		if (session.getAttribute("usuarioLogado") == null) {
			return "redirect:/login";
		}
		return "index";
	}

}
