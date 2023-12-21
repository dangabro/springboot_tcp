package org.info1.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.UUID;

public class TcpConnection implements Connection {
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;
    private String id;
    private Callback callback;

    public TcpConnection(Socket socket, Callback callback) {
        this.id = UUID.randomUUID().toString();
        this.callback = callback;
        this.socket = socket;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }

    @Override
    public void send(Object objectToSend) {
        if (objectToSend instanceof byte[]) {
            byte[] data = (byte[]) objectToSend;
            try {
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        new Thread(() -> {
            send("tester sending stuff\r\n".getBytes());

            byte buf[] = new byte[64 * 1024];
            while (true) {
                try {
                    int count = inputStream.read(buf);
                    if (count > 0) {
                        byte[] bytes = Arrays.copyOf(buf, count);

                        // TODO process there
                        System.out.print(new String(bytes));
                    } else {
                        socket.close();
                        // TODO process there
                        callback.removeConnection(getId());
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }).start();
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
