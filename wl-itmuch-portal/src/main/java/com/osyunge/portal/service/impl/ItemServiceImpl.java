package com.osyunge.portal.service.impl;

import com.osyunge.dataobject.FCResult;
import com.osyunge.pojo.TbItem;
import com.osyunge.pojo.TbItemDesc;
import com.osyunge.pojo.TbItemParamItem;
import com.osyunge.portal.service.ItemService;
import com.osyunge.utils.HttpClientUtil;
import com.osyunge.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public TbItem getItemById(Long itemId){
        try {
            //调用rest的服务查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
            if (!StringUtils.isBlank(json)){
                FCResult fcResult = FCResult.formatToPojo(json,TbItem.class);
                if (fcResult.getStatus() == 200){
                    TbItem item = (TbItem)fcResult.getData();
                    return item;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemDescById(Long itemId){
        try {
            //查询商品描述
            String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
            System.out.println("日志："+REST_BASE_URL+ITEM_INFO_URL+itemId);
            System.out.println("json"+json);
            //转换成java对象
            FCResult fcResult = FCResult.formatToPojo(json,TbItemDesc.class);
            System.out.println("结果："+fcResult);
            if (fcResult.getStatus() == 200){
                TbItemDesc itemDesc = (TbItemDesc)fcResult.getData();
                //取商品描述信息
                String result = itemDesc.getItemDesc();
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //根据id查询规格参数
    @Override
    public String getItemParam(Long itemId){
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            //把json转换为对象
            FCResult fcResult = FCResult.formatToPojo(json, TbItemParamItem.class);
            if (fcResult.getStatus() == 200){
                System.out.println(fcResult.getData());
                TbItemParamItem itemParamItem = (TbItemParamItem)fcResult.getData();
                String paramData = itemParamItem.getParamData();
                //生成html
                //把规格参数json数据转换成json对象
                List<Map> jsonList = JsonUtils.jsonToList(paramData,Map.class);
                StringBuffer sb = new StringBuffer();
                sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                sb.append("    <tbody>\n");
                for (Map m1:jsonList){
                    sb.append("   <tr>\n");
                    sb.append("     <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                    sb.append("  </tr>\n");
                    List<Map> list2 = (List<Map>)m1.get("params");
                    for (Map m2:list2){
                        sb.append("     <tr>\n");
                        sb.append("      <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                        sb.append("      <td>"+m2.get("v")+"</td>\n");
                        sb.append("    </tr>\n");
                    }
                }
                sb.append("   </tbody>\n");
                sb.append("</table>");
                //返回html片段
                return sb.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
