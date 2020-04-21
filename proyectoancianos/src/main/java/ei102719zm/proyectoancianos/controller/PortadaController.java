package ei102719zm.proyectoancianos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.UserDetails;

@Controller
public class PortadaController {

	@RequestMapping("/portada") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "/portada";
	}
}
