package org.example.mas_zao_jastrzebski_s27397.model;

import java.io.*;
import java.util.*;

public class ObjectPlus implements Serializable {
    private static final String FILE_PATH = "quantfolio.ser";
    private static Map<Class<? extends ObjectPlus>, List<ObjectPlus>> allExtens = new HashMap<>();

    public ObjectPlus() {
        Class<? extends ObjectPlus> theClass = this.getClass();

        allExtens.computeIfAbsent(theClass, k -> new ArrayList<>()).add(this);
    }

    public static void writeExtens(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtens);
    }

    public static void readExtens(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtens = (HashMap) stream.readObject();
    }

    public static <T> Iterable<T> getExtent(Class<T> type) throws ClassNotFoundException {
        if (allExtens.containsKey(type)) {
            return (Iterable<T>) allExtens.get(type);
        }

//        throw new ClassNotFoundException(
//                String.format("%s. Stored extens: %s",
//                        type.toString(),
//                        allExtens.keySet()));
        return Collections.emptyList();
    }

    public static void showExtent(Class theClass) throws Exception {
        List<ObjectPlus> extent = null;

        if (allExtens.containsKey(theClass)) {
            extent = allExtens.get(theClass);
        }
        else {
            throw new Exception("Unknown class " + theClass);
        }

        System.out.println("Extent of the class: " + theClass.getSimpleName());

        for (Object obj : extent) {
            System.out.println(obj);
        }
    }

    public static void clearExtens() {
        allExtens.clear();
    }

    public static void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            writeExtens(out);
            System.out.println("Zapisano ekstensje do pliku");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void read() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Brak pliku ekstensji, start od zera");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Wczytano ekstensje z pliku");
            readExtens(in);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu ekstensji: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static void removeFromExtent(ObjectPlus obj) {
        Class<?> cls = obj.getClass();
        if (allExtens.containsKey(cls)) {
            allExtens.get(cls).remove(obj);
        }
    }

    public static void resetSystemu() {
        clearExtens();
        File file = new File(FILE_PATH);

        if (file.exists()) {
            file.delete();
        }
        else {
            System.out.println("Plik ekstensji nie istnieje");
        }
    }

}
