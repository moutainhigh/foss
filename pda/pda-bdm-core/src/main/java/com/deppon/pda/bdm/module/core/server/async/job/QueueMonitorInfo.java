package com.deppon.pda.bdm.module.core.server.async.job;

public class QueueMonitorInfo {
	public static long readCount;
	public static long activeReadCount;
	public static long totalReadTime;
	public static long totalActiveReadTime;
	public static long lastReadTime;
	
	public static long putCount;
	public static long totalPutTime;
	
	public static long getCount;
	public static long activeGetCount;
	public static long totalGetTime;
	public static long totalActiveGetTime;
	
	public static long syncCount;
	public static long totalSyncTime;
	public static long totalFossTime;
	
	public static long commitTime;
	public static long totalCommitTime;
	
	public static void clean(){
		readCount=0;
		activeReadCount=0;
		totalReadTime=0;
		totalActiveReadTime=0;
		lastReadTime=0;
		putCount=0;
		totalPutTime=0;
		getCount=0;
		activeGetCount=0;
		totalGetTime=0;
		totalActiveGetTime=0;
		syncCount=0;
		totalSyncTime=0;
		totalFossTime=0;
		commitTime=0;
		totalCommitTime=0;
	}
	public  static synchronized void addReadCount(){
		readCount++;
	}
	 
	public static synchronized void addTotalReadTime(long readTime){
		totalReadTime += readTime;
	}
	
	public static synchronized void addActiveReadCount(){
		activeReadCount++;
	}
	public static synchronized void addTotalActiveReadTime(long readTime){
		totalActiveReadTime += readTime;
	}
	public static synchronized void addLastReadTime(long readTime){
		lastReadTime+=readTime;
	}
	public static synchronized void addPutCount(){
		putCount++;
	}
	public static synchronized void addTotalPutTime(long time){
		totalPutTime+=time;
	}
	
	public static synchronized void addGetCount(){
		getCount++;
	}
	
	public static synchronized void addActiveGetCount(){
		activeGetCount++;
	}
	
	public static synchronized void addTotalGetTime(long time){
		totalGetTime += time;
	}
	public static synchronized void addTotalActiveGetTime(long time){
		totalActiveGetTime += time;
	}
	public static synchronized void addSyncCount(){
		syncCount++;
	}
	public static synchronized void addTotalSyncTime(long time){
		totalSyncTime += time;
	}
	public static synchronized void addTotalFossTime(long time){
		totalFossTime += time;
	}
	public static synchronized void addCommitTime(){
		commitTime++;
	}
	public static synchronized void addTotalCommitTime(long time){
		totalCommitTime += time;
	}
	public static long getAverageReadTime(){
		return totalReadTime==0?0:totalReadTime/readCount;
	}
	public static long getAcerageActiveReadTime(){
		return totalActiveReadTime==0?0:totalActiveReadTime/activeReadCount;
	}
	public static long getAverageGetTime(){
		return totalGetTime==0?0:totalGetTime/getCount;
	}
	public static long getAcerageActiveGetTime(){
		return totalActiveGetTime==0?0:totalActiveGetTime/activeGetCount;
	}
	public static long getAveragePutTime(){
		return totalPutTime==0?0:totalPutTime/putCount;
	}
	public static long getAverageSyncTime(){
		return totalSyncTime==0?0:totalSyncTime/syncCount;
	}
	public static long getAverageCommitTime(){
		return totalCommitTime==0?0:totalCommitTime/commitTime;
	}
}
