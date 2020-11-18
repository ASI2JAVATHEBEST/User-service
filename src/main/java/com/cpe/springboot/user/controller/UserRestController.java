package com.cpe.springboot.user.controller;

import java.util.List;
import java.util.Optional;

import com.cpe.springboot.user.model.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cpe.springboot.user.model.UserEntityModel;

//ONLY FOR TEST NEED ALSO TO ALLOW CROOS ORIGIN ON WEB BROWSER SIDE
@CrossOrigin
@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/users")
	private List<UserEntityModel> getAllUsers() {
		return userService.getAllUsers();

	}
	
	@RequestMapping("/user/{id}")
	private UserEntityModel getUser(@PathVariable String id) {
		Optional<UserEntityModel> ruser;
		ruser= userService.getUser(id);
		if(ruser.isPresent()) {
			UserEntityModel userEntityModel = ruser.get();
			UserModel userModel = new UserModel();
			userModel.setId(userEntityModel.getId());
			userModel.setLogin(userEntityModel.getLogin());
			return userEntityModel;
		}
		return null;

	}
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	public int addUser(@RequestBody UserEntityModel user) {
		UserEntityModel userEntityModel = userService.addUser(user);
		return userEntityModel.getId();
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/user/{id}")
	public void updateUser(@RequestBody UserEntityModel user, @PathVariable String id) {
		user.setId(Integer.valueOf(id));
		userService.updateUser(user);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/user/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/auth")
	private int getAllCourses(@RequestParam("login") String login, @RequestParam("pwd") String pwd) {
		if( userService.getUserByLoginPwd(login,pwd).size() > 0) {
			return userService.getUserByLoginPwd(login, pwd).iterator().next().getId();
		}
		return 0;
	}


}
