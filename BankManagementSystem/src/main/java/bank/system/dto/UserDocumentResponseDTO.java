package bank.system.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDocumentResponseDTO {

	private Integer docId;
	private String docName;
    private String docUrl;
	private String docType;
	private String userName;
	private Date createDate;
	private Date updateDate;
}
