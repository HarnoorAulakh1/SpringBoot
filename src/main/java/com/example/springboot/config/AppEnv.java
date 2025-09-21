package com.example.springboot.config;
import com.example.springboot.models.AppCache;
import com.example.springboot.repository.AppCacheRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AppEnv {

    private final HashMap<String,String> cache=new HashMap<>();

    @Autowired
    private AppCacheRepository ob1;

    @Autowired
    private ConfigurableEnvironment environment;

    @PostConstruct
    public void init(){
        System.out.println("Cache updated");
        List<AppCache> data=ob1.findAll();
        //System.out.println(data);
        for(AppCache x:data)
            cache.put(x.getKey(),x.getValue());
        if(cache.containsKey("SMTP_PASS"))
            environment.getSystemProperties().put("spring.mail.password", cache.get("SMTP_PASS"));
        else
            System.out.println("SMTP_PASS not found");
    }

    public String get(String key){
        if(cache.containsKey(key))
            return cache.get(key);
        return "Not_Found";
    }

//    @Scheduled(cron="0 * * * * ?")
//    public void cronRun(){
//        init();
//    }
}
