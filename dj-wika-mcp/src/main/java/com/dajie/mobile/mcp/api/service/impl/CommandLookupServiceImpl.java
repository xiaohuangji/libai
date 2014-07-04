/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service.impl;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.mobile.mcp.api.command.ApiCommand;
import com.dajie.mobile.mcp.api.service.CommandLookupService;

/**
 * @author wei.cheng
 * 
 */
public class CommandLookupServiceImpl implements CommandLookupService {

    private static final Logger logger = LoggerFactory.getLogger(CommandLookupServiceImpl.class);

    private Map<String, ApiCommand> apiMapByConfig;

    private Set<String> ticketUnnecessaryApiSet;

    private String[] allCommands = new String[0];

    private String[] xmlCommands = new String[0];

    @Override
    public ApiCommand lookupApiCommand(String methodValue) {
        if (this.apiMapByConfig == null) {
            return null;
        }
        return this.apiMapByConfig.get(methodValue);
    }

    @Override
    public boolean isNeedLogin(String methodValue) {
        if (this.ticketUnnecessaryApiSet == null) {
            return true;
        }
        if (this.ticketUnnecessaryApiSet.contains(methodValue)) {
            return false;
        }
        return true;
    }

    @Override
    public String[] getCommands() {
        return this.allCommands;
    }

    public void setApiMapByConfig(Map<String, ApiCommand> apiMapByConfig) {
        this.apiMapByConfig = apiMapByConfig;
        if (apiMapByConfig != null) {
            xmlCommands = apiMapByConfig.keySet().toArray(new String[0]);
            this.allCommands = new String[this.xmlCommands.length];
            for (int i = 0; i < xmlCommands.length; i++) {
                this.allCommands[i] = this.xmlCommands[i];
            }
            logger.info("CommandLookupServiceImpl setApiMapByConfig apiMapByConfig:"
                    + apiMapByConfig.size() + " allCommands:" + allCommands.length);
        }
    }

    public void setTicketUnnecessaryApiSet(Set<String> ticketUnnecessaryApiSet) {
        this.ticketUnnecessaryApiSet = ticketUnnecessaryApiSet;
        logger.info("CommandLookupServiceImpl setApiMapByConfig ticketUnnecessaryApiSet:"
                + ticketUnnecessaryApiSet);
    }

}
