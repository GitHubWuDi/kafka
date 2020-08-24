package com.kafkatool.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2019年6月6日 上午8:45:55
* 类说明
*/
public class FileUtil {
     
	public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
