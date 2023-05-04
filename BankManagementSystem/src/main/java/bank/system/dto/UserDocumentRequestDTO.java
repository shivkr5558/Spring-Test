package bank.system.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDocumentRequestDTO {

	private Integer docId;
	private String docName;
	private String docType;
	private String userName;
	private String filePath;
	private Date createDate;
	private Date updateDate;
}
