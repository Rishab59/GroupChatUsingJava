/*
 * Developer's Name : Rishab.H
 */
 
import java.io.* ;
import java.net.* ;

public class Client 
{
    public static void main(String [] array) throws Exception 
	{
        String serverAddress = "127.0.0.1" ;
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in)) ;

        // Connecting to server
		System.out.println("Connecting to " + serverAddress + "  ...") ;
        Socket socket = new Socket(serverAddress, 8000);
        System.out.println("Connected to server : " + socket);

        // Setting up input and output streams
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true) ;

        // Getting username
        System.out.print("Enter username :\t") ;
        String username = consoleInput.readLine() ;
        out.println(username) ;
		
		
        // Receive messages from server
        new Thread(() -> {
            try 
			{
                while (true) 
				{
                    String input = in.readLine() ;
                    if (input == null) 
					{
                        return ;
                    }
                    System.out.println(input + "\n" ) ;
                }
            } 
			catch (IOException e) 
			{
                System.out.println("Error receiving message from server : " + e) ;
            }
        }).start() ;

        // Sending messages to server
        while (true) 
		{
            String message = consoleInput.readLine() ;
            out.println(message) ;
        }
    }
}
