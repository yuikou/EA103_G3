/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *        �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_sitSrv {

	public static String get_aCondition_For_Oracle(String columnName, String value) {
		String memAddress = null;
		String aCondition = null;
		
		if ("addBathing".equals(columnName) || "addPickup".equals(columnName) || "careLevel".equals(columnName) || "stayLoc".equals(columnName)) // �Ω��L
			aCondition = " and " + columnName + "=" + value;
		else if ("overnightLoc".equals(columnName) || "smkFree".equals(columnName) || "hasChild".equals(columnName) || "eqpt".equals(columnName))
			aCondition = " and " + columnName + "=" + value;
		else if ("acpPetNum".equals(columnName))
			aCondition = " and " + columnName + " >= " + value;
		else if ("srvFee".equals(columnName))
			aCondition = " and " + columnName + " <= " + value;
		else if ("nearAddr".equals(columnName)) {// �Ω�varchar
			if (columnName!=null) {
				int city_index = value.indexOf("��");
				int county_index = value.indexOf("��");
				if (city_index != -1) {
					memAddress = value.substring(city_index-2,city_index+1);
				} else if (county_index != -1) {
					memAddress = value.substring(county_index-2,city_index+1);
				}
			}
			aCondition = " and memAddress like '%" + memAddress + "%'";
		} else {
			aCondition = "";
		}
		

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		String orderBy = " order by ";
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				
				if("orderBy".equals(key)) {
					if("priceLower".equals(value)) {
						orderBy = " order by srvFee";
					}else {
						orderBy += value + " DESC";
					}
				} else {
					String aCondition = get_aCondition_For_Oracle(key, value.trim());
					whereCondition.append(aCondition);
				}
			}
		}
		if(orderBy.length() >10)
			return whereCondition.append(orderBy).toString();
		else
			return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("empno", new String[] { "7001" });
		map.put("ename", new String[] { "KING" });
		map.put("job", new String[] { "PRESIDENT" });
		map.put("hiredate", new String[] { "1981-11-17" });
		map.put("sal", new String[] { "5000.5" });
		map.put("comm", new String[] { "0.0" });
		map.put("deptno", new String[] { "10" });
		map.put("action", new String[] { "getXXX" }); // �`�NMap�̭��|�t��action��key

		String finalSQL = "select * from emp2 "
				          + jdbcUtil_CompositeQuery_sitSrv.get_WhereCondition(map)
				          + "order by empno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
