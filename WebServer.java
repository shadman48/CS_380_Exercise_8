
import java.net.*;
import java.io.*;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is online and on Port 8080");
            System.out.println("Waiting for clients to connect...");
            while(true) {
                try(Socket socket = serverSocket.accept()) {
                	System.out.println("Client connected\n>>>");
                    PrintStream out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String firstLine = br.readLine();
                    System.out.println(firstLine);
		    while(br.ready())
			{
			    System.out.println(br.readLine());
			}
                    
                    File inputFile = new File("hello.html");
                    if(inputFile.isFile()) {
                        BufferedReader file = new BufferedReader(new FileReader(inputFile));
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + inputFile.length() + "\n");
			while(file.ready())
			    out.println(file.readLine());
                    }
                    else {
                        File FileNotFound = new File("404.html");
                        BufferedReader NotFound = new BufferedReader(new FileReader(FileNotFound));
                        out.println("HTTP/1.1 404 Not Found");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + FileNotFound.length() + "\n");
			while(NotFound.ready())
			    out.println(NotFound.readLine());

                    }
                }
            }
        }
    }
}