/////////////////////////////////////////////////////////////////////////
// Utils - Auto generated file (contains all LU functions and Globals)
/////////////////////////////////////////////////////////////////////////
	
package com.k2view.cdbms.lu.AT_MRT;

import java.util.*;
import java.sql.*;
import java.math.*;
import java.io.*;
import com.k2view.cdbms.shared.*;
import com.k2view.cdbms.sync.*;
import com.k2view.cdbms.lut.*;
import com.k2view.cdbms.shared.logging.LogEntry.*;
import com.k2view.cdbms.func.oracle.OracleToDate;
import com.k2view.cdbms.func.oracle.OracleRownum;

public class Utils extends UserUtils {

	public Utils(LUType luType,ExecutionContext context) throws ClassNotFoundException, SQLException {
		super(luType,context);
        LUDBBase ludb = getLudb();
        if(ludb != null) {
            if(ludb.getConnection() != null) {
                initSqliteUDFs(ludb.getConnection());
            }
        }		
	}
	
	////////////////////////////////////////////
	// Register as LUDB functions (Sqlite UDFs)
	////////////////////////////////////////////
	public void initSqliteUDFs(Connection conn) 
		throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");

		org.sqlite.Function.create(conn, "java_concat_str", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)java_concat_str(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "concat", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)concat(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "TO_DATE", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)TO_DATE(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "LOWER", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)LOWER(value_text(0)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "TO_CHAR", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)TO_CHAR(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "NVL", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)NVL(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "lu2json", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((String)lu2json(value_text(0)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});


org.sqlite.Function.create(conn, "getRowNum", new org.sqlite.Function() {
			protected void xFunc() throws SQLException {
				try {
					result((Integer)getRowNum(value_text(0), value_text(1)));
				} catch (SQLException e) {
					throw e;
				} catch (Exception e) {
					throw new RuntimeException(e.toString());
				}
			}
		});



	}
	


	/*  */
	public Object fnFieldsRT1(Map<String,Object> map) throws Exception {
//return new Object [] {map.get(""),map.get("")};
return new Object [] {map.get("0"),map.get("1"),map.get("2"),map.get("3")};
	}


/*  */
	public Object fnFieldsRT2(Map<String,Object> map) throws Exception {
//return new Object [] {map.get(""),map.get("")};
return new Object [] {map.get("0"),map.get("1"),map.get("2"),map.get("3"),map.get("4"),map.get("5")};
	}


/*  */
	public Object fnRtSwitch(Map<String,Object> mapIn) throws Exception {
String key = mapIn.get("0").toString();
if(key.equals("100")){
	return new Object [] {mapIn, null};
}else{
	return new Object [] {null, mapIn};
}
	}


/*  */
	public Object k2_breakDate(String dateStr, String format) throws Exception {
if(dateStr == null || format == null)
	return null;

java.text.DateFormat dateFormat = new java.text.SimpleDateFormat(format);
java.util.Date date = dateFormat.parse(dateStr);

java.util.Calendar calendar = java.util.Calendar.getInstance();
calendar.setTime(date);  
			
Object[] arr = new Object[] {
	calendar.get(java.util.Calendar.YEAR),
	calendar.get(java.util.Calendar.MONTH) + 1,
	calendar.get(java.util.Calendar.DAY_OF_MONTH),
	calendar.get(java.util.Calendar.HOUR),
	calendar.get(java.util.Calendar.MINUTE),
	calendar.get(java.util.Calendar.SECOND),
	calendar.get(java.util.Calendar.MILLISECOND),
};
			
return arr;
	}


/*  */
	public String java_concat_str(String i_str, String i_con_str) throws Exception {
return i_str + i_con_str + "shared";

//return s.concat ("_RIVI");
//return i_str.concat ("_RIVI");
	}


/*  */
	public void k2_StreamParser(String folderPath, String folderPathDebug, String regexFilter, String endOfFrameString, String quote, String delimiter) throws Exception {
boolean inDebug = inDebugMode();
if (inDebug){
	folderPath = folderPathDebug;
}
FolderReader reader = new FolderReader(folderPath, regexFilter);
InputStreamReader stream = null;

while ((stream = reader.getNextStream()) != null) {
	try{	
		Object[] row = null;
		do {
			row = getStreamMap(stream, quote.charAt(0), delimiter.charAt(0), endOfFrameString);
		    yield(row);
		} while (row !=null && row[0] != null);
	}
	finally{
		try{
			stream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}	
	// Drop stream only in running mode
	if(!inDebug) { // Running mode
		reader.dropStream(stream);
	}
}

	}


/*  */
	public String getNewPrdCdRef(String prd_cd) throws Exception {
String sql = "SELECT NEW_PRODUCT_CODE FROM REF_YP_PRODUCT_TRN WHERE OLD_PRODUCT_CODE=?";

Object[] valuesArr = {prd_cd};

Object val = DBSelectValue("ludb", sql, valuesArr);

return val.toString();




	}


/* minus one parameter from another to get a result */
	public Double k2_minus(Double i_num1, Double i_num2) throws Exception {
if(i_num1 == null || i_num2 == null)
	return null;
return i_num1 - i_num2;
	}


/* Perform a regular expression match */
	public Boolean k2_regexp_match(String pattern, String subject) throws Exception {
if(pattern == null || subject == null)
	return false;
return subject.matches(pattern);
	}


/*  */
	public String concat(String i_str, String i_con_str) throws Exception {
return i_con_str + i_str + "shared";
	}


/*  */
	public Integer sleep(Integer time_ms) throws Exception {
Thread.sleep(time_ms);
return time_ms;
	}


/* get the list of all files matching file_regExp in a specific path */
	public Object[] k2_find_files(String path, String file_regExp) throws Exception {
java.nio.file.Path dir = java.nio.file.Paths.get(path);
ArrayList<String> al = new ArrayList<String>();

// the Files class offers methods for validation
if (!java.nio.file.Files.exists(dir)
		|| !java.nio.file.Files.isDirectory(dir)) {
	throw new java.io.IOException("Directory does not exist: "
			+ dir);
}

java.nio.file.PathMatcher pathMatcher;
pathMatcher = java.nio.file.FileSystems.getDefault().getPathMatcher("regex:" + file_regExp);

try (java.nio.file.DirectoryStream<java.nio.file.Path> ds = java.nio.file.Files
		.newDirectoryStream(dir)) {
	java.nio.file.Path pFile;
	for (java.nio.file.Path p : ds) {
		pFile = p.getFileName();
		if (pathMatcher != null && pathMatcher.matches(pFile)) {
			al.add(pFile.toUri().getRawPath());
		}
	}
}

return al.toArray();
	}


/*  */
	public String dup(String inp) throws Exception {
return UUID.randomUUID().toString()/*.replace("-","")*/;
//return "35606224-29d9-4670-b48a-7a9407c2c633".replace("-","");
//return "qwdqwd-qwsvwefv-wevcwewef--wefvwevwe-wev-wev".replace("-","");
	}


/* Concatenate upto 5 strings with defined delimiter */
	public String k2_concat5(String i_str1, String i_str2, String i_str3, String i_str4, String i_str5, String i_delimiter) throws Exception {
if(i_delimiter == null)
	i_delimiter = "";

ArrayList<String> list = new ArrayList<String>();
if(i_str1 != null)
	list.add(i_str1);
if(i_str2 != null)
	list.add(i_str2);
if(i_str3 != null)
	list.add(i_str3);
if(i_str4 != null)
	list.add(i_str4);
if(i_str5 != null)
	list.add(i_str5);

return org.apache.commons.lang3.StringUtils.join(list, i_delimiter);

//
//StringBuilder sb = new StringBuilder();
//
//if(i_str1 != null)
//	sb.append(i_str1);
//if(i_str2 != null){
//	if(sb.length()>0) sb.append(i_delimiter);
//	sb.append(i_str2);
//}
//if(i_str3 != null){
//	if(sb.length()>0) sb.append(i_delimiter);
//	sb.append(i_str3);
//}
//if(i_str4 != null){
//	if(sb.length()>0) sb.append(i_delimiter);
//	sb.append(i_str4);
//}
//if(i_str5 != null){
//	if(sb.length()>0) sb.append(i_delimiter);
//	sb.append(i_str5);
//}
//
//return sb.toString();
	}


/* Find position of last occurrence of a char in a string */
	public Integer k2_strrpos(String str, String substring) throws Exception {
if(str == null || substring == null)
	return -1;
return str.lastIndexOf(substring);
	}


/* A template function to be used as a root function for parser map.
The function scans a folder for delimited files based on a file name pattern, parses the files and generates a stream of records.
 */
	public void k2_FolderStreamReader(String folderPath, String folderPathDebug, String regexFilter, String recordDelimiter, String fieldDelimiter, String enclosingChar) throws Exception {
boolean inDebug = inDebugMode();
if (inDebug){
	folderPath = folderPathDebug;
}

if(folderPath == null || folderPath.isEmpty()){
	throw new IllegalArgumentException("Please Populate Mandatory Parameter: Folder Path");
}

if(enclosingChar == null || enclosingChar.isEmpty()){
	throw new IllegalArgumentException("Please Populate Mandatory Parameter: Enclosing Char");
}

if(fieldDelimiter == null || fieldDelimiter.isEmpty()){
	throw new IllegalArgumentException("Please Populate Mandatory Parameter: Field Delimiter");
}

if(regexFilter == null || regexFilter.isEmpty()){
	throw new IllegalArgumentException("Please Populate Mandatory Parameter: Regex Filter");
}

if(recordDelimiter == null || recordDelimiter.isEmpty()){
	throw new IllegalArgumentException("Please Populate Mandatory Parameter: Record Delimiter");
}

FolderReader reader = new FolderReader(folderPath, regexFilter);
InputStreamReader stream = null;

while ((stream = reader.getNextStream()) != null) {	
	Object[] row = null;
	do {
		row = getStreamMap(stream, enclosingChar.charAt(0), fieldDelimiter.charAt(0), recordDelimiter);
	    yield(row);
	} while (row !=null && row[0] != null);
	
	// Drop stream only in running mode
	if(!inDebug) { // Running mode
		reader.dropStream(stream);
	}
}

	}


/* Perform a regular expression search and replace */
	public String k2_regexp_replace(String pattern, String replacement, String subject) throws Exception {
if(pattern == null || subject == null || replacement == null)
	return null;
return subject.replaceAll(pattern, replacement);
	}


/*  */
	public String k2_currentTimeStamp() throws Exception {
java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss:S");
java.util.Date date = new java.util.Date();
return dateFormat.format(date);
	}


/* oracle to_date() */
	public String TO_DATE(String source, String format) throws Exception {
return OracleToDate.toDate(source,format);
		 

	


	}


/*  */
	public String getConcatNewPrdCdRef() throws Exception {
String sql = "SELECT NEW_PRODUCT_CODE FROM YP_PRODUCT P, REF_YP_PRODUCT_TRN T WHERE T.OLD_PRODUCT_CODE=P.PRODUCT_CODE";

ResultSetWrapper rs = DBQuery("ludb", sql, null);

String prd_cd_new="";

for(Object[] row : rs) {
	prd_cd_new = prd_cd_new + row[0];
}

return prd_cd_new;






	}


/* Strip whitespace (or other characters) from the beginning and end of a string */
	public String k2_trim(String i_str, String i_charList) throws Exception {
if(i_str == null)
	return null;
if(i_charList == null || i_charList.isEmpty())
	i_charList = " ";
return org.apache.commons.lang3.StringUtils.strip(i_str, i_charList);
	}


/*  */
	public Object funTestProdutcFunc() throws Exception {
String sql1 = "select CTCR_BATCH_PROG_DT FROM YP_CUSTOMER ";

Object date = DBSelectValue("ludb", sql1, null);

Object[] date_arr = (Object[])k2_breakDate (date.toString(), "yyyy-MM-dd");

String o_date=k2_concat5(date_arr[0].toString(), date_arr[1].toString(), ((Integer)date_arr[2]).toString(), "", "", "_");

Integer percision=2;

int  number1 = ((Integer)date_arr[0]).intValue();
int  number2 = ((Integer)date_arr[1]).intValue();

Double o_num_round=k2_round(k2_multiply((double)number1 ,(double)number2),percision);

String o_strpad=k2_pad(date_arr[2].toString(),"*",4,false);

//String Content=o_date;
//String FileName="TestProductFunc";
//String Location="C:\\k2View";

//k2_Createfile(Content,FileName,Location,0);

return new Object[]{o_date ,o_num_round.toString(),o_strpad};
	}


/*  */
	public String LOWER(String value) throws Exception {
return value.toLowerCase();
	}


/*  */
	public Map<String,Object> k2_RecordValidator(Map<String,Object> map) throws Exception {
return map;

	}


/* Strip whitespace (or other characters) from the beginning of a string */
	public String k2_ltrim(String i_str, String i_charList) throws Exception {
if(i_str == null)
	return null;
if(i_charList == null || i_charList.isEmpty())
	i_charList = " ";
return org.apache.commons.lang3.StringUtils.stripStart(i_str, i_charList);
	}


/*  */
	public String TO_CHAR(String source, String format) throws Exception {
return OracleToDate.toChar(source,format);
	}


/*  */
	public String k2_currentDate() throws Exception {
java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd");
java.util.Date date = new java.util.Date();
return dateFormat.format(date);
	}


/* This function will create a physical file based on an input string. */
	public void k2_Createfile(String i_fileContent, String i_fileName, String i_location, Integer i_append) throws Exception {
if(i_fileContent==null || i_fileName==null || i_location==null)
	return;

java.nio.file.Path path = java.nio.file.Paths.get(i_location, i_fileName);
java.nio.file.StandardOpenOption options = 
	i_append == 1 ?
	java.nio.file.StandardOpenOption.APPEND :
	java.nio.file.StandardOpenOption.CREATE;
java.nio.file.Files.write(
	path,
	i_fileContent.getBytes(java.nio.charset.StandardCharsets.UTF_8),
	options);
	}


/*  */
	public String getEntityID() throws Exception {
//Test

return getInstanceID();
	}


/*  */
	public String concat_shared(String i_str, String i_con_str) throws Exception {
return i_str + i_con_str + "_shared";

//return s.concat ("_RIVI");
//return i_str.concat ("_RIVI");
	}


/*  */
	public Integer rejectRecordMap(String input) throws Exception {
//rejectRecord("Record is reject");

return 1;
	}


/* Strip whitespace (or other characters) from the end of a string */
	public String k2_rtrim(String i_str, String i_charList) throws Exception {
if(i_str == null)
	return null;
if(i_charList == null || i_charList.isEmpty())
	i_charList = " ";
return org.apache.commons.lang3.StringUtils.stripEnd(i_str, i_charList);
	}


/*  */
	public Boolean k2_amIMinNode() throws Exception {
if(inDebugMode()){
	return true;
}

//com.k2view.cdbms.cluster.ClusterUtils.getLiveNodes().iterator().next();
List<java.net.InetAddress> nodes = new ArrayList<>(com.k2view.cdbms.cluster.ClusterUtils.getLiveMembers());
Collections.sort(nodes, Comparator.comparing((java.net.InetAddress arr) -> arr.toString()));
String aNodeId =  org.apache.cassandra.gms.Gossiper.instance.getHostId(nodes.get(0)).toString();
String nodeID = com.k2view.cdbms.cluster.ClusterUtils.getNodeID().toString();

if (nodeID.equals(aNodeId))
	return true;
else
	return false;
	}


/* k2_Multiply two numbers to get a result */
	public Double k2_multiply(Double i_num_1, Double i_num_2) throws Exception {
if (i_num_1 == null || i_num_2 == null){
	return null;
}else{
	return i_num_1 * i_num_2;
}
	}


/* NVL is the Oracle equivelent of IFNULL
If the value is null, replace it with a predifined replacement value
 */
	public String NVL(String value, String replacement) throws Exception {
if(value==null)
	return  replacement;
else
	return value;

	}


/* Round fractions up - Returns the next highest integer value by rounding up value if necessary. */
	public Double k2_ceil(Double value) throws Exception {
if(value == null)
	return null;
return java.lang.Math.ceil(value);
	}


/* Find position of first occurrence of a string */
	public Integer k2_strpos(String str, String substring) throws Exception {
if(str == null || substring == null)
	return -1;
return str.indexOf(substring);
	}


/*  */
	public void getIdRoot(String id) throws Exception {
Object [] obj = new Object[1];
obj[0] = id;
yield(obj);
	}


/*  */
	public String k2_IF(String i_var, String i_var_check_val, String i_var_true_val, String i_var_false_val) throws Exception {
if(i_var == null || i_var_check_val == null)
	return null;

String o_val;
if (i_var.equals(i_var_check_val)){
	o_val=i_var_true_val;
}else{
	o_val=i_var_false_val;
}
return o_val;
	}


/* Make a string uppercase */
	public String k2_strtoupper(String i_str1) throws Exception {
if(i_str1 == null)
	return null;
return i_str1.toUpperCase();
	}


/* sum up to five parameters to get a result */
	public Double k2_plus(Double i_num1, Double i_num2, Double i_num3, Double i_num4, Double i_num5) throws Exception {
if (i_num1 == null) i_num1 = 0d;
if (i_num2 == null) i_num2 = 0d;
if (i_num3 == null) i_num3 = 0d;
if (i_num4 == null) i_num4 = 0d;
if (i_num5 == null) i_num5 = 0d;

return i_num1 + i_num2 + i_num3 + i_num4 + i_num5;
	}


/*  */
	public String k2_currentDateTime() throws Exception {
java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
java.util.Date date = new java.util.Date();
return dateFormat.format(date);
	}


/* get the current instance ID */
	public String k2_getInstanceID() throws Exception {
return getInstanceID();
	}


/* get the current entityID */
	public String k2_getEntityId() throws Exception {
return getInstanceID();
	}


/* Absolute value */
	public Object k2_abs(Double i_num) throws Exception {
if(i_num == null)
	return null;
return java.lang.Math.abs(i_num);
	}


/* Make a string lowercase */
	public String k2_strtolower(String i_str1) throws Exception {
if(i_str1 == null)
	return null;
return i_str1.toLowerCase();
	}


/* Rounds a float - Returns the rounded value of val to specified precision (number of digits after the decimal point). precision can also be negative or zero (default). */
	public Double k2_round(Double value, Integer precision) throws Exception {
if (value==null || precision==null)
      return null;

java.math.BigDecimal bd = new java.math.BigDecimal(value);
bd = bd.setScale(precision, java.math.RoundingMode.HALF_UP);
return bd.doubleValue();
	}


/* when the input is null return the value entered, else return the input itself */
	public String k2_ifNull(String input, String value) throws Exception {
if (input==null){
	return value;
}
else{
	return input;
}
	}


/* This function will pad the i_pad_string to i_str according to i_pad_length */
	public String k2_pad(String i_str, String i_pad_string, Integer i_pad_length, Boolean i_padToRight) throws Exception {
if(i_str == null || i_pad_length == null)
	return null;

if(i_padToRight == null)
	i_padToRight = true;

String o_new_var;
if(i_padToRight){
	o_new_var = org.apache.commons.lang3.StringUtils.rightPad(i_str, i_pad_length, i_pad_string);
}else{
	o_new_var = org.apache.commons.lang3.StringUtils.leftPad(i_str, i_pad_length, i_pad_string);
}
return o_new_var;
	}


/* Decodes a url-parameters String into a map. */
	public Map<String,Object> k2_UrlDecoder(Map<String,Object> map, String key) throws Exception {
if (map == null) 
	return new HashMap<>();

if (key == null || key.equals("null") || key.trim().isEmpty())
	throw new IllegalArgumentException("Key should not be null or empty");

try {
	String val = (String) map.remove(key);
	final Map<String, String> map2 = com.google.common.base.Splitter.on('&').trimResults().withKeyValueSeparator("=").split(val);
	try {
		for (Map.Entry<String, String> entry : map2.entrySet()) {
			String nKey = java.net.URLDecoder.decode(entry.getKey(), "UTF-8");
			String nVal = java.net.URLDecoder.decode(entry.getValue(), "UTF-8");
			map.put(nKey, nVal);
		}
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		return new HashMap<>();
	}
}
catch(Exception e) {
	return map;
}		
return map;

	}


/*  */
	public Object k2_rt1Fields(Map<String,Object> in) throws Exception {
return new Object [] {in.get("c1"), in.get("c2"),in.get("c3"),in.get("c4"),in.get("c5")};
	}


/*  */
	public String lu2json(String startingObject) throws Exception {
return serializeLU(startingObject);
	}


/* Round fractions down - returns the next lowest integer value by rounding down value if necessary. */
	public Double k2_floor(Double value) throws Exception {
if(value == null)
	return null;
return java.lang.Math.floor(value);
	}


/* Modulo of a number */
	public Integer k2_mod(Integer i_number, Integer i_mod) throws Exception {
if(i_number == null || i_mod == null)
	return null;
return i_number % i_mod;
	}


/* Get Rownum value for current row. */
	public Integer getRowNum(String session, String id) throws Exception {
return OracleRownum.get(session, id);
	}





	/* QA Testing */
	public static final String SYSTEM = "QA2";
/*  */
	public static final String CONV_NO = "1";
/*  */
	public static final String LU_NAME = "SHARED";

}
