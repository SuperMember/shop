package com.goods.logistics.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.goods.manager.dao.JedisClient;

import redis.clients.jedis.JedisCluster;



//µ¥»ú°æredis
public class JedisClientCluster implements JedisClient {

	@Autowired
	private JedisCluster jedisCluster;
	

	public String get(String key) {
		return jedisCluster.get(key);
	}

	
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}


	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	
	public long incr(String key) {
		return jedisCluster.incr(key);
	}


	public long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}


	public long del(String key) {
		
		return jedisCluster.del(key);
	}


	public long hdel(String hkey, String key) {
		
		return jedisCluster.hdel(hkey, key);
	}

}
