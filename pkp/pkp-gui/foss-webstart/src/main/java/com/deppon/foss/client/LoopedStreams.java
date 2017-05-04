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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/client/LoopedStreams.java
 * 
 * FILE NAME        	: LoopedStreams.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class LoopedStreams {
	private PipedOutputStream pipedOS = new PipedOutputStream();
	private boolean keepRunning = true;
	private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
		public void close() {
			keepRunning = false;
			try {
				super.close();
				pipedOS.close();
			} catch (IOException e) {
				System.exit(1);
			}
		}
	};
	private PipedInputStream pipedIS = new PipedInputStream() {
		public void close() {
			keepRunning = false;
			try {
				super.close();
			} catch (IOException e) {
				System.exit(1);
			}
		}
	};

	public LoopedStreams() throws IOException {
		pipedOS.connect(pipedIS);
		startByteArrayReaderThread();
	} // LoopedStreams()

	public InputStream getInputStream() {
		return pipedIS;
	} // getInputStream()

	public OutputStream getOutputStream() {
		return byteArrayOS;
	} // getOutputStream()

	private static final long NUM_1000L = 1000L ;
	private void startByteArrayReaderThread() {
		new Thread(new Runnable() {

			public void run() {
				while (keepRunning) {
					if (byteArrayOS.size() > 0) {
						byte[] buffer = null;
						synchronized (byteArrayOS) {
							buffer = byteArrayOS.toByteArray();
							byteArrayOS.reset(); 
						}
						try {
							pipedOS.write(buffer, 0, buffer.length);
						} catch (IOException e) {
							System.exit(1);
						}
					} else
						try {
							Thread.sleep(NUM_1000L );
						} catch (InterruptedException e) {
							//to do nothing
						}
				}
			}
		}).start();
	} // startByteArrayReaderThread()
} // LoopedStreams