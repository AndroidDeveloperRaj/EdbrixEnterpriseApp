package com.edbrix.enterprise.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajk on 16/11/17.
 */

public class CourseContentData {

    @SerializedName("description")
    private String description;

    @SerializedName("submit_type")
    private String submit_type;

    @SerializedName("submit_data")
    private SubmitData submit_data;

    @SerializedName("content")
    private String webContent;

    private String img_content;
    private String audio_content;
    private String video_content;
    private String doc_content;
    private String iframe_content;


    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getSubmit_type ()
    {
        return submit_type;
    }

    public void setSubmit_type (String submit_type)
    {
        this.submit_type = submit_type;
    }

    public SubmitData getSubmit_data ()
    {
        return submit_data;
    }

    public void setSubmit_data (SubmitData submit_data)
    {
        this.submit_data = submit_data;
    }

    public String getWebContent() {
        return webContent;
    }

    public void setWebContent(String webContent) {
        this.webContent = webContent;
    }

    public String getImg_content() {
        return img_content;
    }

    public void setImg_content(String img_content) {
        this.img_content = img_content;
    }

    public String getAudio_content() {
        return audio_content;
    }

    public void setAudio_content(String audio_content) {
        this.audio_content = audio_content;
    }

    public String getVideo_content() {
        return video_content;
    }

    public void setVideo_content(String video_content) {
        this.video_content = video_content;
    }

    public String getDoc_content() {
        return doc_content;
    }

    public void setDoc_content(String doc_content) {
        this.doc_content = doc_content;
    }

    public String getIframe_content() {
        return iframe_content;
    }

    public void setIframe_content(String iframe_content) {
        this.iframe_content = iframe_content;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", submit_type = "+submit_type+", submit_data = "+submit_data+"]";
    }
}
