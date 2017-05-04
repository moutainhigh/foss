package com.esafenet.dll;

public class NativeClientInterface {
	static {
		System.loadLibrary("CdgControl");
	}

	public static native boolean isDynamicDecrypt(String path);

	public static native String buildSerialNumber(String code);

	public static native boolean DecryptFile(String sourcePath, String targetPath, String cypherKey);

	public static native boolean EncryptFile(String sourcePath, String targetPath, String cypherKey);
}
