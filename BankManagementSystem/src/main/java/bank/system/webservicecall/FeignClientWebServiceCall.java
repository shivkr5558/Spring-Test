package bank.system.webservicecall;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="post-service",configuration=FeignClientConfig.class,
					fallback=FeignClientFallBack.class,url = "${feign.url}"	)
public interface FeignClientWebServiceCall {

	@PostMapping("/posts")
	public Post createPost(@RequestBody Post req);
}
