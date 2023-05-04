package bank.system.webservicecall;

import org.springframework.stereotype.Component;

@Component
public class FeignClientFallBack implements FeignClientWebServiceCall{

	@Override
	public Post createPost(Post req) {
		System.out.println("Fallback Call for Post API");
		return new Post();
	}

	

}
