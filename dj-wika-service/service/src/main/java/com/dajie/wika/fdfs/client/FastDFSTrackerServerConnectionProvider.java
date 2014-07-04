/**
 * 
 */
package com.dajie.wika.fdfs.client;

import org.csource.fastdfs.TrackerServer;

/**
 * Thrift 客户端连接池
 * 
 * @author <a href='mailto:lizheng8318@gmail.com'>lizheng</a>
 *
 */
public interface FastDFSTrackerServerConnectionProvider {

    /**
     * 取链接池中的一个链接
     * 
     * @return
     */
    public TrackerServer getConnection();
    
    /**
     * 返回链接
     * 
     * @param socket
     */
    public void returnCon(TrackerServer trackerServer);
    
    /**
     * 释放连接
     * @param trackerServer
     */
    public void invalidateCon(TrackerServer trackerServer);
}
