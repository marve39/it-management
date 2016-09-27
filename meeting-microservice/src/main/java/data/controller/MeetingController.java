package data.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletResponse;
import data.client.JumpajaAPI;
import data.domain.Meeting;
import data.Application;
import data.repositories.MeetingRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class MeetingController {

    @Autowired
    JumpajaAPI jumpajaAPI;

    @Autowired
    MeetingRepository meetingRepository;

    @RequestMapping("/command/create/{id}")
    public String create(@PathVariable("id") Long id){
	Meeting meeting = meetingRepository.findOne(id);
	return restClient(Application.jumpajaAPI + meeting.getCreateAPI());
    }

    @RequestMapping("/command/info/{id}")
    public String info(@PathVariable("id") Long id){
        Meeting meeting = meetingRepository.findOne(id);
        return restClient(Application.jumpajaAPI + meeting.getMeetingInfoAPI());
    }

    @RequestMapping("/command/status/{id}")
    public String status(@PathVariable("id") Long id){
        Meeting meeting = meetingRepository.findOne(id);
        return restClient(Application.jumpajaAPI + meeting.getIsMeetingRunningAPI());
    }

    @RequestMapping("/command/stop/{id}")
    public String stop(@PathVariable("id") Long id){
        Meeting meeting = meetingRepository.findOne(id);
        return restClient(Application.jumpajaAPI + meeting.getEndMeetingAPI());
    }

    @RequestMapping(value = "/command/join/{userID}/{meetingID}")
    public ModelAndView joinRedirect(HttpServletResponse httpServletResponse, @PathVariable("userID") Long userID, @PathVariable("meetingID") Long meetingID) {
	Meeting meeting = meetingRepository.findOne(meetingID);
	try {
	String URL = Application.jumpajaAPI +  meeting.joinAPIURL(userID,"Arindra",true);
	System.out.println(URL);
	RedirectView redirectView = new RedirectView();
        redirectView.setUrl(URL);
       return new ModelAndView (redirectView);
	    
 } catch (Exception e) {

                e.printStackTrace();

          }

return null;
}

    private String restClient(String commandURL){
	String ret = "";
	try {

		URL url = new URL(commandURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			ret = ret + output ;
		}

		conn.disconnect();

	  } catch (Exception e) {

		e.printStackTrace();

	  }
		return ret;
    }

}
