package mail_list_manager.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import mail_list_manager.dao.FileApiKeyDao;
import mail_list_manager.domain.ApiKeyService;
import mail_list_manager.domain.MailerGroup;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

public class Subscribers {

    private String key = "";
    private String url = "https://api.mailerlite.com/api/v2/";
    private ApiKeyService keyService;

    public Subscribers() {
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(is);
        } catch (Exception e) {

        }

        String keyFile = properties.getProperty("keyFile");

        FileApiKeyDao dao = new FileApiKeyDao(keyFile);
        keyService = new ApiKeyService(dao);
        key = keyService.getKey();
    }

    public String createSubscriber(MailerGroup group, String firstName, String lastName, String email) {
        if (key == null || key.equals("")) {
            return null;
        }
        String response = "";
        Header headerKey = new Header("X-MailerLite-ApiKey", key);
        Header content = new Header("content-type", "application/json; charset=utf-8;");

        HttpClient client = new HttpClient();

        String address = "groups/" + group.getId() + "/subscribers";

        PostMethod method = new PostMethod(url + address);

        method.addRequestHeader(headerKey);
        method.addRequestHeader(content);

        try {
            String json = "{\"email\":\"" + email + "\", \"name\": \"" + firstName + "\",  \"fields\": {\"last_name\": \"" + lastName + "\"}}";

            method.setRequestBody(json);

            int statusCode = client.executeMethod(method);
            if (statusCode != 200) {

            }
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(method.getResponseBodyAsStream()));

            response = rd.readLine();
            rd.close();

            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }

        } catch (HttpException e) {
            return null;

        } catch (IOException e) {
            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }
}
