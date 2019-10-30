/**
 * 提交新的评论
 */
function post_comment(id,content,type) {
    if(!content){
        alert("评论不能为空");
        return;
    }

    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":id,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code==200){
                $("#comment-content").val("");
                window.location.reload();   //刷新页面
            }else {
                if (response.code==2003){
                    var isAccepted=confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?" +
                            "client_id=daa33717291993882946&" +
                            "redirect_uri=http://localhost:8080/callback&scop=user&state=1");
                        window.localStorage.setItem("closeable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    })
}

/**
 * 评论问题
 */
function reply_question(){
    var questionId=$("#question_id").val();
    var commentContent=$("#reply-to-question-content").val();
    post_comment(questionId,commentContent,1);
}

/**
 * 评论已有的回复
 */
function reply_comment(e) {
    var commentId=e.getAttribute("data-id");
    var content=$("#reply-to-comment-content-"+commentId).val();
    post_comment(commentId,content,2);
}

/**
 * 显示二级评论
 */
function collapseComment(e) {
    var id=e.getAttribute("data-id");
    var  comments= $("#comment-"+id);
    if(comments.hasClass("in")){
        comments.removeClass("in");
        e.classList.remove("active");
    }else {
        var subCommentContainer=$("#comment-"+id);
        if (subCommentContainer.children().length!=1){
            comments.addClass("in");
            // e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/"+id,function (data) {
                $.each(data.data.reverse(),function (index,comment) {
                    var mediaLeftElement=$("<div>",{
                        "class":"media-left"
                    }).append($("<img>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h5>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var mediaElement=$("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement=$("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("in");
            });
        }

        comments.addClass("in");
        e.classList.add("active");
    }
}

function showSelectTag() {
    var tag_area=$("#select-tag");
    if (tag_area.css("display")=="none"){
        tag_area.show();
    }else {
        tag_area.css("display","none");
    }
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + '、' + value);
        } else {
            $("#tag").val(value);
        }
    }
}