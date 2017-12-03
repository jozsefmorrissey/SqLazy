package com.generate.exception;

import org.w3c.dom.Element;

public class XmlParceException extends ParceException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672259055392985293L;

	public XmlParceException() { super();}
	
	public XmlParceException(String msg) { 
		super();
		super.setErrorMsg(msg);
	}

	public XmlParceException(Element node, String string)
	{
		super();
		String docFilePath = node.getOwnerDocument().getDocumentURI();
		String tagName = node.getTagName();
		// TODO: verify valid (may need to use PositionalXMLReader)
		Object lineNumber = node.getUserData("lineNumber");
		
		String msg = this.getClass().getName() + " was thrown wile parcing document...\n\t\t" +
					docFilePath + " within tag...\n\t\t" +
					tagName + " at Line number...\n\t\t" +
					lineNumber;
		
		this.setErrorMsg(msg);
	}

}
