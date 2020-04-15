package com.me.store;

import com.me.store.dao.*;
import com.me.store.pojo.Category;
import com.me.store.pojo.Product;
import com.me.store.pojo.User;
import com.me.store.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    @Transactional

    public void test(){
       userMapper.deleteByPrimaryKey(9);
       // User user=userMapper.selectByPrimaryKey(7);
        //System.out.println(user.getUsername());
        int i=1/0;
    }

}
