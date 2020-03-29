package com.example.shoppinglist;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Utils {
    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    static void writeToFile(ArrayList<Item> items, String fileName, Context context){
        try {
            File file = new File(context.getFilesDir(),fileName);
            System.err.println("Saving : " + file.getPath());
            FileWriter fw = new FileWriter(file);
            JsonWriter jw = new JsonWriter(fw);
            jw.beginArray();
            for (Item i : items) {
                writeItem(jw,i);
            }
            jw.endArray();
            jw.close();
            fw.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private static void writeItem(JsonWriter jw,Item i){
        try {
            jw.beginObject();
            jw.name("name").value(i.getName());
            jw.name("quantity").value(i.getQuanity());
            jw.name("got").value(i.isGot());
            jw.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static ArrayList<Item> readFromFile(String fileName,Context context){
        try {
            File file = new File(context.getFilesDir(),fileName);
            System.err.println("Loading : " + file.getPath());
            System.err.println(file.getPath());
            ArrayList<Item> items = new ArrayList<>();
            FileReader fr = new FileReader(file);
            JsonReader jr = new JsonReader(fr);
            jr.beginArray();
            while (jr.hasNext()){
                items.add(readItem(jr));
            }
            jr.endArray();
            jr.close();
            fr.close();
            return items;
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println(fileName + " problem...");
            return new ArrayList<>();
        }
    }

    private static Item readItem(JsonReader jr) throws IOException {
        String nameItem = null;
        int quantity=0;
        boolean got = false;
        jr.beginObject();
        while(jr.hasNext()){
            String name = jr.nextName();
            if(name.equals("name"))
                nameItem = jr.nextString();
            else if(name.equals("quantity"))
                quantity = jr.nextInt();
            else if(name.equals("got")) {
                got = jr.nextBoolean();
            }
            else
                jr.skipValue();
        }
        jr.endObject();
        return new Item(nameItem,quantity,got);

    }
}
