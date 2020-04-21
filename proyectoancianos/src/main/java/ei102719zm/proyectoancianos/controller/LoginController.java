package ei102719zm.proyectoancianos.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.UserDetails;

class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
	  // Exercici: Afegeix codi per comprovar que 
         // l'usuari i la contrasenya no estiguen buits 
         // ...
		UserDetails user = (UserDetails) obj;
		if(user.getUsername().equals(""))
			errors.rejectValue("username", "obligatori",
                    "Cal introduir un valor");
		if(user.getPassword().equals(""))
			errors.rejectValue("password", "obligatoria",
                    "Cal introduir un valor");
	}
}

@Controller
public class LoginController {
	@Autowired
	private ElderlyDao elderlyDao;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserDetails());
		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails user,  		
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	       // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		Elderly elderly = elderlyDao.loadUserByUsername(user.getUsername(), user.getPassword()); 
		if (elderly == null) {
			bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta"); 
			return "login";
		}
		// Autenticats correctament. 
		// Guardem les dades de l'usuari autenticat a la sessió
		session.setAttribute("user", elderly); 
			
		if(session.getAttribute("nextUrl") != null) {
			String nextUrl = (String) session.getAttribute("nextUrl");
			session.removeAttribute("nextUrl");
			return "redirect:" + nextUrl;
		}
		// Torna a la pàgina principal
		return "redirect:/";
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}
