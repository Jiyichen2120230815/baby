package com.babyTalk.baby.service.impl;

import com.alibaba.fastjson.JSON;
import com.babyTalk.baby.entity.Baby;
import com.babyTalk.baby.entity.VO.BabyPageVO;
import com.babyTalk.baby.mapper.BabyMapper;
import com.babyTalk.baby.service.BabyService;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.mapper.UserMapper;
import com.babyTalk.user.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class BabyServiceImpl extends ServiceImpl<BabyMapper, Baby> implements BabyService {
    @Autowired
    private BabyMapper babyMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserMapper userMapper;


    /**
     * 获取所有宝宝
     * @param current
     * @param size
     * @param baby
     * @return
     */
    @Override
    public ResponseData<Object> getAll1(Integer current, Integer size, Baby baby){
        Page<Baby> page = new Page<>(current,size);
        final IPage<Baby> babyIPage = babyMapper.selectPage(page, WrapperUtil.queryWrapper(baby));
        return Result.success(babyIPage);
    }
    @Override
    public ResponseData<Object> postBaby1(Baby baby){
        final int i = babyMapper.insert(baby);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.POST_ERROR.getMessage(),ResponseStatus.POST_ERROR.getStatus());
        }
    }
    @Override
    public ResponseData<Object> putBaby1(Baby baby){
        final int i = babyMapper.updateById(baby);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.PUT_ERROR.getMessage(),ResponseStatus.PUT_ERROR.getStatus());
        }
    }
    @Override
    public ResponseData<Object> delBaby1(Integer id){
        final int i = babyMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.DELETE_ERROR.getMessage(),ResponseStatus.DELETE_ERROR.getStatus());
        }
    }
















    /**
     * 获取所有宝宝
     * 名字搜索
     * 分页
     * @param babyPageVO
     */
    @Override
    public ResponseData<Object> getAll(BabyPageVO babyPageVO){
        QueryWrapper<Baby> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at").orderByDesc("updated_at");
        if (StringUtils.isNotBlank(babyPageVO.getName())){
            queryWrapper.like("name",babyPageVO.getName());
        }
        Page<Baby> page = new Page<>();
        if (babyPageVO.getCurrent() != null && babyPageVO.getSize() != null) {
            page = new Page<>(babyPageVO.getCurrent(),babyPageVO.getSize());
        }
        final IPage<Baby> babyIPage = babyMapper.selectPage(page, queryWrapper);
        return Result.success(babyIPage);
    }

    /**
     * 添加宝宝
     * @param baby
     */
    @Override
    public ResponseData<Object> postBaby(Baby baby, HttpServletRequest request){
        final int i = babyMapper.insert(baby);
        if (i <= 0){
            return Result.error();
        }
        //绑定用户
        final User user = jwtUtils.getLoginUserByRequest(request);
        final List<Integer> list = JSON.parseArray(user.getBabyIds(), Integer.class);
        list.add(baby.getId());
        user.setBabyIds(list.toString());
        userMapper.updateById(user);
        return Result.success();
    }

    /**
     * 修改宝宝
     * @param baby
     */
    @Override
    public ResponseData<Object> putBaby(Baby baby){
        final int i = babyMapper.updateById(baby);
        if (i > 0){
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除宝宝
     * @param id
     */
    @Override
    public ResponseData<Object> delBaby(Integer id,HttpServletRequest request){
        final int i = babyMapper.deleteById(id);
        if (i <= 0){
            return Result.error();
        }
        //用户中移除绑定
        final User user = jwtUtils.getLoginUserByRequest(request);
        final List<String> list = JSON.parseArray(user.getBabyIds(), String.class);
        list.remove(id.toString());
        user.setBabyIds(list.toString());
        userMapper.updateById(user);

        return Result.success();
    }


}
