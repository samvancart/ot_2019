package mail_list_manager.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import mail_list_manager.dao.ApiKeyDao;
import mail_list_manager.dao.FileApiKeyDao;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class ApiKeyServiceTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    File file;
    ApiKeyService service;
    ApiKeyDao dao;

    public ApiKeyServiceTest() {
    }

    @Before
    public void setUp() throws IOException {
        file = testFolder.newFile("testfile_key.txt");
        dao = new FileApiKeyDao(file.getAbsolutePath());
    }

    @Test
    public void createKeyWorks() {
        service = new ApiKeyService(dao);
        assertTrue(service.createKey("key"));
    }

    @Test
    public void getKeyWorks() {
        String key = "key";
        service = new ApiKeyService(dao);
        service.createKey(key);
        assertEquals(key,service.getKey());
    }

    @After
    public void tearDown() {
        file.delete();
    }

}
