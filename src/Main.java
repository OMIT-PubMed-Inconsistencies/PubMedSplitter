import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    static ArrayList<String> ids=new ArrayList<String>();
    HashSet<String> idSet=new HashSet<String>();

    public static void main(String[] args) {
        Main m=new Main();
        m.readOld();
       // m.Read();


    }

    public void readOld(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("../pubmed-list.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            String currentKey = "";

            while ((line = bufferedReader.readLine()) != null) {
                SafeAddId(line);
            }
        }
        catch (Exception e) {

        }
        ids=new ArrayList<String>(); //empty the IDs
    }


    public void Read() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("mirna_pubmed.ttl");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line=null;
            String currentKey="";

            while((line = bufferedReader.readLine()) != null) {

                try {
                    String[] parts = line.split(" ");
                    String key = parts[0].split(":")[1];
                    String id = parts[2];
                    id=id.replace("\"","");
                    if(currentKey.length()==0){
                        currentKey=key;
                        SafeAddId(id);
                    }
                    else if(!currentKey.equalsIgnoreCase(key)){
                        //do print
                        if(ids.size()>1000) {
                            writeToFile(currentKey, ids);
                        }

                        currentKey=key;
                        ids=new ArrayList<String>();
                        SafeAddId(id);
                    }
                    else{
                        SafeAddId(id);
                    }


                    System.out.println(key+" "+id);
                } catch (Exception e) {

                }

            }

            writeToFile(currentKey,ids); //Write the last one

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SafeAddId(String id){
        if(!idSet.contains(id)) {
            ids.add(id);
            idSet.add(id);
        }
    }

    private void writeToFile(String name,ArrayList<String> ids ){
        if(ids.size()>0) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("../output/00_PubMedIds/" + name + ".txt", "UTF-8");
                for (int i = 0; i < ids.size(); i++) {
                    writer.println(ids.get(i));
                }
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


}
