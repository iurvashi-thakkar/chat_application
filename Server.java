import java.net.*;
import java.io.*;
class Server{
    ServerSocket server;
    Socket socket1;
    BufferedReader br;
    PrintWriter out;
    public Server()
    {
        try {
            server=new ServerSocket(7777);
            System.out.println("server is ready to accept request");
            System.out.println("waiting...");
            socket1=server.accept();
            br=new BufferedReader(new InputStreamReader(socket1.getInputStream()));
            out=new PrintWriter(socket1.getOutputStream());
            startReading();
            startWriting();
            
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        

    }
    public void startReading() {
        Runnable r1=()->{
            System.out.println("reader started");
            try{
                while(true)
                {
                        String msg=br.readLine();
                        if(msg.equals("exit"))
                        {
                            System.out.println("client terminated chat");
                            socket1.close(); 
                            break;
                        }
                        System.out.println("client: "+msg);
                    }        
                    } catch (Exception e) {
                        //TODO: handle exception
                       System.out.println("connection closed");
                    }
                   
                
        };
        new Thread(r1).start();
        
    }
    public void startWriting() {
        Runnable r2=()->{
            System.out.println("writer  started");
            try{
                while(!socket1.isClosed())
                {
                       BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                       String content=br1.readLine();
                       out.println(content);
                       out.flush();
                       if(content.equals("exit"))
                       {
                           socket1.close();
                           break;
                       }
                 
                        
                    }
                 }
             catch (Exception e) {
                        //TODO: handle exception
                        System.out.println("connection closed");
                    }
        };
        new Thread(r2).start();

    }
    public static void main(String[] args) {
        System.out.println("this is server..going to be ready");
        new Server();
    }
}
 