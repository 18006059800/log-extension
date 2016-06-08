package io.log.extension.server.service;

import io.log.extension.server.entity.App;
import io.log.extension.server.entity.DefaultMessage;
import io.log.extension.server.repo.AppRepo;

import java.util.ArrayList;
import java.util.List;

import io.log.extension.server.repo.DefaultMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private DefaultMessageRepo defaultMessageRepo;
    @Autowired
    private AppRepo appRepo;

    public void handleDefaultMessage(DefaultMessage defaultMessage) {
        String domain = defaultMessage.getDomain();
        String className = defaultMessage.getClassName();
        String classMethod = defaultMessage.getClassMethod();

        DefaultMessage dm = defaultMessageRepo.findByDomainAndClassNameAndClassMethod(domain, className, classMethod);
        if (null != dm) { // 如果ES中有该消息, 应该单独处理,如果这个消息有错误应该更新消息
            // TODO 如果这个消息有错误应该更新消息
            return;
        }

        App de = appRepo.findByAppName(domain);
        if (null == de) { // 如果ES中没有该域信息, 保存域信息以及消息
            de = new App();
            de.setAppName(domain);
            appRepo.save(de);
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
    public List<App> findAllDomain() {
        Iterable<App> result = appRepo.findAll();
        List<App> domains = new ArrayList<App>();
        result.forEach(domain -> {
            domains.add(domain);
        });
        return domains;
    }

    /**
     * 查询域所有根信息
     *
     * @param domain
     * @param page
     * @param size
     * @return
     */
    public Page<DefaultMessage> findAllRootMessage(String domain, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<DefaultMessage> defaultMessages = appRepo.findByDomain(domain, pageable);
        return defaultMessages;
    }

    /**
     * 查询完整信息链
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
