/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dajie.common.bean.PageIterator;
import com.dajie.common.util.StringUtil;
import com.dajie.corp.api.CorpServicesContext;
import com.dajie.corp.api.service.CorpService;
import com.dajie.corp.info.model.CorpBase;
import com.dajie.sphsearch.service.JobSearchService;
import com.dajie.wika.service.CorpSearchService;


@Service("corpSearchService")
public class CorpSearchServiceImpl implements CorpSearchService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CorpSearchServiceImpl.class);

    @Override
    public List<String> getCorpName(String key) {
        List<String> corpNames = new ArrayList<String>();
        final int RELATED_COMPANY_SIZE = 5;//默认显示5个
        StringBuilder sb = new StringBuilder();

        CorpService corpService = CorpServicesContext.getInstance().getCorpService();
        if (!StringUtil.isEmpty(key)) {
            PageIterator<Integer> corpIdIterator = JobSearchService.relatedCompany(key, RELATED_COMPANY_SIZE);
            boolean isDisplay = true;

            if (corpIdIterator == null || corpIdIterator.getData() == null || corpIdIterator.getData().size() == 0) {
                LOGGER.warn("JobSearchService.relatedCompany return empty!!!");
            }
            if (corpIdIterator != null && corpIdIterator.getData() != null) {
                if (!corpIdIterator.getData().isEmpty()) {
                    Map<Integer, CorpBase> corpBaseMap = corpService.getCorpBaseSimpleWithCorpIds(corpIdIterator.getData());
                    LOGGER.info("corpService.getCorpBaseSimpleWithCorpIds(corpIdIterator.getData()) return: {}", corpBaseMap.keySet());
                    final Map<Integer, Integer> employeeCountMap = corpService.getEmployeeCountsByCorpIds(new ArrayList<Integer>(corpBaseMap.keySet()));
                    List<Map.Entry<Integer, CorpBase>> entries = new ArrayList<Map.Entry<Integer, CorpBase>>(corpBaseMap.entrySet());
                    //以公司人数倒叙排序
                    Collections.sort(entries, new Comparator<Map.Entry<Integer, CorpBase>>() {
                        @Override
                        public int compare(Map.Entry<Integer, CorpBase> entryOne, Map.Entry<Integer, CorpBase> entryTwo) {
                            Integer numOne = employeeCountMap.get(entryOne.getKey()) == null ? 0 : employeeCountMap.get(entryOne.getKey());
                            Integer numTwo = employeeCountMap.get(entryTwo.getKey()) == null ? 0 : employeeCountMap.get(entryTwo.getKey());
                            return (numTwo - numOne);
                        }
                    });
                    LOGGER.info("List<Map.Entry<Integer, CorpBase>> entries size: ", entries.size());
                    for (Map.Entry<Integer, CorpBase> corpBaseEntry : entries) {
                        CorpBase corpBase = corpBaseEntry.getValue();                     
                        corpNames.add(corpBase.getName());                        
                     }
                }
            }        
        }  
        return corpNames;
    }

}

