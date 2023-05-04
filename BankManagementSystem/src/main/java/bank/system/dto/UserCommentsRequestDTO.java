package bank.system.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCommentsRequestDTO {
	
	@NotNull
	private Integer postId;
	@NotEmpty
	private String comments;

}
