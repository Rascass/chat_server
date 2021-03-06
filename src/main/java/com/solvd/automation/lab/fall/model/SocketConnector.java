package com.solvd.automation.lab.fall.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.solvd.automation.lab.fall.config.SessionFactory.LOGGER;

public class SocketConnector implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public SocketConnector(ServerSocket server) {
        try {
            this.socket = server.accept();
            this.reader = createReader();
            this.writer = createWriter();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void writeLine (String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine () {
        try {
            return reader.readLine();
        } catch (IOException e) {
            LOGGER.info("Client disconnected!");
            return "Client disconnected!";
        }
    }

    public String getIp () {
        String result = socket.getRemoteSocketAddress().toString();
        return result.substring(1);
    }

    private BufferedReader createReader() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private BufferedWriter createWriter() {
        try {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    public BufferedReader getReader() {
        return reader;
    }
}