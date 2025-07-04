/*
 * CMSC 495 6381 Capstone
 * CHeckPoint Carlie 
 * June 7, 2024
 * 
 * The main program.
 * */
package com.umgc.swingui;

import javax.swing.SwingUtilities;

public class LaunchCPC {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CheckPointCharlieGUI());
	}

}
