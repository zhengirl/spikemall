package tech.izhen.service.impl;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.izhen.dao.PromoDOMapper;
import tech.izhen.dataobject.PromoDO;
import tech.izhen.service.PromoService;
import tech.izhen.service.model.PromoModel;

/**
 * @author zhen
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //dataobject->model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if(promoModel == null){
            return null;
        }

        //判断当前秒杀活动是否即将进行或正在进行
        DateTime now = new DateTime();
        if(promoModel.getStartDate().isAfterNow()){
            //未开始
            promoModel.setStatus(1);
        }
        else if(promoModel.getEndDate().isBeforeNow()){
            //已结束
            promoModel.setStatus(3);
        }
        else {
            //正在进行中
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    public PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoModel;
    }
}
