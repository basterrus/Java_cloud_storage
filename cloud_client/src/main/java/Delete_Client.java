package src.main.java;

import java.io.*;
import java.net.Socket;

public class Delete_Client {

    private static Socket socket;
    private static String login;

    public static void main(String[] args) {

        DataInputStream in = null;
        DataOutputStream out = null;

        try(BufferedReader readConsole = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Input server address");
            String serverName = readConsole.readLine().trim();
            System.out.println("Input server port");
            int serverPort = Integer.parseInt(readConsole.readLine());
            //подключение
            socket = new Socket(serverName, serverPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Input login");
            login = readConsole.readLine().trim();

            //отправка логина
            //out.writeUTF("/login");
            out.write("/login".getBytes());
            //out.writeUTF(login);
            out.write(login.getBytes());

            //бесконечный цикл
            String fileName = null;
            String dirFilename = null;
            while (true){
                System.out.println("Enter command");
                System.out.println("Download the file from the cloud to the folder: /download");
                System.out.println("Upload a file to the cloud: /upload");
                System.out.println("Request a list of files: /all");
                System.out.println("End program: /end");
                String temp = readConsole.readLine();

                if(temp.equals("/download")){
                    out.write(temp.getBytes());

                    System.out.println("Enter file name:");
                    fileName = readConsole.readLine();
                    out.write(fileName.getBytes());

                    System.out.println("Enter the name of the directory where to place the file:");
                    dirFilename = readConsole.readLine();

                    long fileSize = in.readLong();
                    System.out.println(fileSize);
                    File dirFile = FileUtility.createDirectory(dirFilename);
                    File loadFile = FileUtility.createFile(dirFile.getAbsolutePath()+"/"+fileName);
                    try (FileOutputStream inFile = new FileOutputStream(loadFile)){
                        byte[] bufferIn = new byte[8192];
                        long getCount = 0l;
                        while(fileSize > getCount){
                            int count = in.read(bufferIn);
                            if(count <= 0) break;
                            getCount = getCount + count;
                            inFile.write(bufferIn, 0, count);
                        }
                        inFile.flush();
                    }catch (IOException e){
                        System.out.println("File upload error");
                        e.printStackTrace();
                    }

                    System.out.println("Download complete");

                }else if(temp.equals("/upload")){
                    out.writeUTF(temp);

                    System.out.println("Enter full filename:");
                    fileName = readConsole.readLine();
                    File sendFile = FileUtility.createFile(fileName);
                    out.writeUTF(sendFile.getName());

                    long fileSize = sendFile.length();
                    out.writeLong(fileSize);

                    try(FileInputStream outFile = new FileInputStream(sendFile)){
                        byte[] bufferOut = new byte[8192];
                        long getCount = 0l;
                        while(fileSize > getCount){
                            int count = outFile.read(bufferOut);
                            if(count <= 0) break;
                            getCount = getCount + count;
                            out.write(bufferOut, 0, count);
                        }
                        out.flush();
                    }catch (IOException e){
                        System.out.println("File upload error");
                        e.printStackTrace();
                    }
                    System.out.println("Upload complete");

                }else if(temp.equals("/all")){
                    out.writeUTF(temp);

                    System.out.println("Список файлов");
                    while (true){
                        String fileTemp = in.readUTF();
                        if(fileTemp.equals("/end_list")) break;
                        System.out.println("  " + fileTemp);
                    }
                    System.out.println("_____________");


                }else if(temp.equals("/end")){
                    System.out.println("Bye bye");
                    break;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
