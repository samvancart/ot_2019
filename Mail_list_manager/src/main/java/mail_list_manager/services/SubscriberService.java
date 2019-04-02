package mail_list_manager.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import mail_list_manager.domain.MailerGroup;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SubscriberService {
// Mailerlite API Key tähän
    private String API_KEY = "";
    private String url = "https://api.mailerlite.com/api/v2/";

    public String createSubscriber(MailerGroup group, String name, String email) {
        String response = "";
        Header headerKey = new Header("X-MailerLite-ApiKey", API_KEY);

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
