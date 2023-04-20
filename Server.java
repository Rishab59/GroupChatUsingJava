/*
 * Developer's Name : Rishab.H
 */

import java.io.* ;
import java.net.* ;
import java.util.* ;

public class Server 
{
    private ServerSocket serverSocket ;
    private List<PrintWriter> clients = new ArrayList<>() ;

    public void start(int port) throws IOException 
	{
        serverSocket = new ServerSocket(port) ;
        System.out.println("Server started on port " + port) ;

        while (true) 
		{
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket) ;
            new ClientHandler(clientSocket).start() ;
        }
    }

    private class ClientHandler extends Thread 
	{
        private Socket clientSocket ;
        private String username ;
        private BufferedReader in ;
        private PrintWriter out ;

        public ClientHandler(Socket socket) 
		{
            this.clientSocket = socket ;
        }

        public void run() 
		{
            try 
			{
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())) ;
                out = new PrintWriter(clientSocket.getOutputStream(), true) ;

                // Asking for username
                //out.print("Enter username:\t") ;
                username = in.readLine() ;
                out.println("\nWelcome to the chat, " + username + " !\n") ;
			

                // Add client to list
                clients.add(out) ;

                // Broadcast messages to all clients
                while (true) 
				{
                    String input = in.readLine() ;
                    if (input == null) 
					{
                        return ;
                    }
                    for (PrintWriter client : clients) 
					{
                        client.println(username + " : " + input ) ;
                    }
                }
            } 
			catch (IOException e) 
			{
                System.out.println("Error handling client : " + e) ;
            } 
			finally 
			{
                // Remove client from list
                clients.remove(out) ;
                try 
				{
                    clientSocket.close() ;
                } 
				catch (IOException e) 
				{
                    System.out.println("Error closing client socket : " + e) ;
                }
            }
        }
    }

    public static void main(String [] array) throws Exception 
	{
        Server server = new Server() ;
        server.start(8000) ;
    }
}
