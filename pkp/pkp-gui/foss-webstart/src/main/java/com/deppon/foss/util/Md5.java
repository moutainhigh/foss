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
 * FILE PATH        	: foss-webstart/src/main/java/com/deppon/foss/util/Md5.java
 * 
 * FILE NAME        	: Md5.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
// Md5.java
// $Id: Md5.java,v 1.5 2000/08/16 21:37:48 ylafon Exp $
// (c) COPYRIGHT MIT and INRIA, 1996.
// Please first read the full copyright statement in file COPYRIGHT.html

package com.deppon.foss.util ;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class Md5 {
	private static final int BUFFER_SIZE = 1024 ;

	private static final int S11 = 7 ;
	private static final int S12 = 12 ;
	private static final int S13 = 17 ;
	private static final int S14 = 22 ;
	private static final int S21 = 5 ;
	private static final int S22 = 9 ;
	private static final int S23 = 14 ;
	private static final int S24 = 20 ;
	private static final int S31 = 4 ;
	private static final int S32 = 11 ;
	private static final int S33 = 16 ;
	private static final int S34 = 23 ;
	private static final int S41 = 6 ;
	private static final int S42 = 10 ;
	private static final int S43 = 15 ;
	private static final int S44 = 21 ;

	private static byte padding[] = {
		(byte) 0x80, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0,
		(byte) 0, (byte) 0, (byte) 0, (byte) 0
	} ;

	private static final int NUM_4 = 4;

	private static final int NUM_10 = 10;

	private InputStream in       = null ;
	private boolean     stringp  = false ;
	private int         state[]  = null ;
	private long        count    = 0 ;
	private byte        buffer[] = null ;
	private byte        digest[] = null ;

	private static final int NUM_32 = 32;

	private static final int NUM_8 = 8;

	private static final int NUM_16 = 16;

	private static final int NUM_24 = 24 ;

	private static final int NUM_3 = 3 ;

	private static final int NUM_64 = 64 ;

	private static final int NUM_56 = 56 ;

	private static final int NUM_120 = 120 ;

	private static final int NUM_7 = 7 ;

	private static final int NUM_12 = 12 ;

	private static final int NUM_5 = 5 ;

	private static final int NUM_11 = 11 ;

	private static final int NUM_14 = 14 ;

	private static final int NUM_13 = 13 ;

	private static final int NUM_6 = 6 ;

	private static final int NUM_9 = 9 ;

	private static final int NUM_15 = 15 ;

	private static final int NUM_63 = 63 ;


	private static String stringify (byte buf[]) {
		StringBuffer sb = new StringBuffer(2*buf.length) ;
		for (int i = 0 ; i < buf.length; i++) {
			int h = (buf[i] & 0xf0) >> NUM_4 ;
			int l = (buf[i] & 0x0f) ;
			sb.append (Character.valueOf((char)((h>NUM_9) ? 'a'+h-NUM_10 : '0'+h))) ;
			sb.append (Character.valueOf((char)((l>NUM_9) ? 'a'+l-NUM_10 : '0'+l))) ;
		}
		return sb.toString() ;
	}

	private final int f(int x, int y, int z) {
		return ((x & y) | ((~x) & z)) ;
	}

	private final int g(int x, int y, int z) {
		return ((x & z) | (y & (~z))) ;
	}

	private final int h(int x, int y, int z) {
		return (x ^ y ^ z) ;
	}

	private final int i(int x, int y, int z) {
		return (y ^ (x | (~z))) ;
	}

	private final int rotateLeft(int x, int n) {
		return ((x << n) | (x >>> (NUM_32-n))) ;
	}

	private final int ff(int a,int b,int c,int d,int x,int s,int ac) {
		a += (f(b, c, d) + x + ac) ;
		a = rotateLeft(a, s) ;
		a += b ;
		return a ;
	}

	private final int gg(int a,int b,int c,int d,int x,int s,int ac) {
		a += (g(b, c, d) + x + ac) ;
		a = rotateLeft(a, s) ;
		a += b ;
		return a ;
	}

	private final int hh(int a,int b,int c,int d,int x,int s,int ac) {
		a += (h(b, c, d) + x + ac) ;
		a = rotateLeft(a, s) ;
		a += b ;
		return a ;
	}

	private final int ii(int a,int b,int c,int d,int x,int s,int ac) {
		a += (i(b, c, d) + x + ac) ;
		a = rotateLeft(a, s) ;
		a += b ;
		return a;
	}

	private final void decode (int output[], byte input[], int off, int len) {
		int i = 0 ;
		int j = 0 ;
		for ( ; j < len; i++, j += NUM_4) {
			output[i] = (((int) (input[off+j]&0xff))
				 | (((int) (input[off+j+1] & 0xff)) << NUM_8)
				 | (((int) (input[off+j+2] & 0xff)) << NUM_16)
				 | (((int) (input[off+j+NUM_3] & 0xff)) << NUM_24)) ;
		}
	}

	private final void transform (byte block[], int offset) {
		int a   = state[0] ;
		int b   = state[1] ;
		int c   = state[2] ;
		int d   = state[NUM_3] ;
		int x[] = new int[NUM_16] ;

		decode (x, block, offset, NUM_64);
		/* Round 1 */
		a = ff (a, b, c, d, x[ 0], S11, 0xd76aa478); /* 1 */
		d = ff (d, a, b, c, x[ 1], S12, 0xe8c7b756); /* 2 */
		c = ff (c, d, a, b, x[ 2], S13, 0x242070db); /* 3 */
		b = ff (b, c, d, a, x[ NUM_3], S14, 0xc1bdceee); /* 4 */
		a = ff (a, b, c, d, x[ NUM_4], S11, 0xf57c0faf); /* 5 */
		d = ff (d, a, b, c, x[ NUM_5], S12, 0x4787c62a); /* 6 */
		c = ff (c, d, a, b, x[ NUM_6], S13, 0xa8304613); /* 7 */
		b = ff (b, c, d, a, x[ NUM_7], S14, 0xfd469501); /* 8 */
		a = ff (a, b, c, d, x[ NUM_8], S11, 0x698098d8); /* 9 */
		d = ff (d, a, b, c, x[ NUM_9], S12, 0x8b44f7af); /* 10 */
		c = ff (c, d, a, b, x[NUM_10], S13, 0xffff5bb1); /* 11 */
		b = ff (b, c, d, a, x[NUM_11], S14, 0x895cd7be); /* 12 */
		a = ff (a, b, c, d, x[NUM_12], S11, 0x6b901122); /* 13 */
		d = ff (d, a, b, c, x[NUM_13], S12, 0xfd987193); /* 14 */
		c = ff (c, d, a, b, x[NUM_14], S13, 0xa679438e); /* 15 */
		b = ff (b, c, d, a, x[NUM_15], S14, 0x49b40821); /* 16 */
		/* Round 2 */
		a = gg (a, b, c, d, x[ 1], S21, 0xf61e2562); /* 17 */
		d = gg (d, a, b, c, x[ NUM_6], S22, 0xc040b340); /* 18 */
		c = gg (c, d, a, b, x[NUM_11], S23, 0x265e5a51); /* 19 */
		b = gg (b, c, d, a, x[ 0], S24, 0xe9b6c7aa); /* 20 */
		a = gg (a, b, c, d, x[ NUM_5], S21, 0xd62f105d); /* 21 */
		d = gg (d, a, b, c, x[NUM_10], S22,  0x2441453); /* 22 */
		c = gg (c, d, a, b, x[NUM_15], S23, 0xd8a1e681); /* 23 */
		b = gg (b, c, d, a, x[ NUM_4], S24, 0xe7d3fbc8); /* 24 */
		a = gg (a, b, c, d, x[ NUM_9], S21, 0x21e1cde6); /* 25 */
		d = gg (d, a, b, c, x[NUM_14], S22, 0xc33707d6); /* 26 */
		c = gg (c, d, a, b, x[ NUM_3], S23, 0xf4d50d87); /* 27 */
		b = gg (b, c, d, a, x[ NUM_8], S24, 0x455a14ed); /* 28 */
		a = gg (a, b, c, d, x[NUM_13], S21, 0xa9e3e905); /* 29 */
		d = gg (d, a, b, c, x[ 2], S22, 0xfcefa3f8); /* 30 */
		c = gg (c, d, a, b, x[ NUM_7], S23, 0x676f02d9); /* 31 */
		b = gg (b, c, d, a, x[NUM_12], S24, 0x8d2a4c8a); /* 32 */

		/* Round 3 */
		a = hh (a, b, c, d, x[ NUM_5], S31, 0xfffa3942); /* 33 */
		d = hh (d, a, b, c, x[ NUM_8], S32, 0x8771f681); /* 34 */
		c = hh (c, d, a, b, x[NUM_11], S33, 0x6d9d6122); /* 35 */
		b = hh (b, c, d, a, x[NUM_14], S34, 0xfde5380c); /* 36 */
		a = hh (a, b, c, d, x[ 1], S31, 0xa4beea44); /* 37 */
		d = hh (d, a, b, c, x[ NUM_4], S32, 0x4bdecfa9); /* 38 */
		c = hh (c, d, a, b, x[ NUM_7], S33, 0xf6bb4b60); /* 39 */
		b = hh (b, c, d, a, x[NUM_10], S34, 0xbebfbc70); /* 40 */
		a = hh (a, b, c, d, x[NUM_13], S31, 0x289b7ec6); /* 41 */
		d = hh (d, a, b, c, x[ 0], S32, 0xeaa127fa); /* 42 */
		c = hh (c, d, a, b, x[ NUM_3], S33, 0xd4ef3085); /* 43 */
		b = hh (b, c, d, a, x[ NUM_6], S34,  0x4881d05); /* 44 */
		a = hh (a, b, c, d, x[ NUM_9], S31, 0xd9d4d039); /* 45 */
		d = hh (d, a, b, c, x[NUM_12], S32, 0xe6db99e5); /* 46 */
		c = hh (c, d, a, b, x[NUM_15], S33, 0x1fa27cf8); /* 47 */
		b = hh (b, c, d, a, x[ 2], S34, 0xc4ac5665); /* 48 */

		/* Round 4 */
		a = ii (a, b, c, d, x[ 0], S41, 0xf4292244); /* 49 */
		d = ii (d, a, b, c, x[ NUM_7], S42, 0x432aff97); /* 50 */
		c = ii (c, d, a, b, x[NUM_14], S43, 0xab9423a7); /* 51 */
		b = ii (b, c, d, a, x[ NUM_5], S44, 0xfc93a039); /* 52 */
		a = ii (a, b, c, d, x[NUM_12], S41, 0x655b59c3); /* 53 */
		d = ii (d, a, b, c, x[ NUM_3], S42, 0x8f0ccc92); /* 54 */
		c = ii (c, d, a, b, x[NUM_10], S43, 0xffeff47d); /* 55 */
		b = ii (b, c, d, a, x[ 1], S44, 0x85845dd1); /* 56 */
		a = ii (a, b, c, d, x[ NUM_8], S41, 0x6fa87e4f); /* 57 */
		d = ii (d, a, b, c, x[NUM_15], S42, 0xfe2ce6e0); /* 58 */
		c = ii (c, d, a, b, x[ NUM_6], S43, 0xa3014314); /* 59 */
		b = ii (b, c, d, a, x[NUM_13], S44, 0x4e0811a1); /* 60 */
		a = ii (a, b, c, d, x[ NUM_4], S41, 0xf7537e82); /* 61 */
		d = ii (d, a, b, c, x[NUM_11], S42, 0xbd3af235); /* 62 */
		c = ii (c, d, a, b, x[ 2], S43, 0x2ad7d2bb); /* 63 */
		b = ii (b, c, d, a, x[ NUM_9], S44, 0xeb86d391); /* 64 */

		state[0] += a;
		state[1] += b;
		state[2] += c;
		state[NUM_3] += d;
	}

	private final void update (byte input[], int len) {
		int index = ((int) (count >> NUM_3)) & 0x3f ;
		count += (len << NUM_3) ;
		int partLen = NUM_64 - index ;
		int i = 0 ;
		if ( len >= partLen ) {
			System.arraycopy (input, 0, buffer, index, partLen) ;
			transform (buffer, 0) ;
			for (i = partLen ; i + NUM_63 < len ; i+= NUM_64)
				transform (input, i) ;
			index = 0 ;
		} else {
			i = 0 ;
		}
		System.arraycopy (input, i, buffer, index, len - i) ;
	}

	private byte[] end () {
		byte bits[] = new byte[NUM_8] ;
		for (int i = 0 ; i < NUM_8 ; i++)
			bits[i] = (byte) ((count>>>(i*NUM_8)) & 0xff) ;
		int index  = ((int) (count >> NUM_3)) & 0x3f ;
		int padlen = (index < NUM_56) ? (NUM_56 - index) : (NUM_120 - index) ;
		update (padding, padlen) ;
		update (bits, NUM_8) ;
		return encode(state, NUM_16) ;
	}

	// Encode the content.state array into 16 bytes array
	private byte[] encode (int input[], int len) {
		byte output[] = new byte[len] ;
		int i = 0 ;
		int j = 0 ;
		for ( ; j < len ; i++, j+= NUM_4) {
			output[j]   = (byte) ((input[i]      ) & 0xff) ;
			output[j+1] = (byte) ((input[i] >> NUM_8 ) & 0xff) ;
			output[j+2] = (byte) ((input[i] >> NUM_16) & 0xff) ;
			output[j+NUM_3] = (byte) ((input[i] >> NUM_24) & 0xff) ;
		}
		return output ;
	}

	/**
	 * Get the digest for our input stream.
	 * This method constructs the input stream digest, and returns it, as
	 * a String, following the MD5 (rfc1321) algorithm
	 * @return An instance of String, giving the message digest.
	 * @exception IOException Thrown if the digestifier was unable to read
	 *    the input stream.
	 */
	public byte[] getDigest ()
		throws IOException
	{
		byte buffer[] = new byte[BUFFER_SIZE] ;
		int  got      = -1 ;

		if ( digest != null )
			return digest ;
		while ((got = in.read(buffer)) > 0 )
			update (buffer, got) ;
		this.digest = end () ;
		return digest ;
	}

	/**
	 * Get the digest, for this string digestifier.
	 * This method doesn't throw any IOException, since it knows that the
	 * underlying stream was built from a String.
	 */
	public byte[] processString () {
		if ( ! stringp )
			throw new RuntimeException (this.getClass().getName()
						+ "[processString]"
						+ " not a string.") ;
		try {
			return getDigest() ;
		} catch (IOException ex) {
			//to do nothing
		}
		throw new RuntimeException (this.getClass().getName()
						+ "[processString]"
						+ ": implementation error.") ;
	}

	/**
	 * Get the digest, as a proper string.
	 */
	public String getStringDigest() {
		if ( digest == null )
			throw new RuntimeException (this.getClass().getName()
						+ "[getStringDigest]"
						+ ": called before processing.") ;
		return stringify (digest) ;
	}

	/**
	 * Construct a digestifier for the given string.
	 * @param input The string to be digestified.
	 * @param encoding the encoding name used (such as UTF8)
	 */
	public Md5 (String input, String enc) {
		byte bytes [] = null;
		try {
			bytes = input.getBytes (enc);
		} catch(UnsupportedEncodingException e){
			throw new RuntimeException("no "+enc+" encoding!!!");
		}
		this.stringp = true ;
		this.in      = new ByteArrayInputStream (bytes) ;
		this.state   = new int[NUM_4] ;
		this.buffer  = new byte[NUM_64] ;
		this.count   = 0 ;
		state[0] = 0x67452301;
		state[1] = 0xefcdab89;
		state[2] = 0x98badcfe;
		state[NUM_3] = 0x10325476;
	}

	/**
	 * Construct a digestifier for the given string.
	 * @param input The string to be digestified.
	 */
	public Md5 (String input) {
		this(input, "UTF8");
	}

	/**
	 * Construct a digestifier for the given input stream.
	 * @param in The input stream to be digestified.
	 */
	public Md5 (InputStream in) {
		this.stringp = false ;
		this.in      = in ;
		this.state   = new int[NUM_4] ;
		this.buffer  = new byte[NUM_64] ;
		this.count   = 0 ;
		state[0] = 0x67452301;
		state[1] = 0xefcdab89;
		state[2] = 0x98badcfe;
		state[NUM_3] = 0x10325476;
	}

	public String parseToValue(byte b[]){
		return stringify(b);
	}
	
	public static void main (String args[])
		throws IOException
	{
		/*
		if ( args.length != 1) {
			System.out.println ("Md5 <file>") ;
			System.exit (1) ;
		}*/
		
		String[] paths = new String[]{"C:\\Users\\Administrator\\Desktop\\peixun\\foss-config.jar"};
		
		for(String path : paths){
			Md5 md5 = new Md5 (new FileInputStream(new File(path))) ;
			byte b[]= md5.getDigest();
			System.out.println (stringify(b)) ;
		}
	}

}