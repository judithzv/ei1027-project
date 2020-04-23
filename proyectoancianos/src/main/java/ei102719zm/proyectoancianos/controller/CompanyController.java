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
	   
	   @RequestMapping(value="/add/{CIF}")
	   public String addCompany(HttpSession session, Model model, @PathVariable String CIF) {
		   if(session.getAttribute("user") == null) {
			   session.setAttribute("nextURL", "/company/add/"+CIF);
			   return "redirect:../../login";
		   }
		   Company company = companyDao.getCompany(CIF);
		   if(company != null) {
			   session.setAttribute("company", company);
			   return "contract/add";
		   }
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
	   	 return "redirect:../contract/add"; 
	   	 
	    }


}
