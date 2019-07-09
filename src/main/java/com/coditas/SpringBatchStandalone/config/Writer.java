package com.coditas.SpringBatchStandalone.config;

import com.coditas.SpringBatchStandalone.model.Information;
import com.coditas.SpringBatchStandalone.repository.InformationRepository;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class Writer implements ItemWriter<Information> {

    @Autowired
    InformationRepository repo;

    @Override
    public void write(List<? extends Information> list) throws Exception {
        System.out.println("==========In Writer============");
        for(Information info:list) {
            repo.save(info);
//            System.out.println(info);
        }
    }
}
