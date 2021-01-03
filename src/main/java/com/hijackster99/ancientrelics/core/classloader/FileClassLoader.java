package com.hijackster99.ancientrelics.core.classloader;

import java.io.IOException;
import java.io.InputStream;

public class FileClassLoader extends ClassLoader {
	
	public Class<?> getClassFromName(String name, InputStream stream) {
        try {
            byte[] bytes = new byte[stream.available()];

            stream.read(bytes);
            
            stream.close();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
	}
	
}
