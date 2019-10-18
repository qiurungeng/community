package com.study.community.dto;

import com.study.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
    private List<QuestionDto> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private List<Integer> pages=new ArrayList<>();
    private int page;
    private int totalPage;

    public void setPagination(int totalCount, Integer page, Integer size) {

        totalPage=(totalCount%size==0?(totalCount/size):(totalCount/size)+1);
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        this.page=page;


        pages.add(page);
        for (int i=1;i<=3;i++){
            if (page-i>0){
                pages.add(0,this.page-i);
            }
            if (page+i<=totalPage){
                pages.add(this.page+i);
            }
        }

        //是否展示上一页按钮
        showPrevious=!(this.page==1);

        //是否展示下一页
        showNext=!(this.page==totalPage);

        //是否展示第一页
        showFirstPage=(!pages.contains(1));

        //是否展示最后一页
        showEndPage=!pages.contains(totalPage);
    }
}
