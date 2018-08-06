package com.kafkatool.demo.exception;
/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月6日 下午4:41:34
* 类说明 kafka 抛出异常
*/
public class KafKaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Integer resultCode;
	
	public KafKaException(){
		super();
	}
	
	public KafKaException(Integer code,String message){
		super(message);
		this.resultCode = code;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}
}
