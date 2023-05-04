package bank.system.service;

import java.util.List;

import bank.system.dto.UserDocumentRequestDTO;
import bank.system.entity.UserDocuments;

public interface UserDocumentServiceIntf {

	public UserDocuments createDocument(UserDocumentRequestDTO requestDto) throws Exception;
	public UserDocuments updateDocument(UserDocuments updateDoc) throws Exception;
	public UserDocuments getDocumentById(Integer docId) throws Exception;
	public List<UserDocuments> getAllDocuments(String user) throws Exception;
	public boolean deleteDocument(Integer docId) throws Exception;
}
