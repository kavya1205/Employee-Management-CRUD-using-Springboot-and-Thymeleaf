package com.employeemanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employeemanagement.entity.Employee;
import com.employeemanagement.repository.EmployeeRepository;

@Controller
@RequestMapping("/employees/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("signup")
	public String showAddForm(Employee employee) {
		return "add-employee";
	}
	
	@GetMapping("list")
	public String showUpdate(Model model) {
		model.addAttribute("employees", employeeRepository.findAll());
		return "index";
	}
	
	@PostMapping("add")
	public String addEmp(@Valid Employee employee,BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "add-employee";
		}
		employeeRepository.save(employee);
		return "redirect:list";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdate(@PathVariable("id") int id, Model model) {
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
	 model.addAttribute("employee",employee);
	 return "update-employee";
	}

	
	@PostMapping("/update/{id}")
	public String updateEmployee(@PathVariable("id") int id,@Valid Employee employee, BindingResult result,Model model) {
		if(result.hasErrors()) {
			employee.setId(id);
			return "update-employee";
		}
		employeeRepository.save(employee);
		model.addAttribute("employees", employeeRepository.findAll());
	   return "index";
	   
	}
	
	@GetMapping("delete/{id}")
	public String deleteEmployee(@PathVariable("id") int id,Model model) {
		Employee employee=employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
		employeeRepository.delete(employee);
		model.addAttribute("employees", employeeRepository.findAll());
		return "index";
	}
	
	
}
