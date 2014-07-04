/**
 * 
 */
package com.dajie.wika.fdfs.client;

import java.net.InetSocketAddress;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * FastDFS 客户端连接池实现
 * 
 * @author <a href='mailto:lizheng8318@gmail.com'>lizheng</a>
 *
 */
public class FastDFSGenericConnectionProvider implements FastDFSTrackerServerConnectionProvider, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(FastDFSGenericConnectionProvider.class);
	
    /** 可以从缓存池中分配对象的最大数量 */
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
    /** 缓存池中最大空闲对象数量 */
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
    /** 缓存池中最小空闲对象数量 */
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
    /** 阻塞的最大数量 */
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
    /** 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
    /** 对象缓存池 */
    private ObjectPool<TrackerServer> objectPool = null;
    
	/**
	 * 
	 */
	public FastDFSGenericConnectionProvider() {
	}

	/* (non-Javadoc)
	 * @see com.dajie.framework.rpc.thrift.ConnectionProvider#getConnection()
	 */
	@Override
	public TrackerServer getConnection() {
		try {
			TrackerServer socket = objectPool.borrowObject();
			return socket;
		} catch (Exception e) {
			logger.error("error getConnection", e);
			throw new RuntimeException("error getConnection", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dajie.framework.rpc.thrift.ConnectionProvider#returnCon(org.apache.thrift.transport.TSocket)
	 */
	@Override
	public void returnCon(TrackerServer socket) {
		try {
			objectPool.returnObject(socket);
		} catch (Exception e) {
			logger.error("error returnCon", e);
			throw new RuntimeException("error returnCon", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dajie.framework.res.api.fastdfs.pool.FastDFSTrackerServerConnectionProvider#invalidateCon(org.csource.fastdfs.TrackerServer)
	 */
	@Override
	public void invalidateCon(TrackerServer trackerServer) {
		try {
			objectPool.invalidateObject(trackerServer);
		} catch (Exception e) {
			logger.error("error invalidateCon", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		try {
			logger.info("close thrift client pool...");
			objectPool.close();
			logger.info("thrift client pool closed.");
		} catch (Exception e) {
			throw new RuntimeException("erorr destroy()", e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// 初始化fastDFS配置
		initFastDFSConfig();
        // 工厂方法
        FastDFSTrackerServerFactory fastDFSPoolableObjectFactory = new FastDFSTrackerServerFactory();
        // 对象池配置
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxActive = maxActive;
        config.maxIdle = maxIdle;
        config.minIdle = minIdle;
        config.maxWait = maxWait;
        config.testOnBorrow = testOnBorrow;
        config.testOnReturn = testOnReturn;
        config.testWhileIdle = testWhileIdle;
        config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
        // 对象池
        objectPool = new GenericObjectPool<TrackerServer>(fastDFSPoolableObjectFactory, config);
	}

	/**
	 * 初始化fastDFS
	 */
	public void initFastDFSConfig() {
		// 不使用文件方式初始化 ClientGlobal.init(conf_filename);
		ClientGlobal.setG_connect_timeout(ClientGlobal.DEFAULT_CONNECT_TIMEOUT * 1000);
		ClientGlobal.setG_network_timeout(ClientGlobal.DEFAULT_NETWORK_TIMEOUT * 1000);
		ClientGlobal.setG_charset("ISO8859-1");
		
		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		// TODO fastDFS tracker servers配置化
		tracker_servers[0] = new InetSocketAddress("10.10.64.153", 22122);
		ClientGlobal.setG_tracker_group(new TrackerGroup(tracker_servers));
		
		ClientGlobal.setG_tracker_http_port(80);
		ClientGlobal.setG_anti_steal_token(false);
	}

	/**
	 * @return the maxActive
	 */
	public int getMaxActive() {
		return maxActive;
	}

	/**
	 * @param maxActive the maxActive to set
	 */
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * @return the maxIdle
	 */
	public int getMaxIdle() {
		return maxIdle;
	}

	/**
	 * @param maxIdle the maxIdle to set
	 */
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * @return the minIdle
	 */
	public int getMinIdle() {
		return minIdle;
	}

	/**
	 * @param minIdle the minIdle to set
	 */
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * @return the maxWait
	 */
	public long getMaxWait() {
		return maxWait;
	}

	/**
	 * @param maxWait the maxWait to set
	 */
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * @return the testOnBorrow
	 */
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	/**
	 * @param testOnBorrow the testOnBorrow to set
	 */
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	/**
	 * @return the testOnReturn
	 */
	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	/**
	 * @param testOnReturn the testOnReturn to set
	 */
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	/**
	 * @return the testWhileIdle
	 */
	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	/**
	 * @param testWhileIdle the testWhileIdle to set
	 */
	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	/**
	 * @return the objectPool
	 */
	public ObjectPool<TrackerServer> getObjectPool() {
		return objectPool;
	}

	/**
	 * @param objectPool the objectPool to set
	 */
	public void setObjectPool(ObjectPool<TrackerServer> objectPool) {
		this.objectPool = objectPool;
	}
}
