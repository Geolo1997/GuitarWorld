function getMyProfile() {
    $.ajax({
        url: "/GuitarWorld/getMyProfile",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                var user = data.data;
                $('#tv_username').text(user.username);
                $('#img_profile_picture').attr('src', '/GuitarWorld/profilePicture?username=' + user.username);
            } else {
                alert('拉取个人用户信息失败！');
            }
        }
    });
}

function updateProfilePicture() {
    var formData = new FormData();
    formData.append('profilePicture', $('#ip_profile_picture')[0].files[0]);
    $.ajax({
        url: '/GuitarWorld/profilePicture',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (res) {
            alert('上传成功！');
            window.location.reload();
        },
        error: function (message) {
            alert('上传失败：' + message);
        }
    });
}

function logout() {
    $.ajax({
        url: "/GuitarWorld/logout",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                alert('注销成功！');
                window.location.href = 'login.html';
            } else {
            }
        }
    });
}

function getAllWorks() {
    $.ajax({
        url: "/GuitarWorld/getAllWorks",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                var item = $('.works_list_item');
                item.before(item.clone());
                var worksList = data.data;
                console.log(worksList);
                for (var i = 0; i < worksList.length; i++) {
                    var works = worksList[i];
                    item.attr('id', 'work_item_' + works.id);
                    item.children('.author').text(works.author);
                    item.children('.title').text(works.title);
                    item.children('.content').text(works.content);
                    item.children('.detail').attr('onclick', 'worksDetail(' + works.id + ')');
                    item.children('.comments_list').attr('id', 'comments_of_work_' + works.id);
                    // item.children('.detail').click(function () {
                    //     worksDetail(item.children('.comments_list'), works.id);
                    // });
                    if (i !== worksList.length - 1) {
                        item.before(item.clone());
                    }
                }
            } else {
            }
        }
    });
}

function getButtonOfWorks(worksId) {
    return $('#work_item_' + worksId).children('.detail');
}

function worksDetail(worksId) {
    var Button = getButtonOfWorks(worksId);
    console.log(Button.text());
    if (Button.text() === '详情') {
        $.ajax({
            url: "/GuitarWorld/listCommentOfWorks",
            type: "POST",
            data: {
                "worksId": worksId
            },
            dataType: "json",
            success: function (data) {
                if (data.code === 0) {
                    var commentList = data.data;
                    console.log(commentList);
                    var item = $('#comments_of_work_' + worksId).children('.comment_item');
                    item.before(item.clone());
                    for (var i = 0; i < commentList.length; i++) {
                        var comment = commentList[i];
                        item.attr('id', 'comment_item_' + comment.id);
                        item.attr('class', 'comment_item entity');
                        // item.children('.id').text(comment.id);
                        item.children('.author').text(comment.author);
                        item.children('.create_time').text(comment.createTime);
                        item.children('.content').text(comment.content);
                        if (i !== commentList.length - 1) {
                            item.before(item.clone());
                        }
                    }
                }
            }
        });
        Button.text('收起');
    } else {
       $('.entity').remove();
        Button.text('详情');
    }
}