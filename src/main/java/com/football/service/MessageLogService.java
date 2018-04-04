package com.football.service;

import com.football.domain.MessageLog;
import com.football.repository.MessageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018-3-28.
 */

@Service
public class MessageLogService {

    @Autowired
    private MessageLogRepository messageLogRepository;
    @Autowired
    private EntityManager entityManager;

    public MessageLog save(MessageLog messageLog){
        try {
            MessageLog r=messageLogRepository.save(messageLog);
            return r;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     * @Description:查询所有已推送消息的内容
     * @param:[messageLog]
     * @return java.util.List<com.football.domain.MessageLog>
     * @Date:  2018-3-28 11:26上午
     * @Author:王陈
     *
     */
    public List<MessageLog> getmessloglist(MessageLog messageLog){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessageLog> query = cb.createQuery(MessageLog.class);
        Root<MessageLog> root = query.from(MessageLog.class);
        List<Predicate> predicates = new ArrayList<>();

        try {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            query.orderBy(new ArrayList<Order>(){{add(cb.desc(root.get("messagesenddate")));}});
            List<MessageLog> cls= entityManager.createQuery(query).getResultList();
            return cls;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
