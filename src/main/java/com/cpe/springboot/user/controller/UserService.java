package com.cpe.springboot.user.controller;

import java.util.*;

import com.cpe.springboot.card.model.CardReference;
import com.cpe.springboot.user.bus.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.cpe.springboot.card.model.CardModel;
import com.cpe.springboot.user.model.UserEntityModel;

import javax.jms.Message;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BusService busService;

	public List<UserEntityModel> getAllUsers() {
		List<UserEntityModel> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return userList;
	}

	public Optional<UserEntityModel> getUser(String id) {
		return userRepository.findById(Integer.valueOf(id));
	}

	public Optional<UserEntityModel> getUser(Integer id) {
		return userRepository.findById(id);
	}

	public UserEntityModel addUser(UserEntityModel user) {
		// needed to avoid detached entity passed to persist error
		userRepository.save(user);

//		HttpClient httpClient = new HttpClient();
//		List<CardModel> cardList = httpClient.getRandCards();

		busService.sendUser(user.getId(),"channelUserToCard");

		return user;
	}

	public void updateUser(UserEntityModel user) {
		userRepository.save(user);

	}

	public void deleteUser(String id) {
		userRepository.deleteById(Integer.valueOf(id));
	}

	public List<UserEntityModel> getUserByLoginPwd(String login, String pwd) {
		List<UserEntityModel> ulist=null;
		ulist=userRepository.findByLoginAndPwd(login,pwd);
		return ulist;
	}

}
