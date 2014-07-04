/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Aug 21, 2013 6:59:22 PM
 */

@Repository
public class JedisCacheUtil {

    private static final Logger logger = LoggerFactory.getLogger(JedisCacheUtil.class);

    @Autowired
    @Qualifier("wikaJedisPool")
    private JedisPool pool;

    public JedisCacheUtil() {
    }

    /**
     * @return the pool
     */
    private JedisPool getPool() {
        return pool;
    }

    public Jedis getJedis() {
        Jedis jedis = getPool().getResource();
        return jedis;
    }

    public void returnJedis(Jedis jedis) {
        getPool().returnResource(jedis);
    }

    @Override
    protected void finalize() throws Throwable {
        getPool().destroy();
        super.finalize();
    }

    public void setObject(String key, Object value) {
        Jedis jedis = getJedis();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                byte[] bs = bos.toByteArray();
                jedis.set(key.getBytes(), bs);
            } catch (IOException e) {
                logger.error("jedis cache util error!", e);
            }
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
    }

    public void setObject(String key, Object value, int seconds) {
        Jedis jedis = getJedis();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            try {
                oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                byte[] bs = bos.toByteArray();
                jedis.setex(key.getBytes(), seconds, bs);
            } catch (IOException e) {
                logger.error("jedis cache util error!", e);
            }
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
    }

    public Object getObject(String key) {
        Jedis jedis = getJedis();
        try {
            try {
                byte[] bi = jedis.get(key.getBytes());
                if (bi != null) {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bi, 0,
                            bi.length));
                    return ois.readObject();
                }
            } catch (IOException e) {
                logger.error("jedis cache util error!", e);
            } catch (ClassNotFoundException e) {
                logger.error("jedis cache util error!", e);
            }
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
        return null;
    }

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            if (jedis != null) returnJedis(jedis);
        }
    }

    public void set(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            if (jedis != null) returnJedis(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            if (jedis != null) returnJedis(jedis);
        }
        return null;
    }
    
    public void setIncr(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.incr(key);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            if (jedis != null) returnJedis(jedis);
        }

    }

    public void delete(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
    }

    public double zscore(String key,String value) {
        double score=0.0;
        Jedis jedis = getJedis();
        try {
            score=  jedis.zscore(key, value);
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
        return score;
    }
    
    public String zrange(String key) {
        Set<String> codeSet=null;
        Jedis jedis = getJedis();
        String code=null;
        try {
            codeSet=  jedis.zrevrange(key,0,1);
            if(codeSet!=null && !codeSet.isEmpty()){
                Iterator<String> iter = codeSet.iterator();
                while (iter.hasNext()) {
                     code = iter.next();
                }
            }
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
        return code;
    }
    
    public void incr(String key,String value) {
        Jedis jedis = getJedis();
        try { 
  
            jedis.zincrby(key, 1, value);
            
            try{
                double  score=  jedis.zscore(key, value);
                if(score==1.0){
                    jedis.expire(key, 60*5); 
                }
           }catch(Exception e){
               logger.error("jedis.zscore error!"+e);
           }
            
            
        } catch (Exception e) {
            logger.error("jedis cache util error!", e);
        } finally {
            returnJedis(jedis);
        }
    }
    
    public Long incr(String key) {
        Jedis jedis = getJedis();
        Long result=null;
        try { 
            result=jedis.incr(key);
        } catch (Exception e) {
            logger.error("jedis  incr error", e);
        } finally {
            returnJedis(jedis);
        }
        return result;
    }
    
    public boolean setnx(String key,String value) {
        Jedis jedis = getJedis();
        Long result=null;
        try { 
            result=jedis.setnx(key, value);
        } catch (Exception e) {
            logger.error("jedis setnx error", e);
        } finally {
            returnJedis(jedis);
        }
        return (result.equals(0))?false:true;
    }
    
}
