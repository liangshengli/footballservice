package com.football.service;

import com.football.domain.MessUser;
import com.football.repository.MessUserRepository;
import com.football.util.HelperClazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018-3-28.
 */

@Service
public class MessUserService {

    @Autowired
    private MessUserRepository messUserRepository;
    @Autowired
    private EntityManager entityManager;

    public MessUser save(MessUser messUser){
        try {
            MessUser r=messUserRepository.save(messUser);
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
    public List<MessUser> getmessuserlist(MessUser messUser){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessUser> query = cb.createQuery(MessUser.class);
        Root<MessUser> root = query.from(MessUser.class);
        List<Predicate> predicates = new ArrayList<>();
        //根据是否查看过的状态查询
        if(!HelperClazz.isEmpty(messUser.getRead_status())){
            predicates.add(cb.equal(root.get("read_status"), messUser.getRead_status()));
        }
        //根据用户名查询对应的信息
        if(!HelperClazz.isEmpty(messUser.getUsername())){
            predicates.add(cb.equal(root.get("username"), messUser.getUsername()));
        }
        try {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
            query.orderBy(new ArrayList<Order>(){{add(cb.desc(root.get("cdate")));}});
            List<MessUser> cls= entityManager.createQuery(query).getResultList();
            return cls;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
