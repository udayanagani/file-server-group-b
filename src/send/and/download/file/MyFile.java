/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package send.and.download.file;

/**
 *
 * @author Kavishka Heshan
 */

public class MyFile {
    private int id;
    private String name;
    private byte[] data;
    private String fileextention;
    
    public MyFile(int id, String name, byte[] data,String fileExtention){
        this.id=id;
        this.name= name;
        this.data= data;
        this.fileextention= fileextention;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFileextention(String fileextention) {
        this.fileextention = fileextention;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public String getFileExtention() {
        return fileextention;
    }
     
}