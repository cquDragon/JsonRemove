import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by XAL16 on 2018-1-5.
 */
public class JsonPuter {

    public void jsonPutter(Object outterObject, String source) {
        if (outterObject == null || source == null)
            return;
        if ("".equals(source)) {
            doPut(outterObject);
            return;
        }
        if (outterObject instanceof JSONObject) {
            if (source.contains(".")) {
                String arg = source.substring(0, source.indexOf('.'));
                jsonPutter(((JSONObject) outterObject).get(arg), source.substring(source.indexOf('.') + 1));
                return;
            } else if (!source.contains(".")) {
                doPut(((JSONObject) outterObject).get(source));
                return;
            }
        }
        if (outterObject instanceof JSONArray) {
            JSONArray tempArray = (JSONArray) outterObject;
            for (int i = 0; i < tempArray.size(); i++) {
                JSONObject tempJson = (JSONObject) tempArray.get(i);
                if (source.contains(".")) {
                    String arg = source.substring(0, source.indexOf('.'));
                    jsonPutter(tempJson.get(arg), source.substring(source.indexOf('.') + 1));
                    return;
                } else {
                    doPut(tempJson);
                }
            }
        }
    }

    private static void doPut(Object obj) {
        if (obj instanceof JSONObject)
            ((JSONObject) obj).put("memberId", "aaa");
        else if (obj instanceof JSONArray) {
            for (int i = 0; i < ((JSONArray) obj).size(); i++) {
                ((JSONArray) obj).getJSONObject(i).put("memberId", "aaa");
            }
        }
    }

    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\"pages\":1,\"obj\":{\"marketingStartTime\":\"11:22:15\",\"id\":{ \"marketingEndTime\":\"22:47:26\",\n" +
                "            \"state\":\"1\"},\"marketingEndTime\":\"11:24:20\",\"state\":\"1\"},\"count\":10,\"start\":1,\"pageSize\":10,\"end\":10,\"currentPage\":1,\"list\":[{\"marketingStartTime\":\"11:14:39\",\"id\":37,\"marketingEndTime\":\"22:47:26\",\"state\":\"1\"},{\"marketingStartTime\":\"11:22:15\",\"id\":36,\"marketingEndTime\":\"11:24:20\",\"state\":\"1\"}]}");
        System.out.println(jsonObject.toJSONString());
        new JsonPuter().jsonPutter(jsonObject, "obj.id");
        System.out.println(jsonObject.toJSONString());
    }
}
