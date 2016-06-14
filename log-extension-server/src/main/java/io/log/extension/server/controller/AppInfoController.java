package io.log.extension.server.controller;

import io.log.extension.server.entity.DefaultMessage;
import io.log.extension.server.entity.Domain;
import io.log.extension.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 应用信息
 * <p>
 * Created by percy on 6/8/16.
 */
@Controller
@RequestMapping("/app")
public class AppInfoController {

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @RequestMapping("/list")
    public List<Domain> listApp() {
        List<Domain> domains = messageService.findAllDomain();
        return domains;
    }

    @RequestMapping("/list-root-message")
    public String listMessage(String domain, Integer page, Integer size, ModelMap mm) {
        Integer totalPages = 0;
        page = (null == page) ? 0 : page;
        size = (null == size) ? 0 : size;

        List<Domain> domains = messageService.findAllDomain();
        mm.put("domains", domains);

        if (StringUtils.isEmpty(domain)) {
            mm.put("page", 0);
            mm.put("size", 0);
            mm.put("totalPages", totalPages);
            return "/index";
        }


        Page<DefaultMessage> messages = messageService.findAllRootMessage (domain, page, size);
        mm.put("data", messages.getContent());
        mm.put("page", page);
        mm.put("size", size);
        mm.put("totalPages", messages.getTotalPages());
        return "/index";
    }

}
