import java.util.*;

/**
 * Created by XAL16 on 2018-2-2.
 */
public class JsonPathKeepUtil {

    public static void main(String[] args) {
        String pathStr = "resCode,obj.start,obj.count,obj.list.couponName,obj.list.jumpType";

        Map<String, Object> pathMap = pathStr2Map(pathStr);
        System.out.println(pathMap);
        Set<String> resultSet = new TreeSet<String>();
        getKeepStylePath("", pathMap,resultSet);
        System.out.println(resultSet);

    }

    /***
     * pathStrt
     * @param pathStr
     * @return
     */
    public static Map<String, Object> pathStr2Map(String pathStr) {
        Map<String, Object> pathMap = new HashMap<String, Object>();
        String[] paths = pathStr.split(",");
        for (String path : paths) {
            String[] tmp = path.split("\\.");
            if (tmp.length == 1) {
                pathMap.put(tmp[0], null);
            }
            Map<String, Object> tmpMap = pathMap;
            for (int i = 0; i < tmp.length; i++) {
                if (tmpMap.get(tmp[i]) != null)
                    tmpMap = (HashMap<String, Object>) tmpMap.get(tmp[i]);
                else if (i < tmp.length) {
                    tmpMap.put(tmp[i], new HashMap<String, Object>());
                    tmpMap = (HashMap<String, Object>) tmpMap.get(tmp[i]);
                } else
                    tmpMap.put(tmp[i], null);
            }
        }
        return pathMap;
    }

    /**
     * 获取key打印，用逗号隔开
     *
     * @param map
     * @return
     */
    public static String getKeySpiltBy(Map<String, Object> map) {
        if (map == null || map.size() == 0)
            return "";
        Set<String> set = map.keySet();
        String resultStr = "";
        for (String key : set) {
            resultStr += key;
            resultStr += ",";
        }
        return resultStr.substring(0, resultStr.length() - 1);
    }

    /**
     * 获取路径方法
     *
     * @param path
     * @param map
     */
    public static void getKeepStylePath(String path, Map<String, Object> map, Set<String> resultSet) {
        if (map == null || map.size() == 0) {
            return;
        }
        if ("".equals(path)) {
            resultSet.add(getKeySpiltBy(map));
            path = "";
        } else {
            resultSet.add(path + "." + getKeySpiltBy(map));
            path += ".";
        }
        for (String key : map.keySet()) {
            getKeepStylePath(path + key, (Map<String, Object>) map.get(key), resultSet);

        }
    }

}
