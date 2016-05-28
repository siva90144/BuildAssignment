package com.build.util;

public class Util {
	/**
	 * This Method is used to wait the thread for specified time
	 * @param time
	 */
	public static void waitForSecifiedTime(long time){
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * This method is used to convert the string to double value
	 * @param s
	 * @return
	 */
	public static Double stringToDouble(String s){
		if(s !=null && !s.isEmpty()){
			try{				
			return Double.valueOf(s.replace("$", ""));
			}catch(NumberFormatException nfe){
				System.out.println("nfe");
			}
		}
		return null;
	}
}
