package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.QRTemplate;
import com.dajie.wika.model.WikaTemplate;

public interface TemplateDAO {

	public List<WikaTemplate> getWikaTemplates();
	
	public List<QRTemplate> getQRTemplates();
	
}
