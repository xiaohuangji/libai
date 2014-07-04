package com.dajie.wika.service.stub;

import java.util.List;

import com.dajie.wika.model.Message;
import com.dajie.wika.model.wrapper.MessageWrapper;

public interface MessageServiceStub {

	public List<MessageWrapper> getMessage(int userId,long timestamp,int limit);
	
}
