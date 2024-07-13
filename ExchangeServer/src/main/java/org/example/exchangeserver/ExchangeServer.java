package org.example.exchangeserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeServer {
    private static final Logger logger = Logger.getLogger(ExchangeServer.class.getName());
    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) {

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            logger.log(Level.INFO, "Server started, waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                logger.log(Level.INFO, "Client connected: " + clientSocket.getInetAddress());

                threadPool.execute(new ClientHandler(clientSocket));
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occur in starting server.");
        }
    }
}

class ClientHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private Socket clientSocket;

    ConsoleHandler consoleHandler;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new ColorFormatter());
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String line;

            while ((line = in.readLine()) != null) {

                logger.log(Level.INFO, "Received: " + line);

                String response = processRequest(line);

                out.println(response);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occur when write in the file.");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "An error occur in closing from socket.");
            }
        }
    }

    private String processRequest(String input) {
        if (input.equalsIgnoreCase("exchange")) {
            return "Exchange processed";
        } else if (input.equalsIgnoreCase("place offer")) {
            return "Offer placed";
        }
        return "Login request";
    }
}
