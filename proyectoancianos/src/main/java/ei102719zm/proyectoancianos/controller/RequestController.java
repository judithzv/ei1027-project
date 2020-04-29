package ei102719zm.proyectoancianos.controller;

import java.util.ArrayList;
import java.util.List;

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

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.RequestDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.Request;
import ei102719zm.proyectoancianos.model.UserDetails;


@Controller
@RequestMapping("/request") 
public class RequestController {
	private RequestDao requestDao;
	private UserDao userDao;
	private ElderlyDao elderlyDao;
	   @Autowired
	   public void setRequestDao(RequestDao requestDao) { 
	       this.requestDao=requestDao;
	   }
	   
	   @Autowired
	   public void setUserDao(UserDao userDao) { 
	       this.userDao=userDao;
	   }
	   
	   @Autowired
	   public void setElderlyDao(ElderlyDao elderlyDao) { 
	       this.elderlyDao = elderlyDao;
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
		   if(bindingResult.hasErrors())
			   return "request/add";
		   
		   String number = requestDao.getLastNumber();
		   	if(number==null)
		   		number = "199999999";
		   	int num = Integer.parseInt(number) + 1;
		   	UserDetails user = (UserDetails) session.getAttribute("user");
		   	Elderly elderly = userDao.getElderly(user.getUsername());
		   	String DNI = elderly.getDNI();
		   	if(elderlyDao.hasService(request.getService(), DNI)) {
		   		bindingResult.rejectValue("service","service", "you already have a request for " + request.getService() + " service"); 
		   		return "request/add";
		   	}
			request.setDNI(elderly.getDNI());
			request.setNumber(Integer.toString(num));
			request.setState("in process");
			requestDao.addRequest(request);
			return "redirect:../elderly/perfil/"+elderly.getDNI();
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
	   
		  @RequestMapping("/gestion")
		   public String gestionRequest(Model model) {
			  List<Request> requests = requestDao.getPendingRequests();
		      model.addAttribute("requests", requests);
		      return "request/gestion";
		   }
	   
	   @RequestMapping(value="accept/{number}")
	   public String acceptRequest(HttpSession session, Model model, @PathVariable String number) {
		   Request request = requestDao.getRequest(number);
		   List<Contract> contracts = requestDao.getPossibleContracts(request.getService());
		   session.setAttribute("request", request);
		   model.addAttribute("contracts", contracts);
		   return "request/accept";
	   }
	   
	   @RequestMapping(value="accepted/{idContract}")
	   public String processAcceptRequest(HttpSession session, Model model, @PathVariable("idContract") String idContract) {
		   Request request = (Request) session.getAttribute("request");
		   request.setState("accepted");
		   request.setIdContract(idContract);
		   requestDao.updateRequest(request);
		   return "redirect:../gestion";
	   }
	   
	   @RequestMapping(value="cancel/{number}")
	   public String cancelRequest(HttpSession session, Model model, @PathVariable String number) {
		   Request request = requestDao.getRequest(number);
		   request.setState("rejected");
		   requestDao.updateRequest(request);
		   return "redirect:../gestion";
	   }


}
