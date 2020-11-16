package com.cpe.springboot.user.model;

import com.cpe.springboot.card.model.CardModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "user_model")
public class UserModel implements Serializable {

	private static final long serialVersionUID = 2733795832476568049L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "login")
	private String login;
	@Column(name = "pwd")
	private String pwd;
	@Column(name = "account")
	private float account;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "sur_name")
	private String surName;
	@Column(name = "email")
	private String email;


	@OneToMany(cascade = CascadeType.ALL,
			mappedBy = "user")
	private Set<CardModel> cardList = new HashSet<>();

	public UserModel() {
		this.login = "";
		this.pwd = "";
		this.lastName="lastname_default";
		this.surName="surname_default";
		this.email="email_default";
	}

	public UserModel(String login, String pwd) {
		super();
		this.login = login;
		this.pwd = pwd;
		this.lastName="lastname_default";
		this.surName="surname_default";
		this.email="email_default";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Set<CardModel> getCardList() {
		return cardList;
	}

	public void setCardList(Set<CardModel> cardList) {
		this.cardList = cardList;
	}

	public void addAllCardList(Collection<CardModel> cardList) {
		this.cardList.addAll(cardList);
	}


	public void addCard(CardModel card) {
		this.cardList.add(card);
		card.setUser(this);
	}

	private boolean checkIfCard(CardModel c){
		for(CardModel c_c: this.cardList){
			if(c_c.getId()==c.getId()){
				return true;
			}
		}
		return false;
	}

	public float getAccount() {
		return account;
	}

	public void setAccount(float account) {
		this.account = account;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
