package com.babyTalk.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.babyTalk.baby.entity.Baby;
import com.babyTalk.baby.mapper.BabyMapper;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.StringArrUtil;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.parent.entity.Parent;
import com.babyTalk.parent.mapper.ParentMapper;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.entity.vo.UserPageVO;
import com.babyTalk.user.entity.vo.UserVO;
import com.babyTalk.user.mapper.UserMapper;
import com.babyTalk.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private BabyMapper babyMapper;


    /**
     * 获取所有用户
     * @param current
     * @param size
     * @param user
     * @return
     */
    @Override
    public ResponseData<Object> getAll1(Integer current,Integer size,User user){
        Page<User> page = new Page<>(current,size);
        final IPage<User> userIPage = userMapper.selectPage(page, WrapperUtil.queryWrapper(user));
        List<UserVO> userVOList = new ArrayList<>();
        for (User record : userIPage.getRecords()) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(record,userVO);
            userVO.setParentList(parentMapper.selectList(WrapperUtil.getQueryWrapper("user_id",record.getId())));
            userVO.setBabyList(babyMapper.selectList(WrapperUtil.getQueryWrapper("user_id",record.getId())));
            userVOList.add(userVO);
        }
        Page<UserVO> userVOPage = new Page<>(current,size);
        BeanUtils.copyProperties(page,userVOPage);
        userVOPage.setRecords(userVOList);
        return Result.success(userVOPage);
    }








//    /**
//     * 获取所有用户
//     * 用户角色搜索
//     * 用户名搜索
//     * 用户昵称搜索
//     * @param userPageVO
//     * @return ResponseData<Object>
//     */
//    @Override
//    public ResponseData<Object> getAll(UserPageVO userPageVO){
//        Page<User> page = new Page<>();
//        if (userPageVO.getCurrent() != null && userPageVO.getSize() != null) {
//           page = new Page<>(userPageVO.getCurrent(),userPageVO.getSize());
//        }
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("created_at").orderByDesc("updated_at");
//        if (userPageVO.getRole() != null) {
//            queryWrapper.eq("role",userPageVO.getRole());
//        }
//        if (userPageVO.getUsername() != null) {
//            queryWrapper.like("username",userPageVO.getUsername());
//        }
//        if (userPageVO.getNickname() != null) {
//            queryWrapper.like("nickname",userPageVO.getNickname());
//        }
//        final IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
//
//        Page<UserVO> userVOPage = new Page<>();
//        List<UserVO> userVOList = new ArrayList<>();
//        for (User record : userIPage.getRecords()) {
//            UserVO userVO = new UserVO();
//            BeanUtils.copyProperties(record,userVO);
//            if (record.getMotherId() != null) {
//                userVO.setParentMother(parentMapper.selectById(record.getMotherId()));
//            }
//            if (record.getFatherId() != null) {
//                userVO.setParentFather(parentMapper.selectById(record.getFatherId()));
//            }
//            if (StringUtils.isNotBlank(record.getBabyIds())){
//                final List<Integer> babyIds = JSON.parseArray(record.getBabyIds(), Integer.class);
//                if (babyIds.size() != 0) {
//                    QueryWrapper<Baby> babyQueryWrapper = new QueryWrapper<>();
//                    babyQueryWrapper.in("id",babyIds);
//                    userVO.setBabyList(babyMapper.selectList(babyQueryWrapper));
//                }
//            }
//            userVOList.add(userVO);
//        }
//        BeanUtils.copyProperties(userIPage,userVOPage);
//        userVOPage.setRecords(userVOList);
//        return Result.success(userVOPage);
//    }

    /**
     * 添加用户
     * @param user 用户
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> postUser(User user){
        //密码加密
        if (StringUtils.isNotBlank(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        //宝宝id集合格式处理
        if (StringUtils.isNotBlank(user.getBabyIds())) {
            user.setBabyIds(StringArrUtil.stringArrTOStringList(user.getBabyIds()));
        }
        final int i = userMapper.insert(user);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 修改用户
     * @param user 用户
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> putUser(User user){
        //密码加密
        if (StringUtils.isNotBlank(user.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        //宝宝id集合格式处理
        if (StringUtils.isNotBlank(user.getBabyIds())) {
            user.setBabyIds(StringArrUtil.stringArrTOStringList(user.getBabyIds()));
        }
        final int i = userMapper.updateById(user);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除用户
     * @param id id
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> delUser(Integer id){
        final int i = userMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }


}
