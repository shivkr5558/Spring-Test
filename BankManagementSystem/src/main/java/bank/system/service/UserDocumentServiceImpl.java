package bank.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.system.dto.UserDocumentRequestDTO;
import bank.system.entity.UserDocuments;
import bank.system.repository.UserDocumentRepository;

@Service
public class UserDocumentServiceImpl implements UserDocumentServiceIntf {

	@Autowired
	private UserDocumentRepository documentRepository;

	public UserDocuments createDocument(UserDocumentRequestDTO dto) throws Exception {
		UserDocuments saveDocuments = null;

		System.out.println("creating document .....");
		try {

			UserDocuments documents = UserDocuments.builder().userName(dto.getUserName()).docName(dto.getDocName())
					.docType(dto.getDocType()).active(1).filePath(dto.getFilePath()).build();
			saveDocuments = documentRepository.save(documents);
			System.out.println("document saved completed ..... ");
			System.out.println(saveDocuments.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saveDocuments;
	}

	public UserDocuments updateDocument(UserDocuments updateDoc) throws Exception {
		UserDocuments updateDocuments = null;

		System.out.println("updating document .....");
		try {
			
			updateDocuments = documentRepository.save(updateDoc);
			System.out.println("document update completed ..... ");
			System.out.println(updateDocuments.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return updateDocuments;
	}

	public UserDocuments getDocumentById(Integer docId) throws Exception {
		System.out.println("getDocumentById  :" + docId);
		return documentRepository.getDocumentsById(docId).orElse(null);
	}

	public List<UserDocuments> getAllDocuments(String user) throws Exception {
		System.out.println("getAllDocuments for User  :" + user);
		return documentRepository.getAllDocumentsForUser(user);
	}

	public boolean deleteDocument(Integer docId) throws Exception {
		System.out.println(" deleteDocument ...:" + docId);
		boolean result = false;
		try {
			documentRepository.deleteDocumentById(docId);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

}
