package cn.onesdream.util;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {
    /*实体类属性名在转换成字符串的时候会将其转换成apkname，所以这里指定转换为json后的属性名*/
    @JsonProperty("APKName")
    private String APKName;
    @JsonProperty("delResult")
    private String delResult;
    public Data apkIsEmpty(){
        this.APKName = "empty";
        return this;
    }

    public Data apkIsExist(){
        this.APKName = "exist";
        return this;
    }

    public Data apkIsNoexist(){
        this.APKName = "noexist";
        return this;
    }
    public Data delIsFalse(){
        this.delResult = "false";
        return this;
    }
    public Data delIsTrue(){
        this.delResult = "true";
        return this;
    }
    public Data delIsNotExist(){
        this.delResult = "notexist";
        return this;
    }

}
