package com.deppon.foss.print.labelprint.impl;

public class ecWebBridge
{
	static
	{
	  System.loadLibrary("ecWebBridge"); //The native Java bridge dll is ecWebBridge.dll
	}
	
	public 	ecWebBridge(String driverName) 
	{
			m_driverName = driverName ;
			m_driverExists	= checkDriver(m_driverName) ;
			if(m_driverExists)
			m_driverLoaded 	= loadDriver(m_driverName);
	};
	
	public 	boolean	getDriverExists() {return m_driverExists; } ;
	public	boolean getDriverLoaded() {return m_driverLoaded; } ;
	public	String  getDriverName()	{ return m_driverName; } ;

//////////////////////////////////////////////////////////////////
// Native functions
	public 	native	boolean		openPort(String printerName) ;
	public	native	boolean		beginJob(int a,int b,int c,int d,int e,int f);
	public 	native	boolean		sendCommand(String command) ;
	public	native 	boolean		intLoadImage(String fileName,String idName,String imageType);
	public	native	boolean		extLoadImage(String fileName,String idName,String imageType);
	public	native	boolean		closePort() ;
	public	native	boolean		endJob() ;
	public	native	boolean		ecTextOut(int x,int y,int b , String c,String d) ;
	public	native	int 			getJobCount() ;
	public	native	boolean		ecTextOutR(int x,int y,int b,String c,String d,int e,int f,int g) ;
	public	native	boolean		ecTextDownLoad(int b,String c,String d,int e,int f,int g,String name) ;
	public 	native	boolean 	putImage(int x,int y ,String fileName,int degree) ;
	public	native	boolean 	downloadImage(String fileName,int degree,String name) ;
	public	native	boolean 	ecTextOutFine(int x,int y ,int b ,String c,String d,int e,int f,int g,int h,int i,int j,int k) ;
	
	public	native	boolean		formFeed() ;
	public	native	boolean		printFont(String a,String b,String c,String d) ;
	public	native	boolean		printBar(String a,String b,String c,String d ,String e,String f,String g) ;
	public	native	boolean		forward(String no) ;
	public	native	boolean		backward(String no) ;
	public	native	boolean		cutter() ;
	public	native	boolean		strip_on() ;
	public	native	boolean		strip_off() ;
	
	public	native 	String 		getTraceFilePath() ;
	public	native	void			setTraceFilePath(String FileName) ;
	public	native	boolean		getTrace() ;
	public	native	void			setTrace(boolean Enable) ;
	
	public		native 	boolean	checkDriver(String driverName);
	protected	native	boolean	loadDriver(String driverName) ;
	protected	native	boolean	unloadDriver();
	
	protected void finalize()
	{
		if(m_driverLoaded)
		unloadDriver() ;	
		m_driverLoaded = false ;
	}
	
	private	boolean m_driverExists = false ;
	private boolean m_driverLoaded = false ;
	private String m_driverName ;
} ;