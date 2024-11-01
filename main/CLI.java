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



    // -----------------------------------------------
    // -----------------------------------------------
    
    // Marwa


    // -----------------------------------------------
    // -----------------------------------------------

    // Mostafaa



    // -----------------------------------------------
    // -----------------------------------------------

    // Mohamed 


    public void mv(String str){
        List<String> files = new ArrayList<>(Arrays.asList(str.split(" ")));

        Path target = currDir.resolve(files.get(files.size() - 1)); // last file or dir
        List<Path> source = new ArrayList<>(); // list if multiple sorces
        for (int i = 0 ; i < files.size() - 1 ; i++)
            source.add(currDir.resolve(files.get(i)));

        try{
            if (source.size() == 1){

                Path src = source.get(0);

                if (!Files.exists(src)) {
                    throw new FileNotFoundException(src.getFileName() + " -> Not Found");
                }

                if (Files.isDirectory(src)){
                    if (Files.notExists(target)){
                        Files.move(src , src.resolveSibling(target.toString()));
                    }
                    else if (Files.isDirectory(target)){
                        Files.move(src, target.resolve(src.getFileName()) , StandardCopyOption.REPLACE_EXISTING);
                    }
                    else {
                        throw new FileNotFoundException("Cannot overwrite non-directory " + src.toFile().getName() + " with directory " + target.toFile().getName());
                    }
                }
                else if (!Files.isDirectory(src)){
                    if (Files.notExists(target)){
                        Files.move(src , src.resolveSibling(target.toString())); // rename
                    }
                    else if (Files.isDirectory(target)){
                        Files.move(src , src.resolveSibling(target.toString()));
                    }
                    else {
                        Files.move(src, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            else { //  multiple sources
                if (Files.notExists(target) || !Files.isDirectory(target)) {
                    throw new IOException("Target must be an existing directory for multiple sources");
                }

                for (Path file : source){
                    if (Files.notExists(file)) {
                        throw new FileNotFoundException("Source file/directory does not exist: " + file.toFile().getName());
                    }
                    else
                        Files.move(file, target.resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    // -----------------------------------------------
    // -----------------------------------------------

    private String execute(String[] str){

        String command = str[0].trim();
        String content  = "" ;

        if (command.equals("ls")) {
            ArrayList<String> files = ls();
            content = String.join("\n" , files);
        }
        else if (command.equals("echo")){
            StringBuilder s = new StringBuilder();
            for (int i = 1 ; i < str.length ; i++){
                if (str[i].equals(">"))
                    break;
                s.append(str[i]).append(" ");
            }
            content = s.toString();
        }
        else if (command.equals("cat"))
            content = cat(str[1].split(" "));

        return content;
    }

    public void redirect(String content , String file , boolean append){  // for > and >> 
        try{
            FileWriter w = new FileWriter(file , append);
            w.write(content);
            w.write("\n");
            w.close();
        }
        catch (IOException e){
            System.out.println("Error writing ti file : " + e.getMessage());
        }
    }

    // -----------------------------------------------
    // -----------------------------------------------

    public ArrayList<String> ls(){
        ArrayList<String> files = new ArrayList<>();
        if (Files.isDirectory(currDir)){
            try{
                DirectoryStream<Path> streamDir = Files.newDirectoryStream(currDir);
                for (Path file : streamDir)
                    files.add(file.getFileName().toString());

                Collections.sort(files);
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("Current path not directory");

        return files;
    }



}
