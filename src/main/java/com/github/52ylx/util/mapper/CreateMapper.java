package com.gitee.ylx.util.mapper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 用于生成数据库对应的增删改查的class 以及mapper
 * @author Administrator
 *
 */
public class CreateMapper {
	private String mapperRoot = "a/mapper/";			//mapper 更目录
    private String javaRoot = "a/mcls/";			//mapper 更目录
	private String str = "";//insert语句
	private String zs = "";//注释
	public static void main(String[] args) throws Exception {
		String str ="";
		str = "INSERT INTO `test`.`sys_limit_` (`limit_id`, `limit_name`, `sj_limit_id`, `service_id`,`add_time`,`update_time`) VALUES (NULL, NULL, NULL, NULL);";
		String zs = "系统字典表";//注释
        CreateMapper.create(zs,str);
	}

    /**
     * 实例化一个对象
     */
    public static void create(String zs , String str ) throws Exception{
        CreateMapper f = new CreateMapper();
        f.str = str;
        f.zs = zs;
        f.generate();//执行解析并进行相应的写入
    }

    private void generate() throws Exception{
        str=str.replace("`","");
        String tableName = str.substring(str.indexOf(".")+1,str.indexOf("(")).trim();
        String fileName = tableName;
        String id = str.substring(str.indexOf("(")+1,str.indexOf(",")).trim();
        str = str.substring(indexOf(str,"(",1)+1, indexOf(str,")",1));
        str= str.replaceAll(" ", "");
        //去掉id
        str = str.substring(str.indexOf(",")+1);
        String[]arr = str.split(",");
        int i = fileName.indexOf("_");
        while (i!=-1){
            if (i+2 <fileName.length()){
                fileName = fileName.substring(0, i)+fileName.substring(i+1,i+2).toUpperCase() + fileName.substring(i+2);
            }else{
                fileName = fileName.substring(0,i);
            }
            i = fileName.indexOf("_");
        }
        fileName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
        genMapper(fileName,tableName,id,arr);
        genJava(fileName,tableName,id,arr);
    }

    private void genJava(String fileName, String tableName,String id, String[] arr) throws IOException {
        File file = new File(javaRoot+fileName+".txt");
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        file.createNewFile();
        System.out.println("文件位置:>>>>"+file.getPath());
        String str =
                "    @Autowired\n" +
                "    private Dao dao;\n" +
                "\n" +
                "    @Doc(name = \"查询"+zs+"(单条)\", method = \"select"+fileName+"\", type = \"\", auth = \"\", day = \"\"\n" +
                "           ,in=\"\"\n" +
                "           ,out=\"\")\n" +
                "    public Var select"+fileName+"(Var var){\n" +
                "       LX.exMap(var,\""+id+"\");\n"+
                "       return dao.findforObj(\""+fileName+"Mapper.select"+fileName+"\",var);\n" +
                "    }\n\n" +
                "    @Doc(name = \"查询"+zs+"(列表)\", method = \"select"+fileName+"List\", type = \"\", auth = \"\", day = \"\"\n" +
                "           ,in=\"\"\n" +
                "           ,out=\"\")\n" +
                "    public List select"+fileName+"List(Var var){\n" +
                "        return dao.findforList(\""+fileName+"Mapper.select"+fileName+"\",var);\n" +
                "    }\n\n" +
                "    @Doc(name = \"查询"+zs+"(分页)\", method = \"select"+fileName+"ListPage\", type = \"\", auth = \"\", day = \"\"\n" +
                "           ,in=\"\"\n" +
                "           ,out=\"\")\n" +
                "    public Page select"+fileName+"ListPage(Var var){\n" +
                "        return dao.listPage(\""+fileName+"Mapper.select"+fileName+"\",var);\n" +
                "    }\n\n" +
                "    @Doc(name = \"新增(修改)"+zs+"\", method = \"save"+fileName+"\", type = \"\", auth = \"\", day = \"\"\n" +
                "           ,in=\"\")\n" +
                "    public void save"+fileName+"(Var var){\n" +
                "        //LX.exMap(var,\"service_name\");\n" +
                "        if (LX.isEmpty(var.get(\""+id+"\"))){\n" +
                "            var.put(\""+id+"\",LX.uuid());\n" +
                "            dao.exec(\""+fileName+"Mapper.save"+fileName+"\",var);\n" +
                "        }else{\n" +
                "            dao.exec(\""+fileName+"Mapper.update"+fileName+"\",var);\n" +
                "        }\n" +
                "    }\n\n" +
                "    @Doc(name = \"删除"+zs+"\", method = \"delete"+fileName+"\", type = \"\", auth = \"\", day = \"\"\n" +
                "           ,in=\""+id+"=ID,ids=ID列表=使用逗号分隔\" )\n" +
                "    public void delete"+fileName+"(Var var){\n" +
                "        LX.exObj(var,\"请选择需要删除的记录!\");\n" +
                "        if (LX.isNotEmpty(var.get(\"ids\"))){//注意 list格式为id,id...\n" +
                "            var.put(\"list\",var.getStr(\"ids\").split(\",\"));\n" +
                "            dao.exec(\""+fileName+"Mapper.delAll"+fileName+"\",var);\n" +
                "        }else{\n" +
                "            LX.exMap(var,\""+id+"\");\n" +
                "            dao.exec(\""+fileName+"Mapper.delete"+fileName+"\",var);\n" +
                "        }\n" +
                "    }";
        PrintWriter pw = new PrintWriter(file,"utf-8");
        pw.println(str);
        pw.close();
    }

    public  void genMapper(String fileName,String tableName,String id ,String[] arr) throws Exception{
		File file = new File(mapperRoot+fileName+"Mapper.xml");
		File fileParent = file.getParentFile();
		if(!fileParent.exists()){
		    fileParent.mkdirs();
		}
		file.createNewFile();
        System.out.println("文件位置:>>>>"+file.getPath());
		PrintWriter pw = new PrintWriter(file,"utf-8");
		pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		pw.println("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		pw.println("<mapper namespace=\"mapper."+fileName+"Mapper\">");
		pw.flush();
		pw.println();
		pw.println();
		pw.println("    <!-- 添加"+zs+" -->");
		pw.println("    <insert id=\"save"+fileName+"\" parameterType=\"map\">");
		pw.println("        insert into "+tableName);
		pw.println("        (");
		 pw.println("            "+id);
		for(String a:arr){
		    switch (a){
                case "update_time":
                    pw.println("            ,update_time");
                    break;
                case "add_time":
                    pw.println("            ,add_time");
                    break;
                case "is_enabled":
                    pw.println("            ,is_enabled");
                    break;
                    default:
                        pw.println("            <if test=\"_parameter.containsKey('"+a+"')\">");
                        pw.println("                , " + a);
                        pw.println("            </if>");
            }
		}
		pw.println("        )values(");
		 pw.println("            #{"+id+"}");
		for(String a:arr){
            switch (a){
                case "update_time":
                    pw.println("            ,now()");
                    break;
                case "add_time":
                    pw.println("            ,now()");
                    break;
                case "is_enabled":
                    pw.println("            ,'1'");
                    break;
                default:
                    pw.println("            <if test=\"_parameter.containsKey('"+a+"')\">");
                    pw.println("                , #{"+a+"}");
                    pw.println("            </if>");
            }
		}
		pw.println("        )");
		pw.println("    </insert>");

		pw.println("    <!-- 修改"+zs+" -->");
		pw.println("    <update id=\"update"+fileName+"\" parameterType=\"map\">");
		pw.println("        update "+tableName);
		pw.println("        set");
		pw.println("            "+id+" = #{"+id+"}");
		for(String a:arr){
            if("update_time".equals(a)){
                pw.println("            ,update_time = now()");
            }else{
                pw.println("            <if test=\"_parameter.containsKey('"+a+"')\">");
                pw.println("                , "+a+"= #{"+a+"}");
                pw.println("            </if>");
            }
		}
		pw.println("        where");
		pw.println("            "+id+" = #{"+id+"}");
		pw.println("    </update>");

        StringBuffer buffer = new StringBuffer();
        // 转为char数组
        char[] ch = fileName.toCharArray();
        // 得到大写字母
        for(int i = 0; i < ch.length ; i++){
            if(ch[i] >= 'A' && ch[i] <= 'Z'){
                buffer.append(ch[i]);
            }
        }
        String sx = buffer.toString();
        //表名缩写
        sx = sx.toLowerCase()+"lx";
		pw.println("    <!-- 查詢"+zs+" -->");
		pw.println("    <select id=\"select"+fileName+"\" parameterType=\"map\" resultType=\"var\">");
		pw.println("        select");
		pw.println("            "+sx+"."+id);
		for(String a:arr){
			pw.println("            , "+sx+"."+a);
		}
		pw.println("        from");
		pw.println("            "+tableName + " "+sx);
		pw.println("        <where>");
        pw.println("            <if test=\"_parameter.containsKey('"+id+"')\">");
        pw.println("                and "+sx+"."+id+" = #{"+id+"}");
        pw.println("            </if>");
		for(String a:arr){
            pw.println("            <if test=\"_parameter.containsKey('"+a+"')\">");
			pw.println("                and "+sx+"."+a+"= #{"+a+"}");
			pw.println("            </if>");
		}
		pw.println("        </where>");
		pw.println("    </select>");
		pw.println("    <!-- 删除"+zs+" -->");
		pw.println("    <delete id=\"delete"+fileName+"\" parameterType=\"map\">");
		if(Arrays.asList(str.split(",")).contains("is_enabled")) {
			pw.print("        update "+tableName + " set is_enabled = '0'");
			if(Arrays.asList(str.split(",")).contains("update_time")) pw.print(",update_time = now()");
		}else{
			pw.print("        delete from "+tableName);
		}
		pw.print(" where ");
		pw.println(id+"= #{"+id+"}");
		pw.println("    </delete>");

        pw.println("    <!-- 删除多个"+zs+" -->");
        pw.println("    <delete id=\"delAll"+fileName+"\" parameterType=\"map\">");
        if(Arrays.asList(str.split(",")).contains("is_enabled")) {
            pw.print("        update "+tableName + " set is_enabled = '0'");
            if(Arrays.asList(str.split(",")).contains("update_time")) pw.print(",update_time = now()");
        }else{
            pw.print("        delete from "+tableName);
        }
        pw.print(" where ");
        pw.println(id+" in");
        pw.println("        <foreach collection=\"list\"  item=\"item\" open=\"(\" separator=\",\" close=\")\"  >");
        pw.println("            #{item}");
        pw.println("        </foreach>");
        pw.println("    </delete>");
		pw.println("</mapper>");
		pw.close();
	}
	/**
	 * 查询一个字符串在另一个字符串中第n次出现的位置
	 * @param str
	 * @param str2
	 * @param i
	 * @return
	 */
	public int indexOf(String str , String str2 , int i){
		int wz = -1;
		for (int j = 0; j <i; j++) {
			wz = str.indexOf(str2 , wz+1);
		}
		return wz;
	}
	/**
	 * 一个字符串是否包含另一个字符串
	 * @param str
	 * @param str1
	 * @return
	 */
	public boolean contains(String str , String str1){
		if(str==null||str == ""||str1 ==null || str1==""){
			return false;
		}
		int i = str.indexOf(str1);
		if(i!=-1) return true;
		return false;
		
	}
}
