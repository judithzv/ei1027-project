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

import ei102719zm.proyectoancianos.dao.CompanyDao;
import ei102719zm.proyectoancianos.dao.ContractDao;
import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;

@Controller
@RequestMapping("/contract") 
public class ContractController {
	private ContractDao contractDao;
	
	@Autowired
	 public void setCompanyDao(ContractDao contractDao) { 
	       this.contractDao = contractDao;
	   }
	  @RequestMapping("/list")
	   public String listContract(Model model) {
	      model.addAttribute("contracts", contractDao.getContracts());
	      return "contract/list";
	   }
	   
	
	   @RequestMapping(value="/add")
	   public String addContract(HttpSession session, Model model) {
		   Contract contract = (Contract) session.getAttribute("contract");
		   if(contract != null) {
			   contractDao.addContract(contract);
			   return "redirect:list";
		   }
		   model.addAttribute("contract", new Contract());
		   return "contract/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("contract") Contract contract,
	                                   BindingResult bindingResult) {  
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
			 if (bindingResult.hasErrors()) 
				 return "contract/update";
			 contractDao.updateContract(contract);
			 return "redirect:list"; 
		}    

	   @RequestMapping(value="/delete/{id}")
		public String processDelete(@PathVariable String id) {
		   contractDao.deleteContract(id);
	       return "redirect:../list"; 
		}

}
