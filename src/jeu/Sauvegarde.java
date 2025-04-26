package jeu;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Sauvegarde extends JSObject implements Serializable{
    private Map<String, Object> savedMap;
    private String PATH_TO_SAVES = "./src/jeu/savedFiles/";
    @Serial
    private  static  final  long serialVersionUID =  1483002994431364708L;

    public Sauvegarde(){
        savedMap = new HashMap<>();
    }

    public void writeSave(){
        String fileName = savedMap.get("playerPseudo").toString() +"_test.ser";
        File saveFile = new File(PATH_TO_SAVES+fileName);
        try {
            if(saveFile.exists()){
                if(saveFile.delete()){
                    System.out.println("File deleted");
                };
            }
            if (saveFile.createNewFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(savedMap);
                objectOutputStream.close();
                fileOutputStream.close();
            }
        }catch (IOException e){
            System.out.println();
        }
    }

    public HashMap<String, Object> loadSave(){
        String fileName = savedMap.get("playerPseudo").toString() +"_test.ser";
        File saveFile = new File(PATH_TO_SAVES+fileName);
        try{
            FileInputStream fileIn = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileIn);
            HashMap<String,Object> loadedData = (HashMap<String, Object>) objectInputStream.readObject();
            objectInputStream.close();
            return loadedData;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getMember(String name) throws JSException {
        return savedMap.get(name);
    }

    @Override
    public void setMember(String name, Object value) throws JSException {
        savedMap.put(name,value);
    }

    @Override
    public void removeMember(String name) throws JSException {
        savedMap.remove(name);
    }

    // None of these following methods are used - IMPLEM @Override ONLY

    @Override
    public Object getSlot(int index) throws JSException {
        return null;
    }

    @Override
    public void setSlot(int index, Object value) throws JSException {
        // No use
    }

    @Override
    public Object call(String methodName, Object... args) throws JSException {
        return null;
    }

    @Override
    public Object eval(String s) throws JSException {
        return null;
    }
}
