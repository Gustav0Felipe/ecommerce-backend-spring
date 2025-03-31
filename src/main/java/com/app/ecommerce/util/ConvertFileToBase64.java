package com.app.ecommerce.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class ConvertFileToBase64 {
	public static String encodeFileToBase64Binary(String filePathName)
	        throws IOException {
	
	    File file = new File(filePathName);
	    byte[] bytes = loadFile(file);
	    byte[] encoded = Base64.getEncoder().encode(bytes);
	    String encodedString = new String(encoded);
	
	    return encodedString;
	}
	private static byte[] loadFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);
	
	    long length = file.length();
	    byte[] bytes = new byte[(int)length];
	
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }
	
	    is.close();
	    if (offset < bytes.length || length > Integer.MAX_VALUE) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	
	    return bytes;
	}
}

