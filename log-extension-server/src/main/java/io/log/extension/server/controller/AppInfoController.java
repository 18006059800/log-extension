package io.log.extension.server.controller;

import io.log.extension.server.entity.DefaultMessage;
import io.log.extension.server.entity.Domain;
import io.log.extension.server.entity.ExceptionMessage;
import io.log.extension.server.service.MessageService;
import io.log.extension.server.service.vo.ListMessageCriteria;
import io.log.extension.server.service.vo.TreeGridMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 应用信息
 * <p>
 * Created by percy on 6/8/16.
 */
@Controller
@RequestMapping("/app")
public class AppInfoController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @RequestMapping("/list")
    public List<Domain> listApp() {
        List<Domain> domains = messageService.findAllDomain();
        return domains;
    }

    @ResponseBody
    @RequestMapping("/findSelectedMessage")
    public List<DefaultMessage> findSelectedMessage(ListMessageCriteria criteria) {
        List<DefaultMessage> result = messageService.findSelectedMessage(criteria);
        return result;
    }

    @RequestMapping("/showChain")
    public String showChain() {
        return "showChain";
    }

    @ResponseBody
    @RequestMapping("/getShowChainMessage")
    public List<TreeGridMessage> getShowChainMessage(String rootMessageId, Boolean error) {

        List<TreeGridMessage> messages = new ArrayList<TreeGridMessage>();
        if (null != error && error) {
            List<ExceptionMessage> result = messageService.getErrorMessageChain(rootMessageId);

            Collection<ExceptionMessage> newResult = deleteTwiceMessage(result);
            for (ExceptionMessage item :newResult) {
                TreeGridMessage message = new TreeGridMessage();
                message.setClassMethod(item.getClassMethod());
                message.setClassName(item.getClassName());
                message.setId(item.getMessageId());
                message.setDomain(item.getDomain());
                message.setHasError(item.getHasError());
                if (item.getMessageId().equals(item.getParentMessageId())) {
                    message.setPid("-1");
                } else {
                    message.setPid(item.getParentMessageId());
                }
                messages.add(message);
            }
            return  messages;
        }

        List<DefaultMessage> result = messageService.getMessageChain(rootMessageId);

        for (DefaultMessage item : result) {
            TreeGridMessage message = new TreeGridMessage();
            message.setClassMethod(item.getClassMethod());
            message.setClassName(item.getClassName());
            message.setId(item.getMessageId());
            message.setDomain(item.getDomain());
            message.setHasError(item.getHasError());
            if (item.getMessageId().equals(item.getParentMessageId())) {
                message.setPid("-1");
            } else {
                message.setPid(item.getParentMessageId());
            }
            messages.add(message);
        }

//        result.forEach(item -> {
//            TreeGridMessage message = new TreeGridMessage();
//            message.setClassMethod(item.getClassMethod());
//            message.setClassName(item.getClassName());
//            message.setId(item.getMessageId());
//            message.setDomain(item.getDomain());
//            message.setHasError(item.getHasError());
//            if (item.getMessageId().equals(item.getParentMessageId())) {
//                message.setPid("-1");
//            } else {
//                message.setPid(item.getParentMessageId());
//            }
//            messages.add(message);
//        });

        return messages;
    }

    @RequestMapping("/list-root-message")
    public String listMessage(ListMessageCriteria criteria, ModelMap mm) {
        Integer totalPages = 0;
        Integer page = criteria.getPage();
        Integer size = criteria.getSize();
        page = (null == page) ? 0 : page;
        size = (null == size) ? 20 : size;

        List<Domain> domains = messageService.findAllDomain();
        mm.put("domains", domains);

        List<DefaultMessage> data = new ArrayList<DefaultMessage>();
        if (StringUtils.isEmpty(criteria.getDomain())) {
            mm.put("page", 0);
            mm.put("size", 20);
            mm.put("totalPages", totalPages);
            mm.put("data", data);

            return "/index";
        }

        Page<DefaultMessage> messages = messageService.findAllRootMessage (criteria.getDomain(), page, size);
        data = messages.getContent();
        mm.put("data", data);
        mm.put("page", page);
        mm.put("size", size);
        mm.put("totalPages", messages.getTotalPages());
        return "/index";
    }

    @RequestMapping("/exception")
    public String showException(ListMessageCriteria criteria, ModelMap mm) {
        Integer totalPages = 0;
        Integer page = criteria.getPage();
        Integer size = criteria.getSize();
        page = (null == page) ? 0 : page;
        size = (null == size) ? 20 : size;
        criteria.setPage(page);
        criteria.setSize(size);

        List<Domain> domains = messageService.findAllDomain();
        mm.put("domains", domains);

        List<DefaultMessage> data = new ArrayList<DefaultMessage>();
        if (StringUtils.isEmpty(criteria.getDomain())) {
            mm.put("page", 0);
            mm.put("size", 20);
            mm.put("totalPages", totalPages);
            mm.put("data", data);

            return "/exception";
        }

        Page<ExceptionMessage> messages = messageService.findExceptionMessage(criteria);

        mm.put("page", page);
        mm.put("size", size);
        mm.put("totalPages", messages.getTotalPages());
        mm.put("data", messages.getContent());

        return "/exception";
    }

    private Collection<ExceptionMessage> deleteTwiceMessage (List<ExceptionMessage> defaultMessages) {
        Map<String, ExceptionMessage> result = new HashMap<>();
        for (ExceptionMessage dm : defaultMessages) {
            result.put(dm.getMessageId(), dm);
        }
        return result.values();
    }
}
