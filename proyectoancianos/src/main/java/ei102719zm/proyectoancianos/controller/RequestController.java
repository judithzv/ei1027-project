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
import org.springframework.web.bind.annotation.RequestParam;

import ei102719zm.proyectoancianos.dao.RequestDao;
import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.Request;
import ei102719zm.proyectoancianos.model.UserDetails;


@Controller
@RequestMapping("/request") 
public class RequestController {
	private RequestDao requestDao;
	private int numRequest;
	   @Autowired
	   public void setRequestDao(RequestDao requestDao) { 
	       this.requestDao=requestDao;
	       numRequest = 0;
	   }
	   
	   @RequestMapping(value="/add") 
		public String addRequest(HttpSession session, Model model) {
	       if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/add");
	          return "redirect:../login";
	       } 
	       model.addAttribute("request", new Request());
			return "request/add";
		}
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("request") Request request,
	                                   BindingResult bindingResult) {  
		   	String number = requestDao.getLastNumber();
		   	int num = Integer.parseInt(number) + 1;
		   	Elderly elderly = (Elderly) session.getAttribute("user");
			request.setDNI(elderly.getDNI());
			request.setNumber(Integer.toString(num));
			request.setState("in process");
			requestDao.addRequest(request);
			return "redirect:../";
	   }
	   
	   @RequestMapping(value="list")
	   public String listRequests(HttpSession session, Model model) {
		   String DNI = (String) session.getAttribute("dni");
		   if(DNI != null) {
			   model.addAttribute("requests", requestDao.getRequests(DNI));
			   return "request/list";
		   }
		   return "redirect:../";
	   }

}
