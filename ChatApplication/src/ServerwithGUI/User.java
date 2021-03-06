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
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class User extends Thread {

    String username;
    Socket mySocket;
    DataInputStream in;
    DataOutputStream out;

    public User(Socket s) throws IOException {
        this.mySocket = s;
        this.in = new DataInputStream(s.getInputStream());
        this.out = new DataOutputStream(s.getOutputStream());
    }

    public void run() {
        try {
            out.writeUTF("SRV: Please reply with your username");
            this.username = in.readUTF();
            if (!ChatServer.usernameIsFree(this.username)) {
                out.writeUTF("SRV: Username is taken");
            } else {
                out.writeUTF("SRV: Welcome to our chat");
                ChatServer.users.add(this);
                this.startUserChat();
            }
        } catch (IOException e) {
            System.err.println("ERR: User died. Reason: " + e.getMessage());
        } finally {
            ChatServer.removeUser(this);
            try {
                if (this.in != null) {
                    in.close();
                }
                if (this.out != null) {
                    out.close();
                }
                if (this.mySocket != null) {
                    mySocket.close();
                }
            } catch (IOException e) {
                System.err.println("ERR: Can't close socket: " + e.getMessage());
            }
        }
    }

    private void startUserChat() throws IOException {
        String message;
        do {
            message = this.in.readUTF();
            ChatServer.sendToAll(this.username + " says: " + message);
        } while (!message.equalsIgnoreCase("exit"));
    }

    void send(String message) throws IOException {
        out.writeUTF(message);
    }
}
