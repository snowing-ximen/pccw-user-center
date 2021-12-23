package net.pccw.uc.web.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import net.pccw.uc.common.model.User;
import net.pccw.uc.manager.UserManager;
import net.pccw.uc.web.config.MailBox;
import net.pccw.uc.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private MailBox mailBox;

    @Override
    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                            @HystrixProperty(name="maxQueueSize", value="10"),
                    },
            commandProperties={
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    public Long insert(User user) {
        Long result = userManager.insert(user);
        if(result > 0){
            mailBox.sendHtmlMail("Welcome " + user.getUsername() + " to PCCW");
        }

        return result;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                            @HystrixProperty(name="maxQueueSize", value="10"),
                    },
            commandProperties={
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    @Override
    public Integer updateByUsername(User user) {
        return userManager.updateByUsername(user);
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                            @HystrixProperty(name="maxQueueSize", value="10"),
                    },
            commandProperties={
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    @Override
    public User selectByUsername(String username) {
        return userManager.selectByUsername(username);
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties =
                    {@HystrixProperty(name = "coreSize",value="30"),
                            @HystrixProperty(name="maxQueueSize", value="10"),
                    },
            commandProperties={
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets", value="5")}
    )
    @Override
    public Integer deregisterByUsername(String username) {
        return userManager.updatedDeletedStateByUsername(username);
    }

}
