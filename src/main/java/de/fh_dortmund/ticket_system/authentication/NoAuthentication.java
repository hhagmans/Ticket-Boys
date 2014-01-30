package de.fh_dortmund.ticket_system.authentication;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.EmployeeDao;
import de.fh_dortmund.ticket_system.util.LDAPJsonParser;
import de.fh_dortmund.ticket_system.util.WebClientDevWrapper;


@ManagedBean(name = "auth")
@SessionScoped
public class NoAuthentication extends Authentication {
	private static final long serialVersionUID = -1084126377607196446L;

	@Override
	protected boolean authenticate(String name, String passwort) {
		
		String checkUrl = "https://bv.intranet.degussa.com/webservice/logincheck/loginCheckJson.cfc?method=checkUser&user=" + name + "&pwd=" + passwort + "&applikation=Tinnef&language=en";
		
//		trust all certificates for SSL connection
		HttpClient httpclient = new DefaultHttpClient();
		WebClientDevWrapper.wrapClient(httpclient);
		
        try {
            HttpGet httpget = new HttpGet(checkUrl);
            System.out.println("executing http request...");

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            // Body contains your json stirng
            String responseBody = null;
			try {
				responseBody = httpclient.execute(httpget, responseHandler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
            
            if (LDAPJsonParser.checkEmployee(responseBody)) {
            	LDAPJsonParser.parseEmployee(responseBody);
            	return true;
            }

        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
		return false;
	}
	
}
