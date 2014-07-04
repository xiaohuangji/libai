/**
 * 
 */
package com.dajie.wika.fdfs.client;

import java.io.IOException;
import java.net.Socket;

import org.apache.commons.pool.PoolableObjectFactory;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href='mailto:lizheng8318@gmail.com'>lizheng</a>
 *
 */
public class FastDFSTrackerServerFactory implements PoolableObjectFactory<TrackerServer> {

	/** 日志记录器 */
    public static final Logger logger = LoggerFactory.getLogger(FastDFSTrackerServerFactory.class);
    
    /** tracker client */
    private TrackerClient tracker = new TrackerClient();
    
	/**
	 * 
	 */
	public FastDFSTrackerServerFactory() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
	 */
	@Override
	public TrackerServer makeObject() throws Exception {
		try {
			TrackerServer transport = tracker.getConnection();
			return transport;
		} catch (Exception e) {
			logger.error("error ThriftPoolableObjectFactory()", e);
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	@Override
	public void destroyObject(TrackerServer trackerServer) throws Exception {
		if (trackerServer != null) {
			trackerServer.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
	 */
	@Override
	public boolean validateObject(TrackerServer trackerServer) {
		if (trackerServer == null) {
			return false;
		}
		Socket socket;
		try {
			socket = trackerServer.getSocket();
		} catch (IOException e) {
			logger.warn("Get tracker server sokcet error!", e);
			return false;
		}
		
		if (socket != null && socket.isBound() && !socket.isClosed()
				&& socket.isConnected() && !socket.isInputShutdown()
				&& !socket.isOutputShutdown()) {
			
			try {
				return ProtoCommon.activeTest(socket);
			} catch (IOException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#activateObject(java.lang.Object)
	 */
	@Override
	public void activateObject(TrackerServer obj) throws Exception {
		// DO NOTHING
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#passivateObject(java.lang.Object)
	 */
	@Override
	public void passivateObject(TrackerServer obj) throws Exception {
		// DO NOTHING
	}

}
