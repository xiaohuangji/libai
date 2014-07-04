package com.dajie.wika.model.message;

import java.util.List;

/**
 * 解锁新的二维码模板
 * 新解锁的二维码模板id
 * @author xing.feng
 *
 */
public class MsgPayload69 extends MsgPayload{

	private List<Integer> templateId;
	
	public MsgPayload69(int...ids){
		if(ids!=null){
			for(int id:ids){
				templateId.add(id);
			}
		}
	}

	public List<Integer> getTemplateId() {
		return templateId;
	}

	public void setTemplateId(List<Integer> templateId) {
		this.templateId = templateId;
	}
	
	
}
