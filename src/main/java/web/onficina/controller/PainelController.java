package web.onficina.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;

@Controller
public class PainelController {

	@GetMapping(value = {"/", "/home.html"} )
	public String home() {
		return "home";
	}

	@GetMapping(value = {"/painel", "/painel.html"} )
	public String painel() {
		return "painel";
	}
	
	@HxRequest
	@GetMapping(value = { "/painel", "/painel.html" })
	public View painelHTMX() {
		return FragmentsRendering
				.with("painel :: conteudo")
				.fragment("/layout/fragments/header :: usuariologinlogout")
				.build();
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/emDesenvolvimento")
	public String EmDesenvolvimento() {
		return "emDesenvolvimento :: conteudo";
	}

}
