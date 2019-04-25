package com.itszt.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerCacheUtil {
    private FreeMarkerConfigurer configuration;
    private ThreadLocal<Map> datas=new ThreadLocal<>();
    private String cacheFTLPath;

    public void setCacheFTLPath(String cacheFTLPath) {
        this.cacheFTLPath = cacheFTLPath;
    }

    public void addAttribute(String key, Object value){

        Map map = datas.get();

        if (map==null) {
            datas.set(new HashMap());
        }
        datas.get().put(key, value);

    }

    public void setConfiguration(FreeMarkerConfigurer configuration) {
        this.configuration = configuration;
        configuration.getConfiguration().setDefaultEncoding("UTF-8");
    }

    public boolean processFtlCache(String ftlName, Writer out,String cacheKey) throws Exception{
        ftlName=cacheKey+ftlName;
        File dir=new File(cacheFTLPath);
        if (!dir.exists()) {
            dir.mkdirs();
            return false;
        }
        //1.全部的模板文件
        String[] allftls = dir.list();
        boolean contains = Arrays.asList(allftls).contains(ftlName);
        if (contains) {

            File ftl=new File(dir,ftlName);
            BufferedReader bufferedReader=new BufferedReader(new FileReader(ftl));
            StringBuilder stringBuilder=new StringBuilder();
            String temp=null;
            while ((temp=bufferedReader.readLine())!=null){
                stringBuilder.append(temp);
            }
            out.write(stringBuilder.toString());
            return true;
        }

        return false;
    }

    public void  processFtl(String ftlName, Writer out,String cacheKey) throws Exception {


        //加载模板文件
        Template t1 = configuration.getConfiguration().getTemplate(ftlName);

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        t1.process(datas.get(), new OutputStreamWriter(byteArrayOutputStream));
        t1.setOutputEncoding("UTF-8");

        String ftlContents = byteArrayOutputStream.toString();
        //转储本地静态文件
        File dir=new File(cacheFTLPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(new File(dir,cacheKey+ftlName)));
        bufferedWriter.write(ftlContents);
        bufferedWriter.close();

        //把动态页面数据返回给浏览器
        out.write(ftlContents);
        out.flush();



    }


}
