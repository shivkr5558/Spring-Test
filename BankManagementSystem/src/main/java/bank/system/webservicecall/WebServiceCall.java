package bank.system.webservicecall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class WebServiceCall {

	@Autowired
	private FeignClientWebServiceCall feignClientWebServiceCall;
	
	public void callPostApi(Post post) {
		try {
			
			feignClientWebServiceCall.createPost(post);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
