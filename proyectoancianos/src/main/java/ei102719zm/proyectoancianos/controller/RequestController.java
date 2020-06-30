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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	   
	   @RequestMapping(value="/choose") 
		public String chooseRequest(HttpSession session, Model model) {
	       if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/choose");
	          return "redirect:../login";
	       }
	       UserDetails user = (UserDetails) session.getAttribute("user");
		   Elderly elderly = userDao.getElderly(user.getUsername());
	       model.addAttribute("elderly",elderly);
			return "request/choose";
		}
	   
	   @RequestMapping(value="/catering")
	   public String addCatering(HttpSession session, Model model) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/catering");
	          return "redirect:../login";
	       }
		   UserDetails user = (UserDetails) session.getAttribute("user");
		   Elderly elderly = userDao.getElderly(user.getUsername());
		   if(elderlyDao.hasService("catering", elderly.getDNI()))
			   return "request/error";
		   Request request = new Request();
		   request.setService("catering");
		   request.setDNI(elderly.getDNI());
		   request.setState("in process");
		   BankData bankData = elderly.getBankData();
		   request.setBankData(bankData);
		   model.addAttribute("request", request);
		   return "request/catering";
	   }
	   
	   @RequestMapping(value="/health")
	   public String addHealth(HttpSession session, Model model) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/health");
	          return "redirect:../login";
	       }
		   UserDetails user = (UserDetails) session.getAttribute("user");
		   Elderly elderly = userDao.getElderly(user.getUsername());
		   if(elderlyDao.hasService("health", elderly.getDNI()))
			   return "request/error";
		   Request request = new Request();
		   request.setService("health");
		   request.setDNI(elderly.getDNI());
		   request.setState("in process");
		   BankData bankData = elderly.getBankData();
		   request.setBankData(bankData);
		   model.addAttribute("request", request);
		   return "request/health";
	   }
	   
	   @RequestMapping(value="/cleaning")
	   public String addCleanning(HttpSession session, Model model) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/cleanning");
	          return "redirect:../login";
	       }
		   UserDetails user = (UserDetails) session.getAttribute("user");
		   Elderly elderly = userDao.getElderly(user.getUsername());
		   if(elderlyDao.hasService("cleaning", elderly.getDNI()))
			   return "request/error";
		   Request request = new Request();
		   request.setService("cleaning");
		   request.setDNI(elderly.getDNI());
		   request.setState("in process");
		   BankData bankData = elderly.getBankData();
		   request.setBankData(bankData);
		   model.addAttribute("request", request);
		   return "request/cleaning";
	   }
			   
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("request") Request request,
			   							BindingResult bindingResult) {  
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/add");
	          return "redirect:../login";
	       }
		   
		   RequestValidator requestValidator = new RequestValidator();
		   requestValidator.validate(request, bindingResult);
		   if(bindingResult.hasErrors()) {
			   request.setDetails(null);
			   if(request.getService().equals("cleaning"))
				   return "request/cleaning";
			   else if(request.getService().equals("health"))
				   return "request/health";
			   else
				   return "request/catering";
		   }
		   String number = requestDao.getLastNumber();
		   	if(number==null)
		   		number = "199999999";
		   	int num = Integer.parseInt(number) + 1;
		   	UserDetails user = (UserDetails) session.getAttribute("user");
		   	Elderly elderly = userDao.getElderly(user.getUsername());
			request.setNumber(Integer.toString(num));
			request.setElderly(elderly);
			requestDao.addRequest(request);						
			return "redirect:../elderly/feedback";
	   }
	   
	   
	   
	   
	   @RequestMapping(value="list")
	   public String listRequests(HttpSession session, Model model) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/list");
	          return "redirect:../login";
	       }
		   String DNI = (String) session.getAttribute("dni");
		   model.addAttribute("DNI", DNI);
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
		      for(Request request: requests) {
		    	  String DNI = request.getDNI();
		    	  Elderly elderly = elderlyDao.getElderly(DNI);
		    	  request.setElderly(elderly);
		      }
		      return "request/gestion";
		   }
	   
	   @RequestMapping(value="accept/{number}")
	   public String acceptRequest(HttpSession session, Model model, @PathVariable String number) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/accept/"+number);
	          return "redirect:../../login";
	       }
		   Request request = requestDao.getRequest(number);
		   List<Contract> contracts = requestDao.getPossibleContracts(request.getService());
		   session.setAttribute("request", request);
		   model.addAttribute("contracts", contracts);
		   return "request/accept";
	   }
	   
	   @RequestMapping(value="accepted/{idContract}")
	   public String processAcceptRequest(HttpSession session, Model model, @PathVariable("idContract") String idContract,  RedirectAttributes redirectAttrs) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/accepted/"+idContract);
	          return "redirect:../../login";
	       }
		   Request request = (Request) session.getAttribute("request");
		   request.setState("accepted");
		   request.setIdContract(idContract);
		   requestDao.updateRequest(request);
		   redirectAttrs
           .addFlashAttribute("mensaje", "Request with number " + request.getNumber() + " succesfully assigned to the contract with id " + idContract)
           .addFlashAttribute("clase", "success");
		   return "redirect:../gestion";
	   }
	   
	   @RequestMapping(value="cancel/{number}")
	   public String cancelRequest(HttpSession session, Model model, @PathVariable String number) {
		   if (session.getAttribute("user") == null) 
	       { 
	          session.setAttribute("nextUrl", "/request/cancel/"+number);
	          return "redirect:../../login";
	       }
		   model.addAttribute("number", number);
		   return "request/confirmation";
	   }
	   
	   
	   @RequestMapping(value="confirmation/{number}")
	   public String confirmCancelRequest(HttpSession session, Model model, @PathVariable String number, RedirectAttributes redirectAttrs) {
		   Request request = requestDao.getRequest(number);
		   request.setState("rejected");
		   requestDao.updateRequest(request);
		   redirectAttrs
           .addFlashAttribute("mensaje", "Request with number " + request.getNumber() + " succesfully rejected.")
           .addFlashAttribute("clase", "success");
		   return "redirect:../gestion";
	   }

}
