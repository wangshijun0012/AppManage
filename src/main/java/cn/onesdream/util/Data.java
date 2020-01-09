package cn.onesdream.util;


import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {
    /*实体类属性名在转换成字符串的时候会将其转换成apkname，所以这里指定转换为json后的属性名*/
    @JsonProperty("APKName")
    private String APKName;
    public Data isEmpty(){
        this.APKName = "empty";
        return this;
    }
    public Data isExist(){
        this.APKName = "exist";
        return this;
    }
    public Data isNoExist(){
        this.APKName = "noexist";
        return this;
    }
}
