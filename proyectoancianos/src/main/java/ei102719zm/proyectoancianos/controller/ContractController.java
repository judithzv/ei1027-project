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
	      return "contracts/list";
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

}
