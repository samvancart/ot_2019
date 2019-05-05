package mail_list_manager.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import mail_list_manager.dao.FileApiKeyDao;
import mail_list_manager.domain.ApiKeyService;
import mail_list_manager.domain.MailerGroup;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Groups {

    private ApiKeyService keyService;
    private String key = "";
    private String url = "https://api.mailerlite.com/api/v2/";

    public Groups()  {
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

    public String getGroupsAsString() {
        String response = "";
        Header headerKey = new Header("X-MailerLite-ApiKey", key);

        HttpClient client = new HttpClient();

        GetMethod method = new GetMethod(url + "groups");

        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        method.addRequestHeader(headerKey);

        try {
            int statusCode = client.executeMethod(method);

            if (statusCode != HttpStatus.SC_OK) {
            }
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(method.getResponseBodyAsStream()));

            response = rd.readLine();
            rd.close();
        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());

        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    public ArrayList<MailerGroup> getParsedGroups() {
        String response = getGroupsAsString();
        ArrayList<MailerGroup> groups = new ArrayList<>();

        ArrayList<String> data = new ArrayList<>();
        char[] c = new char[response.length()];
        c = response.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c.length; i++) {
            char comma = ',';
            if (c[i] != '[' && c[i] != ']' && c[i] != '{' && c[i] != '}') {
                sb.append(c[i]);
            }
            if (c[i] == comma) {
                String text = sb.toString();
                sb = new StringBuilder();
                if (text.contains("id") || text.contains("name")) {
                    for (int j = 0; j < text.length(); j++) {
                        if (text.charAt(j) == ':') {
                            text = text.substring(j + 1, text.length());
                        }
                    }
                    text = text.replace('"', ' ');
                    text = text.replace(',', ' ');
                    text = text.trim();
                    data.add(text);
                }
            }
        }
        for (int i = 0; i < data.size(); i++) {
            groups.add(new MailerGroup(data.get(i), data.get(i + 1)));
            i++;
        }

        return groups;
    }

}