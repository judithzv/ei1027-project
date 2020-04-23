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

import ei102719zm.proyectoancianos.dao.CompanyDao;
import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;


@Controller
@RequestMapping("/company") 
public class CompanyController {
	 private CompanyDao companyDao;

	   @Autowired
	   public void setCompanyDao(CompanyDao companyDao) { 
	       this.companyDao = companyDao;
	   }
	   @RequestMapping("/list")
	   public String listCompany(Model model) {
	      model.addAttribute("companies", companyDao.getCompanies());
	      return "company/list";
	   }
	   
	   @RequestMapping(value="/add")
	   public String addCompany(HttpSession session, Model model) {
		   model.addAttribute("company", new Company());
		   return "company/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("company") Company company,
	                                   BindingResult bindingResult) {  
		   if (bindingResult.hasErrors()) 
		   	return "company/add";
	   	 companyDao.addCompany(company);
	   	 session.setAttribute("company", company);
	   	 return "redirect:list"; 
	   	 
	    }

	   @RequestMapping(value="/update/{CIF}", method = RequestMethod.GET) 
	 		public String editCompany(Model model, @PathVariable String CIF) { 
	 			model.addAttribute("company", companyDao.getCompany(CIF));
	 			return "company/update"; 
	 		}
	 	    @RequestMapping(value="/update", method = RequestMethod.POST) 
	 		public String processUpdateSubmit(
	 	                            @ModelAttribute("company") Company company, 
	 	                            BindingResult bindingResult) {
	 			 if (bindingResult.hasErrors()) 
	 				 return "company/update";
	 			 companyDao.updateCompany(company);
	 			 return "redirect:list"; 
	 		}    

	 	   @RequestMapping(value="/delete/{CIF}")
	 		public String processDelete(@PathVariable String CIF) {
	 		   companyDao.deleteCompany(CIF);
	 	       return "redirect:../list"; 
	 		}

}
