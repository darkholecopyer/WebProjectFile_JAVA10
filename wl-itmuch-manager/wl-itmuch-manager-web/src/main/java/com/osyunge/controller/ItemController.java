package com.osyunge.controller;

import com.osyunge.dataobject.EasyUIDataGridResult;
import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbItem;
import com.osyunge.pojo.TbItemDesc;
import com.osyunge.service.ItemService;
import com.osyunge.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;

/**
 * 添加商品
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private TbItemDescService tbItemDescService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @GetMapping("/{itemId}")
    @ResponseBody
    public TbItem selectItemInfoById(@PathVariable long itemId){
        return itemService.selectItemInfoById(itemId);
    }
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(int page,int rows){
        return itemService.getItemList(page,rows);
    }


    /*删除商品*/
    @RequestMapping("/delete")
    @ResponseBody
    public FCResult deleteItem(String ids){
        String[] idStr = ids.split(",");
        for(String id:idStr){
            long l = Long.parseLong(id);
            itemService.deleteItemById(l);
        }
        FCResult result = new FCResult();
        result.setStatus(200);
        return result;
    }
    /*控制商品上下架*/
    @RequestMapping("/instock")
    @ResponseBody
    public FCResult instock(String ids){
        String[] idStr = ids.split(",");
        for(String id:idStr){
            long l = Long.parseLong(id);
            itemService.instockItemById(l);
        }
        FCResult result = new FCResult();
        result.setStatus(200);
        return result;
    }
    /*控制商品上架*/
    @RequestMapping("/reshelf")
    @ResponseBody
    public FCResult reshelf(String ids){
        String[] idStr = ids.split(",");
        for(String id:idStr){
            long l = Long.parseLong(id);
            itemService.reshelfItemById(l);
        }
        FCResult result = new FCResult();
        result.setStatus(200);
        return result;
    }

    /*
    * 添加商品
    * */
    @RequestMapping("/save")
    @ResponseBody
    public FCResult save(TbItem tbItem, String desc){
        //保存图片,在PicController中已经处理了
        //获取tbItem数据存表
        Date date = new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        byte b = Byte.parseByte("1");
        tbItem.setStatus(b);

        //自动保存图片路径

        itemService.addItem(tbItem);
        long itemId =tbItem.getId();
        /*无法获取id，无法通过id向描述表插记录*/
        /*使用数据库自动递增*/
        //保存描述
        tbItemDescService.addItemDesc(desc,itemId);

        //保存规格
        //发送一个商品添加信息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId+"");
                return textMessage;
            }
        });

        return FCResult.ok();
    }
//    获取描述
    @RequestMapping("/desc/{descId}")
    @ResponseBody
    public FCResult desc(@PathVariable long descId){
        TbItemDesc itemDesc = tbItemDescService.getItemDescById(descId);
        FCResult result = new FCResult();
        result.setStatus(200);
        result.setData(itemDesc);

        return result;
    }
//    获取规格
    @RequestMapping("/param/{id}")
    @ResponseBody
    public FCResult param(@PathVariable long id){
        TbItemDesc itemDesc = tbItemDescService.getItemDescById(id);
        FCResult result = new FCResult();
        result.setStatus(200);
        result.setData(itemDesc);
        return result;
    }
    /*
    编辑商品
    因为无法获取id所以暂时放弃
    */
    @RequestMapping("/update")
    public FCResult update(TbItem tbItem,String desc,Long id){
        tbItem.setId(id);
        itemService.updateTbItem(tbItem);
        tbItemDescService.updateTbDesc(tbItem.getId(),desc);
        FCResult result = new FCResult();
        result.setStatus(200);
        return result;
    }
}
