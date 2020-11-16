package com.cpe.springboot.user.bus;

import com.cpe.springboot.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class BusService {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMsg(String msg) {
        System.out.println("[BUSSERVICE] SEND String MSG=["+msg+"]");
        jmsTemplate.convertAndSend("RESULT_BUS_MNG",msg);
    }

    public void sendMsg(String msg, String busName) {
        System.out.println("[BUSSERVICE] SEND String MSG=["+msg+"] to Bus=["+msg+"]");
        jmsTemplate.convertAndSend(busName,msg);
    }

    public void sendUser(UserModel user, String busName) {
        System.out.println("[BUSSERVICE] SEND String MSG=["+user.toString()+"] to Bus=["+user.toString()+"]");
        jmsTemplate.convertAndSend(busName,user);
    }
}