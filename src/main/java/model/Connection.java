package model;

import java.io.*;
import java.net.Socket;

public class Connection {
    private final Socket socket;
    private final Thread thread;
    private final ConnectionListener eventListener;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Connection(ConnectionListener eventListener, String ip, int port) throws IOException {
        this(eventListener, new Socket(ip, port));

    }

    public Connection(ConnectionListener eventListener, Socket socket) throws IOException {
        this.eventListener = eventListener;
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(Connection.this);
                    while (!thread.isInterrupted()) {
                        eventListener.onReceiveString(Connection.this, reader.readLine());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public synchronized void sendString(String value) {
        try {
            writer.write(value);
            writer.flush();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(Connection.this, e);
        }
    }

}
