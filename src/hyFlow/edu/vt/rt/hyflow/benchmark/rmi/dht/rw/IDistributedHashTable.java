package edu.vt.rt.hyflow.benchmark.rmi.dht.rw;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IDistributedHashTable extends Remote, Serializable{

	public Integer get(Object key) throws RemoteException;
	public void put(Object key, Integer value) throws RemoteException;
	
	public void rLock(Object key) throws RemoteException, InterruptedException;
	public void rUnlock(Object key) throws RemoteException;
	
	public void wLock(Object key) throws RemoteException, InterruptedException;
	public void wUnlock(Object key) throws RemoteException;
	
}
