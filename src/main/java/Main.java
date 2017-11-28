import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Main {
    private static final String SPLIT_FLAG = "\\.";
    private static final String INNER_SPLIT_FLAG = ",";

    /**
     * 递归删除指定字段
     *
     * @param outterObject
     * @param source
     * @return
     */
    public static void deleteJsonProperty(Object outterObject, String source) {
        if (outterObject instanceof JSONObject) {
            JSONObject tempJson = (JSONObject) outterObject;
            if (source.contains(".")) {
                String arg = source.substring(0,source.indexOf('.'));
                deleteJsonProperty(tempJson.get(arg), source.substring(source.indexOf('.') + 1));
                return;
            }
            tempJson.remove(source);

        } else if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                JSONObject tempJson = (JSONObject) tempArray.get(i);
                if (source.contains(".")) {
                      String arg = source.substring(0,source.indexOf('.'));
                     deleteJsonProperty(tempJson.get(arg), source.substring(source.indexOf('.') + 1));
                     return;
                } else {
                    tempJson.remove(source);
                }
            }
        }
    }


    /**
     * 递归实现字段保留功能
     * @param outterObject
     * @param source
     */
    public static void keepKeys(Object outterObject,String source){
        if(outterObject instanceof JSONObject) {
            JSONObject obj = ((JSONObject) outterObject);
            if (source.contains(".")) {
                String arg = source.substring(0,source.indexOf('.'));
                keepKeys(obj.get(arg), source.substring(source.indexOf('.') + 1));
                return;
            }
            keepJsonObjectByGivenKeys(obj,source);
        }else if(outterObject instanceof JSONArray){
            JSONArray tempArray = (JSONArray) outterObject;
            for(int i=0;i<tempArray.size();i++){
                JSONObject obj = tempArray.getJSONObject(i);
                if (source.contains(".")) {
                    String arg = source.substring(0,source.indexOf('.'));
                    deleteJsonProperty(obj.get(arg), source.substring(source.indexOf('.') + 1));
                    return;
                } else {
                    keepJsonObjectByGivenKeys(obj,source);
                }
            }
        }
    }
    /**
     *
     * @param jsonObj given manage jsonObejct
     * @param keys given keep keys
     */
    private static void keepJsonObjectByGivenKeys(JSONObject jsonObj,String keys){
        String[] keyAttr = keys.split(INNER_SPLIT_FLAG);
        Set<String> keyKeepSet = new HashSet<String>();
        for(String s:keyAttr)
            keyKeepSet.add(s);
        Set<String> keyRemove =  new HashSet<String>();
        keyRemove.addAll(jsonObj.keySet());
        keyRemove.removeAll(keyKeepSet);
        for(String key:keyRemove)
            jsonObj.remove(key);
    }



    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        String jsonStr = "{\"resCode\":\"00100000\",\"obj\":{\"currentPage\":1,\"pageSize\":10,\"count\":10,\"pages\":1,\"start\":1,\"end\":10,\"list\":[{\"id\":37,\"marketingStartTime\":\"11:14:39\",\"marketingEndTime\":\"22:47:26\",\"state\":\"1\"},{\"id\":36,\"marketingStartTime\":\"11:22:15\",\"marketingEndTime\":\"11:24:20\",\"state\":\"1\"},{\"id\":35,\"marketingStartTime\":\"11:15:00\",\"marketingEndTime\":\"11:16:37\",\"state\":\"1\"},{\"id\":34,\"marketingStartTime\":\"11:05:26\",\"marketingEndTime\":\"11:07:45\",\"state\":\"1\"},{\"id\":33,\"marketingStartTime\":\"11:07:07\",\"marketingEndTime\":\"11:08:12\",\"state\":\"1\"},{\"id\":32,\"marketingStartTime\":\"15:56:10\",\"marketingEndTime\":\"15:57:22\",\"state\":\"1\"},{\"id\":31,\"marketingStartTime\":\"15:28:30\",\"marketingEndTime\":\"15:30:00\",\"state\":\"1\"},{\"id\":29,\"marketingStartTime\":\"17:31:00\",\"marketingEndTime\":\"17:31:30\",\"state\":\"1\"},{\"id\":28,\"marketingStartTime\":\"17:30:00\",\"marketingEndTime\":\"17:30:00\",\"state\":\"1\"},{\"id\":27,\"marketingStartTime\":\"16:53:00\",\"marketingEndTime\":\"16:54:00\",\"state\":\"1\"}]}}";
        String[] keyAtrr = {"obj.list.marketingStartTime","obj.list.marketingEndTime","obj.list.id"};
        JSONObject object = JSON.parseObject(jsonStr);
        keepKeys(object,"obj.list.id,state");
        System.out.println(System.currentTimeMillis());
        System.out.println(object.toJSONString());

        for(String str :keyAtrr){
            deleteJsonProperty(object,str);
        }
        System.out.println(System.currentTimeMillis());
        System.out.println(jsonStr);
        System.out.println(object.toJSONString());
    }
}
