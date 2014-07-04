/**
 * $Id: ScheduleTaskSupport.java 110445 2012-10-24 07:42:23Z wei.cheng@dajie-inc.com $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service;

import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义调度
 * 
 * @author Marshal(shuai.ma@opi-corp.com) Initial Created at 2012-3-2
 */
public class ScheduleTaskSupport extends TimerTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTaskSupport.class);

    private int times = -1;

    private Map<Runnable, Integer> taskMap;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        if (taskMap == null || taskMap.isEmpty()) {
            return;
        }
        times++;
        if (times < 0) {
            times = 0;
        }
        for (Runnable task : taskMap.keySet()) {
            int period = taskMap.get(task);
            if (period < 2 || times % period == 0) {
                try {
                    task.run();
                } catch (Throwable t) {
                    logger.error("run() - exception ignored", t); //$NON-NLS-1$
                }
            }
        }
    }

    public void setTaskMap(Map<Runnable, Integer> taskMap) {
        this.taskMap = taskMap;
    }

}
