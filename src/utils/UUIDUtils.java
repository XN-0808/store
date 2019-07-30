package utils;/*
@author Shawn
@creat 2019-07-16-17:53
*/

import java.util.UUID;

public class UUIDUtils {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
