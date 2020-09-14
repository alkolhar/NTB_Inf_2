package tests;

import message.*;


public class MessageTest {
    int port;
    Communication com;
    Message txt;

    public MessageTest(int port) {
        com = new Communication();
        txt = new PostingMessage("user1", "Hallo");
        this.port = port;
        com.open(port);
    }
    
    public void sendMsg(int port) {
        com.sendMessage("localhost", port, txt);
    }
    
    public void getMsg() {
        com.waitForMessage();

        
    }
}
