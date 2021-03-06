package ei102719zm.proyectoancianos.controller;

import java.util.ArrayList;
import java.util.LinkedList;
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

import ei102719zm.proyectoancianos.dao.CompanyDao;
import ei102719zm.proyectoancianos.dao.ContractDao;
import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Invoice;
import ei102719zm.proyectoancianos.model.Request;

@Controller
@RequestMapping("/contract") 
public class ContractController {
	private ContractDao contractDao;
	private CompanyDao companyDao;
	
	@Autowired
	 public void setContractDao(ContractDao contractDao) { 
	       this.contractDao = contractDao;
	}
	@Autowired
	 public void setCompanyDao(CompanyDao companyDao) { 
	       this.companyDao = companyDao;
	}
	
	  @RequestMapping("/list")
	   public String listContract(HttpSession session, Model model) {
	      session.setAttribute("back", "../../contract/list");
	      model.addAttribute("contracts", contractDao.getContracts());
	      return "contract/list";
	   }	   
	
	   @RequestMapping(value="/add")
	   public String addContract(HttpSession session, Model model) {
		   Contract contract = (Contract) session.getAttribute("contract");
		   if(contract != null) {
			   contractDao.addContract(contract);
			   session.removeAttribute("contract");
			   return "redirect:list";
		   }
		   model.addAttribute("contract", new Contract());
		   return "contract/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("contract") Contract contract,
	                                   BindingResult bindingResult) {  
		   ContractValidator contractValidator = new ContractValidator();
		   contractValidator.validate(contract, bindingResult);
		   if(bindingResult.hasErrors())
			   return "contract/add";
		   
		   String lastId = contractDao.getLastId();
		   if(lastId == null)
			   lastId = "299999999";
		   int num = Integer.parseInt(lastId) + 1;
		   contract.setId(Integer.toString(num));
		   Company company = contractDao.getCompany(contract.getCIF());
		   if(company == null) {
			   session.setAttribute("contract", contract);
			   return "redirect:../company/add";
		   }
		   contractDao.addContract(contract);
		   return "redirect:list"; 
	   	 
	    }
	   @RequestMapping(value="/update/{id}", method = RequestMethod.GET) 
		public String editContract(Model model, @PathVariable String id) { 
			model.addAttribute("contract", contractDao.getContract(id));
			return "contract/update"; 
		}
	    @RequestMapping(value="/update", method = RequestMethod.POST) 
		public String processUpdateSubmit(
	                            @ModelAttribute("contract") Contract contract, 
	                            BindingResult bindingResult) {
			 ContractValidator contractValidator = new ContractValidator();
			 contractValidator.validate(contract, bindingResult);
			 if(bindingResult.hasErrors())
				 return "contract/update";
			 contractDao.updateContract(contract);
			 return "redirect:list"; 
		}    

	   @RequestMapping(value="/delete/{id}")
		public String processDelete(@PathVariable String id, RedirectAttributes redirectAttrs) {
		   List<Request> requests = contractDao.getRequests(id);
		   String mensaje = "";
		   if(requests.size() != 0) {
			   // Mostrar mensaje
			   redirectAttrs
	            .addFlashAttribute("mensaje", "Contract can not be deleted because there are request associated with it")
	            .addFlashAttribute("clase", "error");
		   }else {
			    redirectAttrs
	            .addFlashAttribute("mensaje", "Successfully deleted")
	            .addFlashAttribute("clase", "success");
				contractDao.deleteContract(id);
		   }
	       return "redirect:../list"; 
		}

}
