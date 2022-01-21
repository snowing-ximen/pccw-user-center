## User Center

##[Mysql db scripts]

    CREATE TABLE `user` (
      `userId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
      `username` varchar(100) NOT NULL COMMENT '用户名',
      `password` varchar(45) NOT NULL COMMENT '密码',
      `mobile` varchar(11) NOT NULL COMMENT '手机号',
      `email` varchar(128) NOT NULL COMMENT '邮件',
      `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '0: undeleted;\\n1: deleted;',
      `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (`userId`),
      UNIQUE KEY `idx_username` (`username`) USING BTREE,
      KEY `idx_mobile` (`mobile`) USING BTREE,
      KEY `idx_email` (`email`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
    

## REST API
    * 1.Register a user:
        POST /users
        Payload: User
        Return value: HTTP status 200
        
        Examples:
            Request:
                {
                    "username": "user7",
                    "password": "pwd7",
                    "mobile": "17710237777",
                    "email": "snowing@163.com77"
                }
             Response:
                {
                    "code": 200,
                    "message": null,
                    "data": {
                        "userId": 2
                    }
                }
    
    * 2.Update a user:
        PUT /users
        Payload: User
        Return value: HTTP status 200
        
        Examples:
            Request:
                {
                    "username": "user7",
                    "password": "pwd7",
                    "mobile": "17710237777",
                    "email": "snowing@163.com77"
                }
             Response:
                {
                    "code": 200,
                    "message": null,
                    "data": true
                }
    
    * 3.Update a user:
        PUT /users/{username}
        Payload: User
        Return value: HTTP status 200
        
        Examples:
            PUT /users/user7
            Request:
                {
                    "username": "user7",
                    "password": "pwd7",
                    "mobile": "17710237777",
                    "email": "snowing@163.com77"
                }
             Response:
                {
                    "code": 200,
                    "message": null,
                    "data": true
                }
    
    * 3.Retrieve a user:
        GET /users/{username}
        Payload: User
        Return value: HTTP status 200
        
        Examples:
            GET /users/user7
            Response:
                {
                    "code": 200,
                    "message": null,
                    "data": {
                        "userId": 2,
                        "username": "user2",
                        "password": "pwd2",
                        "mobile": "17710232222",
                        "email": "snowing@163.com"
                    }
                }
    
     * 4.Deregister a user:
         DELETE /users/{username}
         Payload: User
         Return value: HTTP status 200
         
         Examples:
            PUT /users/user7
            Response:
             {
                 "code": 200,
                 "message": null,
                 "data": true
             }
             
     * 5.The above endpoints are delegated to /api/uc/users/{username} by Zuul gateway.


## Spring Cloud components
    * zuul
    * eureka
    * nacos
    * hystrix
    
   
    
