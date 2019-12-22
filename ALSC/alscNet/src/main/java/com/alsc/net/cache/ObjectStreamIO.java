package com.alsc.net.cache;

import com.mirko.androidutil.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

/**
 * 对储存卡对象流的IO操作
 * @author langel
 *
 */
public class ObjectStreamIO {
	
	
	public synchronized static Object output(String dir,Object obj,Object name) throws IOException {
		dir = fixDir(dir);
		File file = new File(FileUtils.getRootDir()+dir + name);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		return obj;
	}
	
	public synchronized static Object input(String dir,Object name) throws OptionalDataException, ClassNotFoundException, IOException {
		dir = fixDir(dir);
		File file = new File(FileUtils.getRootDir()+dir + name);
		if(!file.exists()) {
			return null;
		}
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj =  ois.readObject();
		ois.close();
		return obj;
	}
	/**
	 * 判断本地是否存在某个对象流
	 * @return
	 */
	public synchronized static Boolean existsObjectStream(String dir,Object name) {

		dir = fixDir(dir);
		File file = new File(FileUtils.getRootDir()+dir +name);
		return file.exists();

	}
	
	private synchronized static String fixDir(String dir) {

		if(dir == null) {
			dir = "";
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		
		File dirFile = new File(FileUtils.getRootDir()+dir);

		if(dirFile != null && !dirFile.exists()) {

			try{

				dirFile.mkdirs();
//				dirFile.createNewFile();

			}catch (Exception e){

			}
		}
		return dir;
	}
	
	public synchronized static void remove(String dir,Object name) {
		try {
			dir = fixDir(dir);
			File file = new File(FileUtils.getRootDir()+dir + name);
			file.delete();
		} catch (Exception e) {
		}
	}


}
