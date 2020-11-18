package com.cpe.springboot.user.controller;

import java.util.*;

import com.cpe.springboot.http.HttpClient;
import com.cpe.springboot.user.bus.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.cpe.springboot.card.model.CardModel;
import com.cpe.springboot.user.model.UserModel;

import javax.jms.Message;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BusService busService;

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

//		HttpClient httpClient = new HttpClient();
//		List<CardModel> cardList = httpClient.getRandCards();

		busService.sendUser(user.getId(),"channelUserToCard");
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

	@JmsListener(destination = "channelCardToUser", containerFactory = "connectionFactory")
	public void receiveUser(Map user, Message message) {

		System.out.println("[BUSLISTENER] [CHANNEL RESULT_BUS_MNG] RECEIVED String MSG=["+user.toString()+"]");

		Integer id = (Integer)user.get("userId");

		Optional<UserModel> u = getUser(id);
		if(!u.isPresent()){
			return;
		}

		UserModel userModel =u.get();

		List<Map> cardMap = (List)user.get("cardMap");

		Set<CardModel> cardList = new HashSet<>();

		for(Map<String, Object> cardModelMap: cardMap) {
			CardModel cardModel = new CardModel();
			cardModel.setId((Integer)cardModelMap.get("id"));
			cardModel.setEnergy((float)cardModelMap.get("energy"));
			cardModel.setHp((float)cardModelMap.get("hp"));
			cardModel.setDefence((float)cardModelMap.get("defence"));
			cardModel.setAttack((float)cardModelMap.get("attack"));
			cardModel.setPrice((float)cardModelMap.get("price"));
			cardModel.setUser(userModel);
			cardList.add(cardModel);
		}

		userModel.setCardList(cardList);

		userRepository.save(userModel);

	}

}
