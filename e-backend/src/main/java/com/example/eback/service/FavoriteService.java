package com.example.eback.service;

import com.example.eback.dao.FavoriteDAO;
import com.example.eback.dao.StockDAO;
import com.example.eback.dao.UserDAO;
import com.example.eback.entity.Stock;
import com.example.eback.entity.User_Stock;
import com.example.eback.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FavoriteService {
    private static final Logger logger = LoggerFactory.getLogger(UserService. class);
    @Autowired
    UserDAO userDAO;

    @Autowired
    StockDAO stockDAO;

    @Autowired
    FavoriteDAO favoriteDAO;

    /**
     * 查看关注的股票
     * @param uid
     * @param page
     * @param size
     * @return
     */
    public MyPage<Stock> findAll(int uid, int page, int size){
        MyPage<Stock> stocks=new MyPage<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Page<User_Stock> user_stocks=favoriteDAO.findAllByUid(uid, PageRequest.of(page, size, sort));
        List<Stock> stockList =new ArrayList<>();
        for (User_Stock u_s : user_stocks.getContent()){
            logger.info(u_s.getSid());
            stockList.add(stockDAO.findById(u_s.getSid()));
        }
        stocks.setContent(stockList);
        stocks.setTotalElements(user_stocks.getTotalElements());
        stocks.setPageNumber(user_stocks.getPageable().getPageNumber());
        stocks.setPageSize(user_stocks.getPageable().getPageSize());
        stocks.setNumberOfElements(user_stocks.getNumberOfElements() );

        return  stocks;
    }
    /**
     * 添加关注的股票，成功添加返回1，已有返回0
     * @param uid
     * @param sid
     * @return
     */
    public int addFavorite(int uid,String sid){
        User_Stock user_stock=new User_Stock();
        if (!stockDAO.existsById(sid)){
            return  2;
        }
        if (favoriteDAO.existsUser_StockBySidAndUid(sid,uid)){
            return 0;
        }
        user_stock.setSid(sid);
        user_stock.setUid(uid);
        logger.info("user_stock",user_stock);
        favoriteDAO.save(user_stock);
        return  1;
    }

    /**
     * 删除关注的股票，成功返回1 失败（没有关注）返回0
     * @param uid
     * @param sid
     * @return
     */
    public int deleteFavorite(int uid,String sid){
        if (!favoriteDAO.existsUser_StockBySidAndUid(sid,uid)){
            return 0;
        }
        User_Stock u=favoriteDAO.findByUidAndSid(uid,sid);

        favoriteDAO.delete(u);

        return  1;
    }
}
