package com.cpe.springboot.user.controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cpe.springboot.user.model.UserEntityModel;

public interface UserRepository extends CrudRepository<UserEntityModel, Integer> {
	
	List<UserEntityModel> findByLoginAndPwd(String login, String pwd);

}
