package com.inverseinnovations.MALSignatureDesigner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.mozilla.javascript.*;

public class Scripting {

	public Scripting(){

	}

	public boolean run(final String script, final String location){
		boolean theReturn = false;
		Callable<Boolean> callable = new Callable<Boolean>() {
			public Boolean call() throws Exception {
				Context cx = createContext();
				Scriptable globalScope = new ImporterTopLevel(cx);

				//js.put("match", match.getERSClass());
				globalScope.put("sig", globalScope, new Signature(location));
				globalScope.put("filter", globalScope, new Filter());
				//js.eval(string);
				cx.evaluateString(globalScope, script, "Script", 1, null);
				return true;
			}
		};
		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<Boolean> task = executorService.submit(callable);

		try{
			// ok, wait for 15 seconds max
			//Boolean result = task.get(15, TimeUnit.SECONDS);
			theReturn = task.get(120, TimeUnit.SECONDS);
			//task.get();
		}
		catch (ExecutionException | InterruptedException | TimeoutException e) {
			e.printStackTrace();//Don't want this spamming the Console
			return theReturn;
		}
		return theReturn;
	}

	private static Context createContext(){
		Context cx = Context.enter();
		cx.setClassShutter(new ClassShutter(){
			public boolean visibleToScripts(String className){
					//if (className.startsWith("com.inverseinnovations.eMafiaServer.")|| className.startsWith("java.lang.")) {
						return true;
					//}
					//return false;
			}
		});
		return cx;
	}
}
