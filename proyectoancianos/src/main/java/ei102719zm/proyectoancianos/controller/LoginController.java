package ei102719zm.proyectoancianos.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors; 
import org.springframework.validation.Validator;

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.UserDetails;

class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
		UserDetails user = (UserDetails) obj;
		if(user.getUsername().equals(""))
			errors.rejectValue("username", "obligatorio",
                    "A username is required");
		if(user.getPassword().equals(""))
			errors.rejectValue("password", "obligatoria",
                    "A password is required");
	}
}

@Controller
public class LoginController {
	UserDao userDao = new UserDao();
	
	@Autowired
	public void setElderlyDao(UserDao userDao) { 
		this.userDao=userDao;
	}

	
	@SuppressWarnings("unused")
	@RequestMapping("/login")
	public String login(HttpSession session, Model model) {
		UserDetails user = (UserDetails) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("user", new UserDetails());
			return "login";
		}
		String nextUrl = (String) session.getAttribute("nextUrl");
		session.setAttribute("back", "../login");
		if(nextUrl != null) {
			session.removeAttribute("nextUrl");
			return "redirect:"+nextUrl;
		}
		Elderly elderly = userDao.getElderly(user.getUsername());
		if(elderly != null) {
			model.addAttribute("elderly", elderly);
			return "redirect:/elderly/perfil/"+elderly.getDNI();
		}
		else {
			Company company = userDao.getCompany(user.getUsername());
			if(company != null) {
				model.addAttribute("company", company);
				return "redirect:/company/perfil/"+company.getCIF();
			}
			else
				return "portada";
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails user,  		
				BindingResult bindingResult, HttpSession session, Model model) {
		UserValidator userValidator = new UserValidator(); 
		userValidator.validate(user, bindingResult); 
		if (bindingResult.hasErrors()) {
			return "login";
		}
	       // Comprova que el login siga correcte 
		// intentant carregar les dades de l'usuari 
		user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
		if (user == null) {
			bindingResult.rejectValue("password", "badpw", "Usuario no válido o contraseña incorrecta"); 
			return "login";
		}
		session.setAttribute("user", user);
		String nextUrl = (String) session.getAttribute("nextUrl");
		if(nextUrl != null) {
			session.removeAttribute("nextUrl");
			return "redirect:" + nextUrl;
		}
		Elderly elderly = userDao.getElderly(user.getUsername());
		if(elderly != null) {
			model.addAttribute("elderly", elderly);
			return "redirect:/elderly/perfil/"+elderly.getDNI();
		}
		else {
			Company company = userDao.getCompany(user.getUsername());
			if(company != null) {
				model.addAttribute("company", company);
				return "redirect:/company/perfil/"+ company.getCIF();
			}
			else
				return "portada";
		}
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
}
