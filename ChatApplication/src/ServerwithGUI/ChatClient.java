/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerwithGUI;

/**
 *
 * @author IICT
 */
import javax.swing.SwingUtilities;

public class ChatClient {
	final static String host = "localhost";
	final static int port = 1111;
	final static ClientThread c = new ClientThread(ChatClient.host, ChatClient.port);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClientGUI());
	}
}