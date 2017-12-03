package com.generate.exception;

public class SqlParceException extends ParceException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5534995130809402734L;

	public SqlParceException(String format, String colStr, String...problems) {
		//this.setClazz(this.getStackTrace()[1].getClass());
		this.setLineNumber(this.getStackTrace()[1].getLineNumber());
		String msg = "Expected format: '" + format + "'\n";
		msg += "\tString Recieved: '" + colStr + "'\n";
		for(String problem : problems) {
			msg += "\t\tError Message: " + problem;
		}
		this.setErrorMsg(msg);
	}

}
