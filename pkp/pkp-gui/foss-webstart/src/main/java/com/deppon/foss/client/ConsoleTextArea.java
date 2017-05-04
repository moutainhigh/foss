/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-gui
 * 
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/ConsoleTextArea.java
 * 
 * FILE NAME        	: ConsoleTextArea.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class ConsoleTextArea extends JTextArea {
	
	private static final long serialVersionUID = 1L;
	private static ConsoleTextArea instance = new ConsoleTextArea();
	private static final int NUM_50 = 50 ;
	private static final int NUM_300 = 300 ;
	private static final int NUM_920 = 920 ;
	private static final int NUM_500 = 500 ;
	private static final int NUM_200 = 200 ;
	private static final int NUM_1000 = 1000 ;
	private static final int NUM_850 = 850 ;
	public static ConsoleTextArea getInstance(){
		if(instance==null ){
			instance = new ConsoleTextArea();
		}
		return instance;
	}
	
	private ConsoleTextArea(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	private ConsoleTextArea() {
		try{
			final LoopedStreams ls = new LoopedStreams();
			PrintStream ps = new PrintStream(ls.getOutputStream());
			System.setOut(ps);
			System.setErr(ps);
			startConsoleReaderThread(ls.getInputStream());
		}catch (Exception e) {
			e.printStackTrace();
		}
	} // ConsoleTextArea()

	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				inStream));
		new Thread(new Runnable() {
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					String s;
					Document doc = getDocument();
					while ((s = br.readLine()) != null) {
						boolean caretAtEnd = false;
						caretAtEnd = getCaretPosition() == doc.getLength() ? true
								: false;
						sb.setLength(0);
						append(sb.append(s).append('\n').toString());
						if (caretAtEnd)
							setCaretPosition(doc.getLength());
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "从BufferedReader读取错误："	+ e);
					System.exit(1);
				}
			}
		}).start();
	} // startConsoleReaderThread()

	// 该类剩余部分的功能是进行测试
	public static void main(String[] args) {
		JFrame f = new JFrame("ConsoleTextArea测试");
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
			consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
			f.getContentPane().add(new JScrollPane(consoleTextArea),
					java.awt.BorderLayout.CENTER);
			f.setBounds(NUM_50, NUM_50, NUM_300, NUM_300);
			f.setVisible(true);
			f.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent evt) {
					System.exit(0);
				}
			});
		} catch (Exception e) {
			System.err.println("不能创建LoopedStreams：" + e);
			System.exit(1);
		}

		// 启动几个写操作线程向
		// System.out和System.err输出
		startWriterTestThread("写操作线程 #1", System.err, NUM_920, NUM_50);
		startWriterTestThread("写操作线程 #2", System.out, NUM_500, NUM_50);
		startWriterTestThread("写操作线程 #3", System.out, NUM_200, NUM_50);
		startWriterTestThread("写操作线程 #4", System.out, NUM_1000, NUM_50);
		startWriterTestThread("写操作线程 #5", System.err, NUM_850, NUM_50);
	} // main()

	private static void startWriterTestThread(final String name,
			final PrintStream ps, final int delay, final int count) {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= count; ++i) {
					ps.println("***" + name + ", hello !, i=" + i);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						//to do nothing
					}
				}
			}
		}).start();
	} // startWriterTestThread()
} // ConsoleTextArea