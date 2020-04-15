package com.me.store.controller.backend;

import com.google.common.collect.Maps;
import com.me.store.common.Const;
import com.me.store.common.ResponseCode;
import com.me.store.common.ServerResponse;
import com.me.store.pojo.Product;
import com.me.store.pojo.User;
import com.me.store.service.IFileService;
import com.me.store.service.IProductService;
import com.me.store.service.IUserService;
import com.me.store.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/manage/product/")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;
    /**
     * 新增或更新产品
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do",method = RequestMethod.POST)
    public ServerResponse saveProduct(HttpSession session,Product product){
        return iProductService.SaveOrUpdateProduct(product);
    }

    /**
     * 更新产品销售状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.POST)
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
              return iProductService.setSaleStatus(productId,status);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse getDetailProduct(HttpSession session,Integer productId){
        return iProductService.getManageDetailProduct(productId);
    }
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value ="pageSize",defaultValue = "10") Integer pageSize){
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iProductService.selectList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }
    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    public ServerResponse search(HttpSession session, String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value ="pageSize",defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "需要登陆");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            return iProductService.searchByNameAndId(productName, productId, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");
    }

    /**
     * 上传商品图片到FTP服务器
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    public ServerResponse upload(@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request,HttpSession session){
        //默认在webapp下的upload目录
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"需要登陆");
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            String path=request.getServletContext().getRealPath("upload");
            return iFileService.upload(file,path);
        }
        return ServerResponse.createByErrorMessage("该用户不是管理员");

    }

    /**
     * 富文本图片上传
     * @param file
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "richtext_upload.do",method = RequestMethod.POST)
    public Map richtextUpload(@RequestParam(value = "upload_file",required = false) MultipartFile file,
                                         HttpServletRequest request, HttpSession session, HttpServletResponse response){
        Map resultMap=Maps.newHashMap();
        User user=(User)session.getAttribute(Const.CURRENT_USER);
        if(user==null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        if (iUserService.checkAdmin(user).isSuccess()) {
            //存放在webapp下的upload下
            String path=request.getServletContext().getRealPath("upload");
            ServerResponse<Map> serverResponse=iFileService.upload(file,path);
            resultMap.put("success",true);
            resultMap.put("msg",serverResponse.getData().get("url"));
            return resultMap;
        }
        resultMap.put("success",false);
        resultMap.put("msg","不是管理员");
        return resultMap;

    }


}
