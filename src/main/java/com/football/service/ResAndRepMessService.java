package com.football.service;

import com.football.domain.ResAndRepMess;
import com.football.repository.ResAndRepMessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2018-3-23.
 */

@Service
public class ResAndRepMessService {
    @Autowired
    private ResAndRepMessRepository resAndRepMessRepository;

    public ResAndRepMess save(ResAndRepMess resAndRepMess){
        try {
            ResAndRepMess r=resAndRepMessRepository.save(resAndRepMess);
            return r;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
