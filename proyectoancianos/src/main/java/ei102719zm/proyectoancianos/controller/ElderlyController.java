package ei102719zm.proyectoancianos.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Elderly;
import ei102719zm.proyectoancianos.model.Invoice;
import ei102719zm.proyectoancianos.model.Request;


@Controller
@RequestMapping("/elderly") 
public class ElderlyController {
	   private ElderlyDao elderlyDao;
	   private UserDao userDao;

	   @Autowired
	   public void setElderlyDao(ElderlyDao elderlyDao) { 
	       this.elderlyDao=elderlyDao;
	   }
	   
	   @Autowired
	   public void setUserDao(UserDao userDao) { 
	       this.userDao=userDao;
	   }

	   @RequestMapping("/list")
	   public String listElderly(HttpSession session, Model model) {
	      model.addAttribute("elderlies", elderlyDao.getElderlies());
	      session.setAttribute("nextURL", "redirect:list");
	      session.setAttribute("back", "../list");
	      return "elderly/list";
	   }
	   @RequestMapping(value="/add") 
		public String addElderly(HttpSession session, Model model) {
			model.addAttribute("elderly", new Elderly());
			model.addAttribute("address", new Address());
			model.addAttribute("bankData", new BankData());
			String URL = (String) session.getAttribute("back");
			if(URL != null)
				model.addAttribute("URL", "elderly/" + URL);
			else
				model.addAttribute("URL", "../login");
			return "elderly/add";
		}
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("elderly") Elderly elderly,
	                                   BindingResult bindingResult) {  
		 ElderlyValidator elderlyValidator = new ElderlyValidator();
		 elderlyValidator.validate(elderly, bindingResult);
		 
		 if (elderlyDao.getElderly(elderly.getDNI()) != null) 
			 bindingResult.rejectValue("DNI","DNI", elderly.getDNI()+" is already in use");
		 
		 String IBAN = elderly.getBankData().getIBAN();
		 if(IBAN != null && elderlyDao.getBankData(IBAN) != null)
			 bindingResult.rejectValue("bankData.IBAN", "bankData.IBAN", "IBAN " + IBAN + " is already in use");

		 if(userDao.userAlreadyExists(elderly.getUserName())) 
			 bindingResult.rejectValue("userName","userName", elderly.getUserName()+" is already in use"); 
		 
		   if (bindingResult.hasErrors())
			   return "elderly/add";
		   
	   	 elderlyDao.addElderly(elderly);
	   	 elderlyDao.addAddress(elderly.getAddress(), elderly.getDNI());
	   	 if(elderly.getBankData().getIBAN() != null)
	   		 elderlyDao.addBankData(elderly.getBankData(), elderly.getDNI());
	   	 String nextURL = (String) session.getAttribute("nextURL");
	   	 if (nextURL != null)
	   		 return nextURL;
	   	 return "redirect:perfil/"+ elderly.getDNI(); 
	   	 
	    }	   
	   
	   @RequestMapping(value="/update/{DNI}", method = RequestMethod.GET) 
		public String editElderly(HttpSession session, Model model, @PathVariable String DNI) { 
		   Elderly elderly = elderlyDao.getElderly(DNI);
			model.addAttribute("elderly", elderly);
			session.setAttribute("userName", elderly.getUserName());
			if(elderly.getBankData().getIBAN() != null) {
				session.setAttribute("IBAN", elderly.getBankData().getIBAN());
			}
			String URL = (String) session.getAttribute("back");
			model.addAttribute("URL", URL);
			
			return "elderly/update"; 
		}
	   
	    @RequestMapping(value="/update", method = RequestMethod.POST) 
		public String processUpdateSubmit(HttpSession session,
	                            @ModelAttribute("elderly") Elderly elderly, 
	                            BindingResult bindingResult) {
			 
	    	ElderlyValidator elderlyValidator = new ElderlyValidator();
	    	elderlyValidator.validate(elderly, bindingResult);
			 
	    	String userName = elderly.getUserName();
			 if(!userName.equals(session.getAttribute("userName")) && userDao.userAlreadyExists(userName)) 
				 bindingResult.rejectValue("userName","userName", elderly.getUserName()+" is already in use");
			 
			 String IBAN = (String) session.getAttribute("IBAN");
			 if(IBAN != null && !elderly.getBankData().getIBAN().equals(IBAN) && elderlyDao.getBankData(elderly.getBankData().getIBAN()) != null)
				 bindingResult.rejectValue("bankData.IBAN","bankData.IBAN", "IBAN " + IBAN + " is already in use");
			 
			 if (bindingResult.hasErrors()) 
				 return "elderly/update";
			 
			 session.removeAttribute("userName");
			 session.removeAttribute("IBAN");

			 elderlyDao.updateAddress(elderly.getAddress(), elderly.getDNI());
			 elderlyDao.updateBankData(elderly.getBankData(), elderly.getDNI());
			 elderlyDao.updateElderly(elderly);
		   	 String nextURL = (String) session.getAttribute("nextURL");
		   	 if (nextURL != null)
		   		 return nextURL;
		   	 return "redirect:perfil/"+ elderly.getDNI(); 
		}    

	   @RequestMapping(value="/delete/{DNI}")
		public String processDelete(@PathVariable String DNI, RedirectAttributes redirectAttrs) {
		   List<Invoice> facturas = elderlyDao.getInvoices(DNI);
		   List<Request> requests = elderlyDao.getRequests(DNI);
		   String mensaje = "";
		   if(facturas.size() != 0 || requests.size() != 0) {
			   // Mostrar mensaje
			   redirectAttrs
	            .addFlashAttribute("mensaje", "User can not be deleted because they have requests and invoices")
	            .addFlashAttribute("clase", "error");
		   }else {
			    redirectAttrs
	            .addFlashAttribute("mensaje", "Successfully deleted")
	            .addFlashAttribute("clase", "success");
			   elderlyDao.deleteAddress(DNI);
			   elderlyDao.deleteBankData(DNI);
			   elderlyDao.deleteElderly(DNI);
		   }
	       return "redirect:../list"; 
		}
	   
	   @RequestMapping(value="/perfil/{DNI}")
	   public String mostrarPerfil(HttpSession session, @PathVariable String DNI, Model model) {
		   session.setAttribute("dni", DNI);
		   model.addAttribute("dni", DNI);
		   model.addAttribute("elderly",elderlyDao.getElderly(DNI));
		   session.setAttribute("back", "../perfil/"+DNI);
		   return "elderly/perfil";
	   }
	   @RequestMapping(value="/datos")
	   public String mostrarDatos(HttpSession session, Model model) {
		   String DNI = (String) session.getAttribute("dni");
		   if(DNI!=null) {
			   Elderly elderly = elderlyDao.getElderly(DNI);
			   model.addAttribute("elderly",elderly);
			   return "elderly/datos";
		   }
		   return "redirect:../";
	   }
	   @RequestMapping(value="/address/{DNI}")
	   public String mostrarAddress( @PathVariable String DNI, Model model) {
		   Address address= elderlyDao.getAddress(DNI);
		   model.addAttribute("address",address);
		   return "elderly/address";
	   
	   }
	   @RequestMapping(value="/bankdata/{DNI}")
	   public String mostrarBankdata( @PathVariable String DNI, Model model) {
		   BankData bankdata= elderlyDao.getBankDataByDNI(DNI);
		   model.addAttribute("bankdata",bankdata);
		   return "elderly/bankdata";
	   
	   }

	   @RequestMapping(value="/eliminarcuenta")
	   public String eliminarcuenta(HttpSession session, Model model) {
		   String DNI = (String) session.getAttribute("dni");
		   if(DNI!=null) {
			   Elderly elderly = elderlyDao.getElderly(DNI);
			   model.addAttribute("elderly",elderly);
			   return "elderly/eliminarcuenta";
		   }
		   return "redirect:../";
	   }
	   @RequestMapping(value="/invoices")
	   public String mostrarFacturas(HttpSession session, Model model) {
		   String DNI = (String) session.getAttribute("dni");
		   model.addAttribute("DNI", DNI);
		   if(DNI!=null) {
			   model.addAttribute("invoices", elderlyDao.getInvoices(DNI));
			   return "invoice/list";
		   }
		   return "redirect:/";
	   }

}
