package com.babyTalk.parent.service.impl;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.parent.entity.Parent;
import com.babyTalk.parent.entity.VO.ParentPageVO;
import com.babyTalk.parent.mapper.ParentMapper;
import com.babyTalk.parent.service.ParentService;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.mapper.UserMapper;
import com.babyTalk.user.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@Service
public class ParentServiceImpl extends ServiceImpl<ParentMapper, Parent> implements ParentService {
    @Autowired
    private ParentMapper parentMapper;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserMapper userMapper;


    @Override
    public ResponseData<Object> getAll1(Integer current,Integer size,Parent parent){
        Page<Parent> page = new Page<>(current,size);
        final IPage<Parent> parentIPage = parentMapper.selectPage(page, WrapperUtil.queryWrapper(parent));
        return Result.success(parentIPage);
    }
    @Override
    public ResponseData<Object> postParent1(Parent parent){
        final int i = parentMapper.insert(parent);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.POST_ERROR.getMessage(),ResponseStatus.POST_ERROR.getStatus());
        }
    }
    @Override
    public ResponseData<Object> putParent1(Parent parent){
        final int i = parentMapper.updateById(parent);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.PUT_ERROR.getMessage(),ResponseStatus.PUT_ERROR.getStatus());
        }
    }
    @Override
    public ResponseData<Object> delParent1(Integer id){
        final int i = parentMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.DELETE_ERROR.getMessage(),ResponseStatus.DELETE_ERROR.getStatus());
        }
    }
    @Override
    public ResponseData<Object> checkParent(Integer id){
        final Integer user_id = parentMapper.selectCount(WrapperUtil.getQueryWrapper("user_id", id));
        if (user_id >= 2) {
            return Result.error(ResponseStatus.PARENT_IS_OVER.getMessage(),ResponseStatus.PARENT_IS_OVER.getStatus());
        }else {
            return Result.success(ResponseStatus.PARENT_NOT_OVER.getMessage(),ResponseStatus.PARENT_NOT_OVER.getStatus());
        }
    }








    /**
     * 获取所有父母
     * 分页
     * 父母名字搜索
     * @param parentPageVO
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> getAll(ParentPageVO parentPageVO){
        Page<Parent> page = new Page<>();
        if (parentPageVO.getCurrent() != null && parentPageVO.getSize() != null) {
            page = new Page<>(parentPageVO.getCurrent(),parentPageVO.getSize());
        }
        QueryWrapper<Parent> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("created_at").orderByDesc("updated_at");
        if (StringUtils.isNotBlank(parentPageVO.getName())){
            queryWrapper.like("name",parentPageVO.getName());
        }
        final IPage<Parent> parentIPage = parentMapper.selectPage(page, queryWrapper);
        return Result.success(parentIPage);
    }

    /**
     * 添加父母
     * @param parent
     * @return
     */
    @Override
    public ResponseData<Object> postParent(Parent parent, HttpServletRequest request){
        final int i = parentMapper.insert(parent);
        if (i <= 0) {
            return Result.error();
        }
        final User user = jwtUtils.getLoginUserByRequest(request);
        if (parent.getRole().equals("爸爸")){
            user.setFatherId(parent.getId());
        }
        if (parent.getRole().equals("妈妈")){
            user.setMotherId(parent.getId());
        }
        userMapper.updateById(user);

        return Result.success();
    }

    /**
     * 修改父母
     * @param parent
     * @return
     */
    @Override
    public ResponseData<Object> putParent(Parent parent){
        final int i = parentMapper.updateById(parent);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除父母
     * @param id
     * @return
     */
    @Override
    public ResponseData<Object> delParent(Integer id,HttpServletRequest request){
        final Parent parent = parentMapper.selectById(id);
        if (parent==null){
            return Result.error();
        }
        parentMapper.deleteById(id);
        //移除与用户的绑定
        final User user = jwtUtils.getLoginUserByRequest(request);
        if (parent.getRole().equals("爸爸")){
            user.setFatherId(null);
        }
        if (parent.getRole().equals("妈妈")){
            user.setMotherId(null);
        }
        userMapper.updateById(user);
        return Result.success();
    }

}
