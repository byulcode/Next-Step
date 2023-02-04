
String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault(); //submit이 자동으로 동작하는 것을 막는다

  //form 데이터들을 자동으로 묶어준다.
  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error : onError,
    success: onSuccess,
  });
}

function onSuccess(json, status){
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

function onError(xhr, status) {
  alert("error");
}

