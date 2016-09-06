package com.niit.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.model.Category;

@Controller
public class CategoryController {
@Autowired
private CategoryDAO categoryDAO;
@Autowired
private Category category;
@RequestMapping(value="/categories",method=RequestMethod.GET)
public String listCategories(Model model){
	model.addAttribute("category", category);
	model.addAttribute("categoryList", this.categoryDAO.list());
	return "category";
	}
@RequestMapping(value="/category/add",method=RequestMethod.POST)
public String addCategory(@ModelAttribute("category") Category category){
	ModelAndView mv=new ModelAndView();
	if (categoryDAO.get("id")==null){
		categoryDAO.save(category);
		}
	else{
		mv.addObject("error message", "The record exist wid this "+category.getId());
	}
	return "category";
	
}
@RequestMapping("category/remove/{id}")
public ModelAndView deleteCategory(@PathVariable("id") String id)
{
	boolean flag = categoryDAO.delete(category);
	ModelAndView mv = new ModelAndView("category");
	String msg = "successfullydone the operation";
	if(flag!=true)
	{
		msg="the operation was not success";
		
	}
	mv.addObject("msg", msg);
	return mv;
}







}


