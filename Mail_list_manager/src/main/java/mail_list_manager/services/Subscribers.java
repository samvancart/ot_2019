package mail_list_manager.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
import org.apache.commons.httpclient.NameValuePair;
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

    public String createSubscriber(MailerGroup group, String name, String email) {
        if (key == null || key.equals("")) {
            return null;
        }
        String response = "";
        Header headerKey = new Header("X-MailerLite-ApiKey", key);

        HttpClient client = new HttpClient();

        String address = "groups/" + group.getId() + "/subscribers";

        PostMethod method = new PostMethod(url + address);
        System.out.println(url + address);

        method.addRequestHeader(headerKey);

        try {

            NameValuePair[] params = {
                new NameValuePair("email", email),
                new NameValuePair("name", name),};

            method.addParameters(params);

            int statusCode = client.executeMethod(method);
            if (statusCode != 200) {

            }
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(method.getResponseBodyAsStream()));

            System.out.println("status " + statusCode);
            response = rd.readLine();
            rd.close();
            System.out.println(response);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: " + method.getStatusLine());
                return null;
            }

        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            return null;

        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }
}
