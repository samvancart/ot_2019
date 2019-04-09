
package mail_list_manager.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileApiKeyDao implements ApiKeyDao {

    private String key;
    private String file;

    public FileApiKeyDao(String file) {
        this.file = file;
    }

    @SuppressWarnings("UseSpecificCatch")
    private boolean load() {
        try {
            Scanner reader = new Scanner(new File(file));
            key = reader.next();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @SuppressWarnings({"UseSpecificCatch", "ConvertToTryWithResources"})
    private void save(String apiKey) {
        try {
            FileWriter writer = new FileWriter(new File(file));
            writer.write(apiKey);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getKeyExists() {
        return load();
    }

    @Override
    public String create(String apiKey) {
        save(apiKey);
        return apiKey;
    }

    @Override
    public String getKey() {
        load();
        return key;
    }

}

