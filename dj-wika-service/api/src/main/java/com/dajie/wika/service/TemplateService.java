package com.dajie.wika.service;

import java.util.List;

import com.dajie.wika.model.UserQRTemplate;
import com.dajie.wika.model.UserWikaTemplate;
import com.dajie.wika.model.WikaTemplate;

public interface TemplateService {

	public WikaTemplate getCurrentWikaTemplate(int userId);
	
    public List<UserWikaTemplate> getWikaTemplates(int userId);

    public List<UserQRTemplate> getQRTemplates(int userId);
    
    public int unlockWikaTemplate(int userId, int WTId);
    
    public int unlockQRTemplate(int userId, int QTId);
	
}
