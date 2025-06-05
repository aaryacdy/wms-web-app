function searchAnswer() {
    let keyword = $("#askQuestion").val();
    let url = baseUrl + "/search-all-post";
    $("#postReplyId").val("");
    $.post(url, {keyword})
        .done(function (response) {
            let success = response.success;
            let firstTimeShowInstance = $(".firstTimeShow");
            let html = "";
            if (success) {
                let data = response.body;
                $.each(data, function (index, post) {
                    let title = post.title;
                    let blogUrl = post.blogUrl;
                    let youtubeUrl = post.youtubeUrl;
                    let image = post.image;
                    let imageUrl = "";
                    let youtubeText = "";
                    if (image != null && image !== "") {
                        imageUrl = `<div style="text-align: center">
                              <a href="${baseUrl}/files/post/${image}" target="_blank">
                                  <image src="${baseUrl}/files/post/${image}" width="250" height="150"/></a>
                          </div><br>`;
                    }

                    if (youtubeUrl != null && youtubeUrl !== "") {
                        youtubeText = `<div style="text-align: center">
                             <iframe width="500" height="400"
                                        src="https://www.youtube.com/embed/${youtubeUrl}">
                                </iframe>
                          </div><br>`;
                    }

                    html += `<div style="border-width: thin !important;">
                                <h1 style="text-align: center">${index + 1}. ${title}</h1>`
                    if (blogUrl != null && blogUrl !== "") {
                        html += `<h5 style="text-align: center;"><a href="${post.blogUrl}" target="_blank">${post.blogUrl}</a></h5>`;
                    }

                    html += imageUrl;
                    html += youtubeText;
                    html += `<br><br>
                                <div class="description" style="text-align: center;">
                                        ${post.description}
                                </div>
                            </div>
                            <br>`;
                });
                let dynamicContainerInstance = $(".dynamicContainer");
                dynamicContainerInstance.show();
                dynamicContainerInstance.html(html);
                firstTimeShowInstance.hide()
            } else {
                alert("Sorry we can't find any answer for your question. But your questions will be saved in system. You will get you answer as soon as possible.");
                // firstTimeShowInstance.show();
                saveQuestions(keyword);
            }
        })
        .fail(function () {
            alert("Something went wrong.");
        })
        .always(function () {

        });
}

/*<div className="row">
    <div className="col-6">
                                    <textarea type="text" name="feedback" className="form-control"
                                              id="postReplyId_${post.id}"
                                              placeholder="Your feedback please"></textarea>
    </div>

    <button type="button" className="btn btn-success" onClick="submitFeedback('${post.id}')">
        Submit
    </button>

</div>*/

function saveQuestions(keyword) {
    if (keyword) {
        let url = baseUrl + "/keyword/save";
        $.post(url, {keyword})
            .done(function (response) {
                let suscess = response.success;
                console.log(success + " No answer question saved successfully.");
            })
            .fail(function () {
                console.log("Something went wrong.");
            })
            .always(function () {

            });
    }
}

function submitFeedback(postId = "") {
    let postReplyInstance = $("#postReplyId_" + postId + "");
    let feedback = postReplyInstance.val();
    if (postId && feedback) {
        let url = baseUrl + "/post-reply/submit";
        $.post(url, {postId, feedback})
            .done(function (response) {
                let success = response.success;
                if (success) {
                    postReplyInstance.val("");
                    location.reload();
                }
                alert(response.message);
            })
            .fail(function () {
                console.log("Something went wrong.");
            })
            .always(function () {

            });
    } else alert("Please write feedback and then try again.");

}