package com.inverseinnovations.MALSignatureDesigner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Main {

	public static void main(String[] args) {
		String script = "";
		boolean toDelay = false;
		File file = new File(System.getProperty("user.dir") + "/sig.ini");
		if(file.isFile() && file.canRead()){//check if the font is in the font
			try {
				script = FileUtils.readFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			toDelay = new Scripting().run(script);
		}

		System.out.println("Done "+toDelay);
		System.exit(0);
	}
}
