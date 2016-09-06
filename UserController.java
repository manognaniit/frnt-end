package com.niit.shoppingcart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.UserDetailsDAO;
import com.niit.shoppingcart.model.Category;
import com.niit.shoppingcart.model.UserDetails;

@Controller
public class UserController {
	@Autowired
	UserDetailsDAO userDetailsDAO;
	@Autowired
	UserDetails userDetails;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private Category category;
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value="id")String id,@RequestParam(value="password")String password,HttpSession session){
	ModelAndView mv=new ModelAndView("Home");
	String msg;
	userDetails=userDetailsDAO.isValidUser(id,password);
			if(userDetails == null){
		msg="invalid user...please try again";
	}
			else
			{
				if(userDetails.getRole().equals("ROLE_ADMIN"))
				{
					mv=new ModelAndView("adminHome");
				}
				else {
					session.setAttribute("msg",userDetails.getName());
					session.setAttribute("userid",userDetails.getId());
					}
				}
			return mv;
}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,HttpSession session){
		ModelAndView mv=new ModelAndView("/Home");
		session.invalidate();
		session=request.getSession(true);
		session.setAttribute("category", category);
		session.setAttribute("categoryList", categoryDAO.list());
		mv.addObject("logout message", "you are successfully logged out");
		mv.addObject("logged out", "true");
		return mv;
		
	}
	}
	



