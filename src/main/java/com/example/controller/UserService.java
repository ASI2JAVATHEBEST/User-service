package com.cpe.springboot.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpe.springboot.card.Controller.CardModelService;
import com.cpe.springboot.card.model.CardModel;
import com.cpe.springboot.user.model.UserModel;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CardModelService cardModelService;

	public List<UserModel> getAllUsers() {
		List<UserModel> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return userList;
	}

	public Optional<UserModel> getUser(String id) {
		return userRepository.findById(Integer.valueOf(id));
	}

	public Optional<UserModel> getUser(Integer id) {
		return userRepository.findById(id);
	}

	public void addUser(UserModel user) {
		// needed to avoid detached entity passed to persist error
		userRepository.save(user);
		List<CardModel> cardList=cardModelService.getRandCard(5);
		for(CardModel card: cardList) {
			user.addCard(card);
		}
		userRepository.save(user);
	}

	public void updateUser(UserModel user) {
		userRepository.save(user);

	}

	public void deleteUser(String id) {
		userRepository.deleteById(Integer.valueOf(id));
	}

	public List<UserModel> getUserByLoginPwd(String login, String pwd) {
		List<UserModel> ulist=null;
		ulist=userRepository.findByLoginAndPwd(login,pwd);
		return ulist;
	}

}
