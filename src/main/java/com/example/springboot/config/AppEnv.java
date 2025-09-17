package com.example.springboot.config;
import com.example.springboot.models.AppCache;
import com.example.springboot.repository.AppCacheRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AppEnv {

    private final HashMap<String,String> cache=new HashMap<>();

    @Autowired
    private AppCacheRepository ob1;

    @PostConstruct
    public void init(){
        System.out.println("Cache updated");
        List<AppCache> data=ob1.findAll();
        //System.out.println(data);
        for(AppCache x:data)
            cache.put(x.getKey(),x.getValue());
    }

    public String get(String key){
        if(cache.containsKey(key))
            return cache.get(key);
        return "Not_Found";
    }

    @Scheduled(cron="0 * * * * ?")
    public void cronRun(){
        init();
    }
}
