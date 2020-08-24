package com.kafkatool.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * java调用shell脚本工具类;
 * 
 * @author wd-pc
 *
 */

public class ShellExecuteScript {

	//private static Logger logger = Logger.getLogger(ShellExecuteScript.class);
	
	/**
	 * java执行shell执行命令行,返回对应的数据
	 * 
	 * @param cmd
	 * @return
	 */
	public static List<String> queryExecuteCmd(String cmd) {
		List<String> stringList = new ArrayList<>();
		try {
			Process pro = Runtime.getRuntime().exec(cmd);
			int exitValue = pro.waitFor();
			System.out.println(exitValue);
			if (0 == exitValue) {//等于0表示脚本能够正确执行
				InputStream in = pro.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = read.readLine()) != null) {
					System.out.println("message:"+line);
					//logger.info("message:"+line);
					stringList.add(line);
				}
				in.close();  
				read.close();
			}
		} catch (Exception e) {
              //logger.error("执行shell脚本异常", e);
              //System.out.println("执行shell脚本异常");
              throw new RuntimeException(e.getMessage());
		}
		return stringList;
	}
	
	
	/**
	 * java调用shell执行是否返回正确结果
	 * @param cmd
	 * @return
	 */
	public static boolean executeShellByResult(String cmd) {
		try {
			Process pro = Runtime.getRuntime().exec(cmd);
			int exitValue = pro.waitFor(); 
			System.out.println("shell执行结果："+exitValue);
			if(exitValue==0){ //等于0表示脚本能够正确执行
				InputStream in = pro.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = read.readLine()) != null) {
                   // logger.info("log info:"+line);
					  System.out.println("log info:"+line);
				}
				in.close();
				read.close();
			}else{
				InputStream errorStream = pro.getErrorStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(errorStream));
				String line = null;
				while ((line = read.readLine()) != null) {
					System.out.println("error message:"+line);
				}
				errorStream.close();
				read.close();
				
				InputStream input = pro.getInputStream();
				BufferedReader inputread = new BufferedReader(new InputStreamReader(input));
				String inputline = null;
				while ((inputline = inputread.readLine()) != null) {
					System.out.println("message:"+inputline);
				}
				input.close();
				inputread.close();
			}
		} catch (Exception e) {
			//logger.error("执行shell脚本异常", e);
		    //System.out.println("执行shell脚本异常");
			return false;
		}
		return true;
	}
	
	
	
	public static boolean executeShellByEnv(String cmd,String[] command) {
		try {
			Process pro = Runtime.getRuntime().exec(cmd, command);
			int exitValue = pro.waitFor(); 
			System.out.println("shell执行结果："+exitValue);
			if(exitValue==0){ //等于0表示脚本能够正确执行
				InputStream in = pro.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = read.readLine()) != null) {
                   // logger.info("log info:"+line);
					  System.out.println("log info:"+line);
				}
				in.close();
				read.close();
			}else{
				InputStream errorStream = pro.getErrorStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(errorStream));
				String line = null;
				while ((line = read.readLine()) != null) {
					System.out.println("error message:"+line);
				}
				errorStream.close();
				read.close();
				
				InputStream input = pro.getInputStream();
				BufferedReader inputread = new BufferedReader(new InputStreamReader(input));
				String inputline = null;
				while ((inputline = inputread.readLine()) != null) {
					System.out.println("message:"+inputline);
				}
				input.close();
				inputread.close();
			}
		} catch (Exception e) {
			//logger.error("执行shell脚本异常", e);
			e.printStackTrace();
		    System.out.println("执行shell脚本异常");
			return false;
		}
		return true;
	}
	
	
	
	public static boolean executeShellByResultArray(String[] command) {
		try {
			Process pro = Runtime.getRuntime().exec(command);
			int exitValue = pro.waitFor(); 
			System.out.println("shell执行结果："+exitValue);
			if(exitValue==0){ //等于0表示脚本能够正确执行
				InputStream in = pro.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in));
				String line = null;
				while ((line = read.readLine()) != null) {
                   // logger.info("log info:"+line);
					  System.out.println("log info:"+line);
				}
				in.close();
				read.close();
			}else{
				InputStream errorStream = pro.getErrorStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(errorStream));
				String line = null;
				while ((line = read.readLine()) != null) {
					System.out.println("error message:"+line);
				}
				errorStream.close();
				read.close();
				
				InputStream input = pro.getInputStream();
				BufferedReader inputread = new BufferedReader(new InputStreamReader(input));
				String inputline = null;
				while ((inputline = inputread.readLine()) != null) {
					System.out.println("message:"+inputline);
				}
				input.close();
				inputread.close();
			}
		} catch (Exception e) {
			//logger.error("执行shell脚本异常", e);
			e.printStackTrace();
		    System.out.println("执行shell脚本异常");
			return false;
		}
		return true;
	}
	
	

	/**
	 * 给对应的文件赋予权限
	 * 
	 * @param shell_file_dir
	 * @param running_shell_file
	 * @return
	 */
	public static int settingPrivilege(String shell_file_dir, String running_shell_file) {
		int rc = 0;
		File tempFile = new File(shell_file_dir+File.separator+running_shell_file);
		ProcessBuilder builder = new ProcessBuilder("/bin/chmod", "755", tempFile.getPath());
		Process process;
		try {
			process = builder.start();
			rc = process.waitFor();
			if (rc == 0) {
				// logger.info("文件赋予对应的权限");
			}
		} catch (IOException e) {
			//logger.error("I/O失败", e);
		} catch (InterruptedException e) {
			//logger.error("线程中断异常", e);
			Thread.currentThread().interrupt();
		}
		return rc;
	}

	/**
	 * 执行shell脚本(无参数执行)
	 * 
	 * @param shell_file_dir
	 * @param running_shell_file
	 * @return
	 */
	public static boolean executeShellScript(String shell_file_dir, String running_shell_file) {

		ProcessBuilder pb = new ProcessBuilder("./" + running_shell_file);
		pb.directory(new File(shell_file_dir));
		int runningStatus = 0;
		String s = null;
		try {
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				// logger.info(s);
				 System.out.println(s);
			}
			while ((s = stdError.readLine()) != null) {
				// logger.info(s);
				 System.out.println(s);
			}
			try {
				runningStatus = p.waitFor();
				System.out.println("runningStatus:"+runningStatus);
				if (runningStatus == 0) {
					// logger.info("脚本调用正常");
				}
			} catch (InterruptedException e) {
				//logger.error("线程中断异常", e);
				Thread.currentThread().interrupt();
			}

		} catch (IOException e) {
			//logger.error("I/O异常", e);
		}
		if (runningStatus != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 执行shell脚本(有参数执行)
	 * 
	 * @param shell_file_dir
	 * @param running_shell_file
	 * @param params
	 * @return
	 */
	public static boolean executeShellScript(String shell_file_dir, String running_shell_file, String[] params) {
		ProcessBuilder pb = new ProcessBuilder("./" + running_shell_file, params[0], params[1], params[2], params[3],
				params[4], params[5], params[6]);
		pb.directory(new File(shell_file_dir));
		int runningStatus = 0;
		String s = null;
		try {
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((s = stdInput.readLine()) != null) {
				// logger.info(s);
				System.out.println(s);
			}
			while ((s = stdError.readLine()) != null) {
				// logger.info(s);
				System.out.println(s);
			}
			try {
				runningStatus = p.waitFor();
				if (runningStatus == 0) {
					return true;
				}
			} catch (InterruptedException e) {
				//logger.error("线程中断异常", e);
				Thread.currentThread().interrupt();
			}

		} catch (IOException e) {
			//logger.error("I/O异常", e);
		}
		return false;
	}

	public static void main(String[] args) {
		// String[] params = new String[] { "root", "mysql", "localhost", "3306",
		// "jsoc", "alarmdeal alarmdefine",
		// "/usr/local/innodbBackup.sql" };
		// executeShellScript(SHELL_FILE_DIR, RUNNING_SHELL_FILE, params);
		//System.out.println(shell);
		//String shell = "/usr/local/vap/flink/bin/flink list";
		//String[] strArr = new String[]{"/honeypot/invadefiled","Honey Pot Intrusion Job","com.vrv.logVO.honeypot.chanting.IntrusionLog","select * from honeyPotIntrusion WHERE 1=1","honeyPotIntrusion"};
	
//		String shell = "\"/honeypot/invadefiled\" \"Honey Pot Intrusion Job\" \"com.vrv.logVO.honeypot.chanting.IntrusionLog\" \"select * from honeyPotIntrusion WHERE 1=1\" \"honeyPotIntrusion\"";
//		String[] split = shell.split(" "); //TODO 这个地方不能改
//		for (String string : split) {
//			System.out.println(string);
//		}
		
//		String cmd = "ssh root@192.168.120.104 /usr/local/vap/flink-1.6.1/bin/flink run -c com.vrv.rule.ruleInfo.FlinkMainFunction /usr/local/vap/api-rule-2.1-SNAPSHOT.jar";
//	    //"/usr/local/vap/flink-1.6.1/bin/flink","run","-c","com.vrv.rule.ruleInfo.FlinkMainFunction","/usr/local/vap/api-rule-2.1-SNAPSHOT.jar",	
//		String[] exeShellArray = new String[] {"/honeypot/invadefiled","Honey Pot Intrusion Job","com.vrv.logVO.honeypot.chanting.IntrusionLog","select * from honeyPotIntrusion WHERE 1=1","honeyPotIntrusion"};
//
//		boolean executeShellByEnv = executeShellByEnv(cmd, exeShellArray);
//		System.out.println(executeShellByEnv);
		String[] commands = new String[] {"/usr/local/vap/flink/bin/flink","run","-c","com.vrv.rule.ruleInfo.FlinkMainFunction","/usr/local/vap/cloud/api-rule.jar","/audit/self/weakMonitor/safeLine",
				"dw_baseline_conf_check job2ee05326e854439e807f57192f95ba66","com.vrv.logVO.selfsafe.DwBaselineConfCheck","select * from dw_baseline_conf_check","dw_baseline_conf_check"};
		boolean executeShellByResult = executeShellByResultArray(commands);
		System.out.println(executeShellByResult);
//		System.out.println(executeShellByResult);
		//		boolean executeShellByResult = executeShellByResultArray(exeShellArray);
//		System.out.println(executeShellByResult);
		//TODO 成功
		//settingPrivilege("/usr/local/vap/soc/api-alarmdeal", "test.sh");
		//executeShellScript("/usr/local/vap/soc/api-alarmdeal", "test.sh");
		//System.out.println(executeShellByResult);
		//String shell = "/usr/local/vap/flink/bin/flink run -c com.vrv.rule.ruleInfo.FlinkMainFunctionTest /usr/local/vap/soc/api-alarmdeal/api-rule-2.1-SNAPSHOT.jar --ruleCode /honeypot/invadefiled --jobName Honey Pot Intrusion Job --orignalLogPath com.vrv.logVO.honeypot.chanting.IntrusionLog --sql select * from honeyPotIntrusion WHERE 1=1 --tableName honeyPotIntrusion";
		//String[] exeShellArray = new String[] {"/usr/local/vap/flink/bin/flink","run","-c","com.vrv.rule.ruleInfo.FlinkMainFunction","/usr/local/vap/soc/api-alarmdeal/api-rule-2.1-SNAPSHOT.jar","--ruleCode \"/honeypot/invadefiled\"","--jobName \"Honey Pot Intrusion Job\"","--orignalLogPath \"com.vrv.logVO.honeypot.chanting.IntrusionLog\"","--sql \"select * from honeyPotIntrusion WHERE 1=1\"","--tableName \"honeyPotIntrusion\""};
//		boolean executeShellByResultArray = executeShellByResultArray(exeShellArray);
//		System.out.println(executeShellByResultArray);
		
		//boolean executeShellByResult = executeShellByResult(shell);
		//System.out.println(executeShellByResult);
//		String cmd = "echo '$MYSQL_HOST'";
//		List<String> queryExecuteCmd = queryExecuteCmd(cmd);
	}

}
