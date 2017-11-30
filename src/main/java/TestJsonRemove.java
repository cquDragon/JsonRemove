import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.TreeSet;

/**
 * Created by XAL16 on 2017-11-30.
 */
public class TestJsonRemove {
    public static void  test_KeepKeys(String jsonStr,String key){
        System.out.println("before: "+ jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        System.out.println(System.currentTimeMillis()+"test_KeepKeys============start");
        Main.keepKeys(object, key);
        System.out.println(System.currentTimeMillis()+"test_KeepKeys============end");
        System.out.println(object.toJSONString());
        System.out.println();
        System.out.println();
        System.out.println();

    }


    public static void  test_Delete(String jsonStr,String key){
        System.out.println("before: "+ jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        System.out.println(System.currentTimeMillis()+"test_Delete============start");
        Main.deleteJsonProperty(object, key);
        System.out.println(System.currentTimeMillis()+"test_Delete============end");
        System.out.println(object.toJSONString());
        System.out.println();
        System.out.println();
        System.out.println();
    }


    public static void  test_DeleteByStrArry(String jsonStr,String[] keyAtrr){
        System.out.println("before: "+ jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        System.out.println(System.currentTimeMillis()+"test_DeleteByStrArry============start");
        for (String str : keyAtrr) {
            Main.deleteJsonProperty(object, str);
        }
        System.out.println(System.currentTimeMillis()+"test_DeleteByStrArry============end");
        System.out.println(object.toJSONString());
        System.out.println();
        System.out.println();
        System.out.println();
    }


    public static void  test_KeepByGivenTreeSet(String jsonStr,TreeSet keySet){
        System.out.println("before: "+ jsonStr);
        JSONObject object = JSON.parseObject(jsonStr);
        System.out.println("test_KeepByGivenTreeSet_start============"+System.currentTimeMillis());
        Main.keepKeysByGivenKeySet(keySet,object);
        System.out.println("test_KeepByGivenTreeSet_end============"+System.currentTimeMillis());
        System.out.println(object.toJSONString());

        System.out.println();
        System.out.println();
        System.out.println();
    }
}
