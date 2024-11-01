package org.AssignemntOS;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.io.File;
import java.util.*;
import java.util.Scanner;

public class CLI {

    private Path currDir = Paths.get(System.getProperty("user.dir"));

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ready to execute your commands. Let's get started");
        while (true){
            System.out.print("> ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting CLI.");
                return;
            }

            String[] parts = input.split(" ");
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : "";

            if (input.contains(">") && !input.contains(">>")){
                String[] str = input.split(" ");
                String content  = execute(str) ;
                redirect(content , str[str.length - 1] , false);
            }
            else if (input.contains(">>")){
                String[] str = input.split(" ");
                String content  = execute(str) ;
                redirect(content , str[str.length - 1] , true);
            }
            else if (command.equalsIgnoreCase("pwd")){
                System.out.println(pwd());
            }
            else if (command.equalsIgnoreCase("cd")){
                cd(argument);
            }
            else if (command.equalsIgnoreCase("help")){
                help();
            }
            else if (command.equalsIgnoreCase("ls")){
                if (parts.length > 1 && argument.equals("-r"))
                    lsRecursive(currDir.toFile(), "");
                else if (parts.length>1 && "-R".equals(parts[1]))
                    lsReverse();
                else if (parts.length == 1){
                    ArrayList<String> files = ls();
                    System.out.println(String.join("\n" , files));
                }
                else if (parts.length > 1 && argument.equals("-a")){
                    ArrayList<String> files = ls();
                    System.out.println(String.join("\n" , files));
                }
                else {
                    System.out.println("Error: Command not recognized.");
                }
            }
            else if (command.equalsIgnoreCase("mkdir")){
                if (parts.length>1) {
                    String dirNames = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
                    mkdir(dirNames);
                }
                else
                    System.out.println("Error: No directory name specified.");
            }
            else if (command.equalsIgnoreCase("rmdir")){
                if (parts.length>1) {
                    String dirNames = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
                    rmdir(dirNames);
                }
                else
                    System.out.println("Error: No directory name specified.");
            }
            else if (command.equalsIgnoreCase("touch")){
                if (parts.length>1) {
                    String fileNames = String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
                    touch(fileNames);
                }
                else
                    System.out.println("Error: No file name specified.");
            }
            else if (command.equalsIgnoreCase("mv")){
                StringBuilder s = new StringBuilder();
                for (int i = 1 ; i < parts.length ; i++)
                    s.append(parts[i]).append(" ");
                mv(s.toString());
            }
            else if (command.equalsIgnoreCase("rm")){
                String[] str = new String[parts.length - 1];
                for (int i = 1 ; i < parts.length ; i++)
                    str[i-1] = parts[i] ;
                rm(str);
            }
            else if (command.equalsIgnoreCase("cat")){
                String[] str = new String[parts.length - 1];
                for (int i = 1 ; i < parts.length ; i++)
                    str[i-1] = parts[i] ;
                System.out.println(cat(str)) ;
            }
        }
    }

    // -------------------------- Commands --------------------------------------
    // --------------------------------------------------------------------------

    // Ayaa





    // Marwa




    // Mostafaa



    // -----------------------------------------------
    // -----------------------------------------------

    // Mohamed 




}
