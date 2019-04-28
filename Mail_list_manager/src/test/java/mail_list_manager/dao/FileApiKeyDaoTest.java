
package mail_list_manager.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class FileApiKeyDaoTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File file;
    FileApiKeyDao dao;

    @Before
    public void setUp() throws IOException {
        file = testFolder.newFile("testfile_key.txt");
        try (FileWriter writer = new FileWriter(file.getAbsolutePath())) {
            writer.write("testKey");
        }
        dao = new FileApiKeyDao(file.getAbsolutePath());
    }

    @Test
    public void keyIsReadCorrectly() {
        assertTrue(dao.getKeyExists());
        String key = dao.getKey();
        assertEquals("testKey",key);
    }

    @After
    public void tearDown() {
        file.delete();
    }

}
