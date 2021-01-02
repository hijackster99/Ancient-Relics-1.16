package com.hijackster99.ancientrelics.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileClassLoader extends ClassLoader {
	
	public Class<?> getClassFromName(String path, String name, ClassLoader loader) {
		File file = new File(path + name + ".class");
		
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];

            fileInputStream.read(bytes);
            
            fileInputStream.close();

            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
	}
	
}
