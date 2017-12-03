package com.generate.exception;

public abstract class ParceException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5306682883484504933L;
	
	private String className;
	private String errorMsg;
	private int lineNumber;
	
	public ParceException() {
		lineNumber = this.getStackTrace()[2].getLineNumber();
		className = this.getStackTrace()[2].getClassName();
	}
	
	@Override
	public String getMessage()
	{
		String lineMsg = "";
		if(lineNumber != -1)
			lineMsg = " at line number" + lineNumber;
		String msg = this.getClass().getName() + " Thrown in class "
					+ className + lineMsg + ".\n\t" + errorMsg;
		return msg;
	}

	protected void setClazz(String className)
	{
		this.className = className;
	}

	protected void setErrorMsg(String errorMsg)
	{
		this.errorMsg = errorMsg;
	}
	
	protected void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
}
