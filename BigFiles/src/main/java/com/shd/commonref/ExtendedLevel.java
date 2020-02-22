package com.shd.commonref;

import java.util.logging.Level;

public class ExtendedLevel extends java.util.logging.Level
{
	protected ExtendedLevel(String name, int value) {
		super(name, value);
	}
	/**
	 * Added Levels for MSG and DEBUG such that DEBUG level Includes the normal info MSGs
	 * While the normal info MSG just for INFO
	 */
	private static final long serialVersionUID = 1L;
	public static final Level DEBUG = new ExtendedLevel("DEBUG", 1900) ;
	public static final Level MSG = new ExtendedLevel("MSG", 2000) ;
	public static final Level ERR = new ExtendedLevel("ERR", 2020) ; 
}
