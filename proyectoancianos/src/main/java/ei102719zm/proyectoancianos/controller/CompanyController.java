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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ei102719zm.proyectoancianos.dao.CompanyDao;
import ei102719zm.proyectoancianos.dao.ContractDao;
import ei102719zm.proyectoancianos.dao.ElderlyDao;
import ei102719zm.proyectoancianos.dao.UserDao;
import ei102719zm.proyectoancianos.model.Company;
import ei102719zm.proyectoancianos.model.Contract;
import ei102719zm.proyectoancianos.model.Elderly;


@Controller
@RequestMapping("/company") 
public class CompanyController {
	 private CompanyDao companyDao;
	 private ContractDao contractDao;
	 private ElderlyDao elderlyDao;
	 private UserDao userDao;

	   @Autowired
	   public void setCompanyDao(CompanyDao companyDao) { 
	       this.companyDao = companyDao;
	   }
	   @Autowired
	   public void setContractDao(ContractDao contractDao) { 
	       this.contractDao = contractDao;
	   }
	   @Autowired
	   public void setElderlyDao(ElderlyDao elderlyDao) { 
	       this.elderlyDao = elderlyDao;
	   }
	   
		@Autowired
		 public void setUserDao(UserDao userDao) { 
			this.userDao = userDao;
		}
	   
	   @RequestMapping("/list")
	   public String listCompany(HttpSession session, Model model) {
	      model.addAttribute("companies", companyDao.getCompanies());
	      session.setAttribute("nextURL", "redirect:list");
	      session.setAttribute("back", "../list");
	      return "company/list";
	   }
	   
	   @RequestMapping(value="/add")
	   public String addCompany(HttpSession session, Model model) {
		   Company company = new Company();
		   Contract contract = (Contract) session.getAttribute("contract");
		   if(contract != null) {
			   company.setCIF(contract.getCIF());
			   session.removeAttribute("contract");
		   }
		   model.addAttribute("company", company);
		   return "company/add";
	   }
	   
	   @RequestMapping(value="/add", method=RequestMethod.POST) 
	   public String processAddSubmit(HttpSession session, @ModelAttribute("company") Company company,
	                                   BindingResult bindingResult) {  
		   
		   CompanyValidator companyValidator = new CompanyValidator();
		   companyValidator.validate(company, bindingResult);
		   
		   if (companyDao.getCompany(company.getCIF()) != null)
			   bindingResult.rejectValue("CIF", "CIF", company.getCIF() +" is already in use");
		   
		   if(userDao.userAlreadyExists(company.getUserName()))
			   bindingResult.rejectValue("userName","userName", company.getUserName()+" is already in use"); 
		   
		   
		   if(bindingResult.hasErrors())
			   return "company/add";
		   
		   companyDao.addCompany(company);
		   
		   if(session.getAttribute("contract") != null)
			   return "redirect:../contract/add";
		   
		   return "redirect:list";    	 
	    }

	   @RequestMapping(value="/update/{CIF}", method = RequestMethod.GET) 
	 		public String editCompany(HttpSession session, Model model, @PathVariable String CIF) { 
	 			model.addAttribute("company", companyDao.getCompany(CIF));
	 			session.setAttribute("userName", companyDao.getCompany(CIF).getUserName());
	 			model.addAttribute("URL", session.getAttribute("back"));
	 			return "company/update"; 
	 		}
	 	    @RequestMapping(value="/update", method = RequestMethod.POST) 
	 		public String processUpdateSubmit(HttpSession session,
	 	                            @ModelAttribute("company") Company company, 
	 	                            BindingResult bindingResult) {
	 			 CompanyValidator companyValidator = new CompanyValidator();
	 			 companyValidator.validate(company, bindingResult);
	 			 
	 			 String userName = (String) session.getAttribute("userName");
	 		  if(!company.getUserName().equals(userName) && userDao.userAlreadyExists(company.getUserName()))
	 			  bindingResult.rejectValue("userName","userName", company.getUserName()+" is already in use"); 
	 		  
	 		   if(bindingResult.hasErrors())
	 			   return "company/update";
	 		   
	 		   session.removeAttribute("userName");
	 		   
	 			 companyDao.updateCompany(company);
	 		   	 String nextURL = (String) session.getAttribute("nextURL");
	 		   	 if (nextURL != null)
	 		   		 return nextURL;
	 		   	 return "redirect:perfil/"+ company.getCIF(); 
	 		}    

	 	   @RequestMapping(value="/delete/{CIF}")
	 		public String processDelete(@PathVariable String CIF, RedirectAttributes redirectAttrs) {
	 		   List<Contract> contracts = companyDao.getContracts(CIF);
			   if(contracts.size() != 0) {
				   // Mostrar mensaje
				   redirectAttrs
		            .addFlashAttribute("mensaje", "User can not be deleted because they have requests and invoices")
		            .addFlashAttribute("clase", "error");
			   }else {
				    redirectAttrs
		            .addFlashAttribute("mensaje", "Successfully deleted")
		            .addFlashAttribute("clase", "success"); 
			 		 companyDao.deleteCompany(CIF);
			   }
	 		  return "redirect:../list";
	 		}
	 	   
		   @RequestMapping(value="/perfil/{CIF}")
		   public String mostrarPerfil(HttpSession session, @PathVariable String CIF,  Model model) {
			   session.setAttribute("cif", CIF);
			   model.addAttribute("cif", CIF);
			   model.addAttribute("company",companyDao.getCompany(CIF));
			   model.addAttribute("contract", contractDao.getContractsCIF(CIF));
			   session.setAttribute("back", "perfil/"+CIF);
			   return "company/perfil";
		   }
		   @RequestMapping(value="/datos")
		   public String mostrarDatos(HttpSession session, Model model) {
			   String CIF = (String) session.getAttribute("cif");
			   String URL = (String) session.getAttribute("back");
			   model.addAttribute("URL", URL);
			   if(CIF!=null) {
				   Company company = companyDao.getCompany(CIF);
				   model.addAttribute("company",company);
				   return "company/datos";
			   }
			   return "redirect:../";
		   
		   }
		   @RequestMapping(value="/datos/{CIF}")
		   public String mostrarDatos(@PathVariable String CIF, Model model) {
			   Company company= companyDao.getCompany(CIF);
			   model.addAttribute("company",company);
			   model.addAttribute("URL", "../../contract/list");
			   return "company/datos";
		   
		   }
		   @RequestMapping(value="/datoscontrato")
		   public String mostrarDatosContrato(HttpSession session, Model model) {
			   String CIF = (String) session.getAttribute("cif");
			   model.addAttribute("CIF",CIF);
			   if(CIF!=null) {
				   List<Contract> contract = contractDao.getContractsCIF(CIF);
				   model.addAttribute("contracts",contract);
				   return "company/datoscontrato";
			   }
			   return "redirect:../";
		   
		   }
		   @RequestMapping(value="/eliminarcuenta")
		   public String eliminarcuenta(HttpSession session, Model model) {
			   String CIF = (String) session.getAttribute("cif");
			   String id = (String) session.getAttribute("id");
			   if(CIF!=null) {
				   Company company= companyDao.getCompany(CIF);
				   model.addAttribute("company",company);
				   Contract contract = contractDao.getContract(id);
				   model.addAttribute("contract",contract);
				   return "company/eliminarcuenta";
			   }
			   return "redirect:../";
		   }
		   @RequestMapping(value="/datoselderly")
		   public String datoselderly(HttpSession session, Model model) {
			   String CIF = (String) session.getAttribute("cif");
			   model.addAttribute("CIF",CIF);
			   List<Contract> contratos=contractDao.getContractsCIF(CIF);
			   List<Elderly> elderlies = new ArrayList<Elderly>();
			   for (Contract contract: contratos) {
				   List<Elderly> elderly = elderlyDao.getElderliesID(contract.getId());
				   elderlies.addAll(elderly);
			   }
			   model.addAttribute("elderlies", elderlies);
			   return "company/datoselderly";

		   }
}
