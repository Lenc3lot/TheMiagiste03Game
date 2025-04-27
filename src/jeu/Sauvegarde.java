package jeu;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe gérant la sauvegarde et le chargement de l'état du jeu.
 *
 * Les données sont enregistrées sous forme sérialisée dans un fichier spécifique pour chaque joueur.
 * Cette classe hérite de {@link JSObject}.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Sauvegarde extends JSObject implements Serializable{

    /** Map contenant les données à sauvegarder. */
    private Map<String, Object> savedMap;

    /** Chemin du dossier où les sauvegardes sont stockées. */
    private String PATH_TO_SAVES = "./src/jeu/savedFiles/";

    /** ID de version pour la sérialisation. */
    @Serial
    private  static  final  long serialVersionUID =  1483002994431364708L;

    /**
     * Constructeur par défaut de la sauvegarde.
     * Initialise une nouvelle map vide.
     */
    public Sauvegarde(){
        savedMap = new HashMap<>();
    }

    /**
     * Sauvegarde les données actuelles dans un fichier.
     */
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

    /**
     * Charge les données sauvegardées depuis le fichier associé au joueur.
     *
     * @return Map des données chargées.
     */
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

    /**
     * Retourne la valeur associée à un nom spécifique dans la sauvegarde.
     *
     * @param name Nom du champ à récupérer.
     * @return Valeur associée, ou null si non trouvée.
     * @throws JSException Exception liée à JavaScript.
     */
    @Override
    public Object getMember(String name) throws JSException {
        return savedMap.get(name);
    }

    /**
     * Définit une valeur associée à un nom spécifique dans la sauvegarde.
     *
     * @param name Nom du champ à définir.
     * @param value Valeur à associer.
     * @throws JSException Exception liée à JavaScript.
     */
    @Override
    public void setMember(String name, Object value) throws JSException {
        savedMap.put(name,value);
    }

    /**
     * Supprime une entrée spécifique dans la sauvegarde en fonction de son nom.
     *
     * @param name Nom du champ à supprimer.
     * @throws JSException Exception liée à JavaScript.
     */
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
