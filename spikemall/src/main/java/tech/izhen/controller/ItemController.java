package tech.izhen.controller;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.izhen.controller.viewobject.ItemVO;
import tech.izhen.error.BusinessException;
import tech.izhen.response.CommonReturnType;
import tech.izhen.service.ItemService;
import tech.izhen.service.model.ItemModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhen
 * @description 商品的Controller层
 */
@Slf4j
@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType creatItem(@RequestParam(name = "title") String title,
                                      @RequestParam(name = "price")BigDecimal price,
                                      @RequestParam(name = "stock") Integer stock,
                                      @RequestParam(name = "description")String description,
                                      @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.creatItem(itemModel);
        ItemVO itemVO = this.convertFromItemModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);
    }

    /**
     * 商品详情页浏览
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);

        ItemVO itemVO = this.convertFromItemModel(itemModel);

        return CommonReturnType.create(itemVO);
    }

    /**
     * 商品列表页浏览
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> modelList = itemService.listItem();

        //使用stream的api将itemmodel列表转换为itemvo列表
        List<ItemVO> itemVOList = modelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertFromItemModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);
    }

    private ItemVO convertFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);

        if(itemModel.getPromoModel() != null){
            //有正在进行或者即将进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }

}
