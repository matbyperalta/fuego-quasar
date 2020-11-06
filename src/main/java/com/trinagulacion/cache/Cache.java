package com.trinagulacion.cache;

public interface Cache<K, V> {

	public void put(K key, V value);
	public V get(K key);
	public void clear();
	public void cleanUp();
	
	
}


