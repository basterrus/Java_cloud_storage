import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Delete_NetworkServer {


    private int port;
    private String upload;

    public Delete_NetworkServer(int port, String rootFolder) {
        this.port = port;
        this.upload = upload;
    }

    public String getRootFolder() {
        return upload;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер был успешно запущен на порту " + port);
            while (true){
                System.out.println("Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился...");
                createClientHandler(clientSocket);
            }
        }catch (IOException e){
            System.out.println("Ошибка при работе с сервером");
            e.printStackTrace();
        }
    }

    private void createClientHandler(Socket clientSocket) {
        Delete_ClientHandler clientHandler = new Delete_ClientHandler(this, clientSocket);
        clientHandler.run();
    }


}
