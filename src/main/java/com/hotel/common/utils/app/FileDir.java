/**
 * copy right 2012 sctiyi all rights reserved
 * create time:5:45:09 PM
 * author:ftd
 */
package com.hotel.common.utils.app;

import java.io.File;
import java.io.IOException;

/**
 * @author ftd
 *
 */
public class FileDir {
	public static File createFileDir(String fileName) {
		File fDir = new File(fileName);
		if (!(fDir.getParentFile().exists()))
			fDir.getParentFile().mkdirs();
		try {
			if (!fDir.exists())
				fDir.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fDir;
	}
}
