package io.log.extension.server.service;

import io.log.extension.server.entity.DefaultMessage;
import io.log.extension.server.entity.Domain;
import io.log.extension.server.repo.DefaultMessageRepo;
import io.log.extension.server.repo.DomainRepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	@Autowired
	private DefaultMessageRepo defaultMessageRepo;
	@Autowired
	private DomainRepo domainRepo;

	public void handleDefaultMessage(DefaultMessage defaultMessage) {
		String domain = defaultMessage.getDomain();
		String className = defaultMessage.getClassName();
		String classMethod = defaultMessage.getClassMethod();

		DefaultMessage dm = defaultMessageRepo.findByDomainAndClassNameAndClassMethod(domain, className, classMethod);
		if (null != dm) { // 如果ES中有该消息, 应该单独处理,如果这个消息有错误应该更新消息
			// TODO 如果这个消息有错误应该更新消息
			return;
		}

		Domain de = domainRepo.findByName(domain);
		if (null == de) { // 如果ES中没有该域信息, 保存域信息以及消息
			de = new Domain();
			de.setName(domain);
			domainRepo.save(de);
			defaultMessageRepo.save(defaultMessage);
			return;
		}

		defaultMessageRepo.save(defaultMessage);
	}
	
	/**
	 * 查询所有域信息
	 * 
	 * @return
	 */
	public List<Domain> findAllDomain() {
		Iterable<Domain> result = domainRepo.findAll();
		List<Domain> domains = new ArrayList<Domain>();
		result.forEach(domain -> {
			domains.add(domain);
		});
		return domains;
	}
	
	/**
	 * 查询域所有根信息
	 * 
	 * @param domain
	 * @return
	 */
	public List<DefaultMessage> findAllRootMessage(String domain) {
		 List<DefaultMessage> defaultMessages = defaultMessageRepo.findByDomainAndIsRootMessage(domain, true);
		return defaultMessages;
	}
	
	/**
	 * 查询完整信息链
	 * 
	 * 
	 * @param domain
	 * @param rootMessageId
	 * @return
	 */
	public List<DefaultMessage> getMessageChain(String domain, String rootMessageId) {
		List<DefaultMessage> defaultMessages = defaultMessageRepo.findByDomainAndRootMessageId(domain, rootMessageId);
		return defaultMessages;
	}
	
}
