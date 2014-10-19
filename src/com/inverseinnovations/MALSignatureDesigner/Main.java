package com.inverseinnovations.MALSignatureDesigner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Main {

	public static void main(String[] args) {
		String script = "";
		boolean toDelay = false;
		String location = "";//depends on how its ran
		int key = 0;//no key
		for(int i = 0;i < args.length;i++){
			if(key == 1){//user
				location = "/" + args[i];
			}
			else{
				if(args[i].equals("-u")){
					key = 1;//user
					continue;
				}
			}
			key = 0;
		}
		File file = new File(System.getProperty("user.dir") + location + "/sig.ini");
		if(file.isFile() && file.canRead()){//check if script exists
			try {
				script = FileUtils.readFileToString(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			toDelay = new Scripting().run(script,location);
		}

		System.out.println("Done "+toDelay);
		System.exit(0);
	}
}
