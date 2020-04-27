package ei102719zm.proyectoancianos.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Address;
import ei102719zm.proyectoancianos.model.BankData;
import ei102719zm.proyectoancianos.model.Elderly;


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
	   public String listElderly(Model model) {
	      model.addAttribute("elderlies", elderlyDao.getElderlies());
	      return "elderly/list";
	   }
	   @RequestMapping(value="/add") 
		public String addElderly(Model model) {
			model.addAttribute("elderly", new Elderly());
			model.addAttribute("address", new Address());
			model.addAttribute("bankData", new BankData());
			return "elderly/add";
		}
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(@ModelAttribute("elderly") Elderly elderly,
	                                   BindingResult bindingResult) {  
		   if (bindingResult.hasErrors()) {
			   List<FieldError> errors = bindingResult.getFieldErrors();
			   for(FieldError error : errors) {
				   bindingResult.rejectValue(error.getField(),error.getField(), error.getDefaultMessage());
			   }
			   return "elderly/add";
		   }
		 
		 if (elderlyDao.getElderly(elderly.getDNI()) != null) {
			 bindingResult.rejectValue("DNI","DNI", elderly.getDNI()+" is already in use"); 
			 return "elderly/add";
		 }
		 
		 if(userDao.userAlreadyExists(elderly.getUserName())) {
			 bindingResult.rejectValue("userName","userName", elderly.getUserName()+" is already in use"); 
			 return "elderly/add";
		 }
	   	 elderlyDao.addElderly(elderly);
	   	 elderlyDao.addAddress(elderly.getAddress(), elderly.getDNI());
	   	 if(elderly.getBankData().getIBAN() != null)
	   		 elderlyDao.addBankData(elderly.getBankData(), elderly.getDNI());
	   	 return "redirect:list"; 
	   	 
	    }	   
	   
	   @RequestMapping(value="/update/{DNI}", method = RequestMethod.GET) 
		public String editElderly(Model model, @PathVariable String DNI) { 
			model.addAttribute("elderly", elderlyDao.getElderly(DNI));
			return "elderly/update"; 
		}
	    @RequestMapping(value="/update", method = RequestMethod.POST) 
		public String processUpdateSubmit(
	                            @ModelAttribute("elderly") Elderly elderly, 
	                            BindingResult bindingResult) {
			 if (bindingResult.hasErrors()) 
				 return "elderly/update";
			 
			 if(userDao.userAlreadyExists(elderly.getUserName())) {
				 bindingResult.rejectValue("userName","userName", elderly.getUserName()+" is already in use"); 
				 return "elderly/add";
			 }
			 elderlyDao.updateAddress(elderly.getAddress(), elderly.getDNI());
			 elderlyDao.updateBankData(elderly.getBankData(), elderly.getDNI());
			 elderlyDao.updateElderly(elderly);
			 return "redirect:list"; 
		}    

	   @RequestMapping(value="/delete/{DNI}")
		public String processDelete(@PathVariable String DNI) {
		   elderlyDao.deleteAddress(DNI);
		   elderlyDao.deleteBankData(DNI);
		   elderlyDao.deleteElderly(DNI);
	       return "redirect:../list"; 
		}
	   
	   @RequestMapping(value="/perfil/{DNI}")
	   public String mostrarPerfil(HttpSession session, @PathVariable String DNI, Model model) {
		   session.setAttribute("dni", DNI);
		   model.addAttribute("dni", DNI);
		   model.addAttribute("elderly",elderlyDao.getElderly(DNI));
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
		   if(DNI!=null) {
			   model.addAttribute("invoices", elderlyDao.getInvoices(DNI));
			   return "invoice/list";
		   }
		   return "redirect:/";
	   }

}
