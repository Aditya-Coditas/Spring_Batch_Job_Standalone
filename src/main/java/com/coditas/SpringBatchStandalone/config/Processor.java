package com.coditas.SpringBatchStandalone.config;

import com.coditas.SpringBatchStandalone.model.Information;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Information,Information> {

    @Override
    public Information process(Information information) throws Exception {
            String fn=information.getFirstName().toUpperCase();
            String ln=information.getLastName().toUpperCase();
            Information info=new Information();
            info.setFirstName(fn);
            info.setLastName(ln);
            System.out.println(info);
            return info;
    }
}
