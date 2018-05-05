/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author minhdao
 */

@Service
public class StorageService {

    private final String SAVED_DIRECTORY = "saved-files";
    
    @Autowired
    ServletContext context; 
    
    public String store(String fileName, String content) throws IOException{
        fileName = fileName.replace(' ', '-');
        String filePath = init() + File.separator + fileName + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
            
            writer.close();
            return filePath;
        }
    }
    
    public String readFileToString(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }
    
    public boolean removeFile(String filePath){
        File file = new File(filePath);
        return file.delete();
    }
    
    public String init(){
//        String rootPath = System.getProperty("user.dir");
//        String uploadPath = rootPath + "/src/main/resources" + File.separator + UPLOAD_DIRECTORY;
        String savePath = context.getRealPath("") + SAVED_DIRECTORY;
//        System.out.println(savePath);
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        return savePath;
    }

}
