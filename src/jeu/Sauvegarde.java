package jeu;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sauvegarde extends JSObject {
    private Map<String, Object> savedMap;

    public Sauvegarde(){
        savedMap = new HashMap<>();
    }

    public void writeSave(String pathToSave){
        String path = "./savedFiles/";
        String fileName = savedMap.get("playerPseudo").toString() +"_test.txt";
        File saveFile = new File(fileName);
        try {
            if (saveFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(pathToSave + fileName + "_test.txt");
                fileWriter.write("coucou");
                fileWriter.close();
            }
        }catch (IOException e){
            System.out.println();
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
