// var pgContainer = document.getElementById("page_container");
// var cdContainer = pgContainer.childNodes;
// pgContainer.removeChild(cdContainer[1]);
// for (var i = 0; i < cdContainer.length; i++) {
//       cdContainer[i].innerHTML = "";
// }
// function deleteCardContainer(event) {
//   var obj = event.srcElement.parentNode.parentNode.parentNode.parentNode;
//   pgContainer.removeChild(obj);
// }
//             <div class="dropdown-content-rating">
// <a href="#">very_good   <i class="fas fa-star"></i><i class="fas fa-star"></i></a>
// <a href="#">good   <i class="fas fa-star"></i><i class="fas fa-star-half"></i></a>
// <a href="#">mediocre   <i class="fas fa-star"></i></a>
// <a href="#">bad   <i class="fas fa-star-half"></i></a>
// </div>
var now = new Date();
var removedCard = new Array();
// var cards = new Array();
var users = new Array();
// var cards = new Array();
var $span_rating_dropdown = $("<span class='dropdown-content-rating'></span>");
var $span_rating_dropdown_veryGood = $(
    "<a href='javascript:void(0);'>Very Good   <i class='fas fa-star'></i>  <i class='fas fa-star'></i></a>"
);
var $span_rating_dropdown_good = $(
    "<a href='javascript:void(0);'>Good   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i></a>"
);
var $span_rating_dropdown_mediocre = $(
    "<a href='javascript:void(0);'>Mediocre   <i class='fas fa-star'></i></a>"
);
var $span_rating_dropdown_bad = $(
    "<a href='javascript:void(0);'>Bad   <i class='fas fa-star-half'></i></a>"
);

var $span_genre_dropdown = $("<span class='dropdown-content'></span>");
var $span_genre_dropdown_Thriller = $(
    "<a href='javascript:void(0);'>Thriller</a>"
);
var $span_genre_dropdown_ScienceFiction = $(
    "<a href='javascript:void(0);'>ScienceFiction</a>"
);
var $span_genre_dropdown_Drama = $("<a href='javascript:void(0);'>Drama</a>");
var $span_genre_dropdown_Action = $("<a href='javascript:void(0);'>Action</a>");
var $span_genre_dropdown_Comedy = $("<a href='javascript:void(0);'>Comedy</a>");
var $span_genre_dropdown_Documentary = $(
    "<a href='javascript:void(0);'>Documentary</a>"
);

var $span_platform_dropdown = $("<span class='dropdown-content'></span>");
var $span_platform_dorpdown_Netflix = $(
    "<a style='color:red;' href='javascript:void(0);'>Netflix</a>"
);
var $span_platform_dorpdown_AmazonPrime = $(
    "<a style='color:#232F3E;' href='javascript:void(0);'>AmazonPrime</a>"
);
var $span_platform_dorpdown_Skye = $(
    "<a style='color:#1E90FF;' href='javascript:void(0);'>Skye</a>"
);

var $body = $(document).find("body");
var $pContainer = $body.find(".page-container");
var $cate_by = $body.find(".cate-by");
var username = getParams("username");

// $(window).onload(function () {
//   username = getParams("username");
// });

function getParams(key) {
  var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null) {
    return unescape(r[2]);
  }
  return null;
};

$pContainer.bind("DOMNodeInserted DOMNodeRemoved", function (e) {
  var $footer = $body.find("footer");
  $footer.attr("style", "top:" + ($(document).height() -80) + "px;");
});

$(window).resize(function () {
  var $footer = $body.find("footer");
  $footer.attr("style", "top:" + ($(document).height() -80) + "px;");
});

$(document).on("click", "a.delete_icon", function () {
  var $card_Container = $(this).parent().parent().parent();
  var title = $card_Container.find(".film_name");
  // deleteSeries(title);
  $card_Container.fadeTo("slow", 0.01, function () {
    //fade
    $card_Container.slideUp("slow", function () {
      //slide up
      $card_Container.remove(); //then remove from the DOM
    });
  });
});

$(document).on("click", "a.edit_icon", function () {
  //alert($(this).html());
  var $text_titel = $(this).parent().parent().find(".text_titel");
  var $text_season = $(this).parent().parent().find(".text_season");
  var $text_genre = $(this).parent().parent().find(".text_genre");
  var $text_platform = $(this).parent().parent().find(".text_platform");
  var $text_rating = $(this).parent().parent().find(".text_rating");
  var $text_remark = $(this).parent().parent().find(".text_remark");

  $text_genre.attr("style", "cursor: pointer;");
  $text_platform.attr("style", "cursor: pointer;");
  $text_rating.attr("style", "cursor: pointer;");

  var $icon_bottom = $(this).parent().parent().find(".icon-bottom");
  var $delete_icon = $(this).parent().find(".delete_icon");
  var $detail_icon = $(this).parent().find(".detail_icon");
  var $poster_icon = $(this).parent().find(".poster_icon");
  if ($(this).parent().parent().find("input").length == 0) {
    $poster_icon.fadeTo("slow", 0.01, function () {
      //fade
      $poster_icon.slideUp("slow", function () {
        //slide up
        $poster_icon.attr("style", "display:none"); //then remove from the DOM
      });
    });

    $detail_icon.fadeTo("slow", 0.01, function () {
      //fade
      $detail_icon.slideUp("slow", function () {
        //slide up
        $detail_icon.attr("style", "display:none"); //then remove from the DOM
      });
    });

    $delete_icon.fadeTo("slow", 0.01, function () {
      //fade
      $delete_icon.slideUp("slow", function () {
        //slide up
        $delete_icon.attr("style", "display:none"); //then remove from the DOM
      });
    });

    //$icon_bottom.removeAttr("display");
    $icon_bottom.fadeIn("slow", function () {
      $icon_bottom.attr("style", "display:inline-block;");
    });

    var $span_titel = $text_titel.find("span");
    var $span_season = $text_season.find("span");
    var $span_genre = $text_genre.find("span");
    var $span_platform = $text_platform.find("span");
    var $span_rating = $text_rating.find("span");
    var $span_remark = $text_remark.find("span");

    $span_genre.attr("style", "color: rgba(1, 1, 1, 0.7);");

    appendGenreDropDown($text_genre);
    appendPlatformDropDown($text_platform);
    appendRatingDropDown($text_rating);

    $span_genre_dropdown_Thriller.click(function () {
      $span_genre.text("Thriller");
      appendGenreDropDown($text_genre);
    });
    $span_genre_dropdown_ScienceFiction.click(function () {
      $span_genre.text("ScienceFiction");
      appendGenreDropDown($text_genre);
    });
    $span_genre_dropdown_Drama.click(function () {
      $span_genre.text("Drama");
      appendGenreDropDown($text_genre);
    });
    $span_genre_dropdown_Action.click(function () {
      $span_genre.text("Action");
      appendGenreDropDown($text_genre);
    });
    $span_genre_dropdown_Comedy.click(function () {
      $span_genre.text("Comedy");
      appendGenreDropDown($text_genre);
    });
    $span_genre_dropdown_Documentary.click(function () {
      $span_genre.text("Documentary");
      appendGenreDropDown($text_genre);
    });

    $span_platform_dorpdown_Netflix.click(function () {
      $span_platform.text("Netflix");
      $span_platform.attr("style", "color: red;");
      appendPlatformDropDown($text_platform);
    });
    $span_platform_dorpdown_AmazonPrime.click(function () {
      $span_platform.text("AmazonPrime");
      $span_platform.attr("style", "color: #232f3e;");
      appendPlatformDropDown($text_platform);
    });
    $span_platform_dorpdown_Skye.click(function () {
      $span_platform.text("Skye");
      $span_platform.attr("style", "color: #1e90ff;");
      appendPlatformDropDown($text_platform);
    });

    $span_rating_dropdown_bad.click(function () {
      $span_rating.attr("class", "span_rating_bad");
      $span_rating.html(
          "Bad   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i>"
      );
      appendRatingDropDown($text_rating);
    });
    $span_rating_dropdown_good.click(function () {
      $span_rating.attr("class", "span_rating_good");
      $span_rating.html(
          "Good   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i>"
      );
      appendRatingDropDown($text_rating);
    });
    $span_rating_dropdown_veryGood.click(function () {
      $span_rating.attr("class", "span_rating_veryGood");
      $span_rating.html(
          "Very Good   <i class='fas fa-star'></i>  <i class='fas fa-star'></i>"
      );
      appendRatingDropDown($text_rating);
    });
    $span_rating_dropdown_mediocre.click(function () {
      $span_rating.attr("class", "span_rating_mediocre");
      $span_rating.html("Mediocre   <i class='fas fa-star'></i>");
      appendRatingDropDown($text_rating);
    });

    if ($text_titel.find("input").length == 0) {
      $span_titel.remove();
      // alert($span_titel.text())
      var $input_titel = $(
          "<input value='" + $span_titel.text() + "'></input>"
      );
      $text_titel.append($input_titel);
    }

    if ($text_season.find("input").length == 0) {
      $span_season.remove();
      var $input_season = $(
          "<input value='" + $span_season.text() + "'></input>"
      );
      $text_season.append($input_season);
    }

    if ($text_remark.find("input").length == 0) {
      $span_remark.remove();
      var $input_remark = $(
          "<input value='" + $span_remark.text() + "'></input>"
      );
      $text_remark.append($input_remark);
    }
  }
  //alert(text_titel.html());.
});

$(document).on("click","a.detail_icon" ,function () {
  // alert()
  var $card_Container = $(this).parent().parent().parent();
  var $span_titel = $card_Container.find(".span_titel").text();
  var $span_season = $card_Container.find(".span_season").text();
  var $span_genre = $card_Container.find(".span_genre").text();
  var $span_platform = $card_Container.find(".span_platform").text();
  var $span_rating = $card_Container.find(".text_rating").find("span").text();
  var $span_remark = $card_Container.find(".span_remark").text();
  var queryHtml = "detail.html?id="+$card_Container.attr("data-series-id")+"&season="+$span_season+"&titel="+$span_titel+"&genre="+$span_genre+"&platform="+$span_platform+"&rating="+$span_rating+"&remark="+$span_remark+"&username="+username;
  $(this).attr("href",queryHtml);
});

$(document).on("click", ".back div.icon-bottom", function () {
  $(document).find("span.dropdown-content").remove();
  $(document).find("span.dropdown-content-rating").remove();

  var $text_titel = $(this).parent().parent().find(".text_titel");
  var $text_season = $(this).parent().parent().find(".text_season");
  var $text_genre = $(this).parent().parent().find(".text_genre");
  var $text_platform = $(this).parent().parent().find(".text_platform");
  var $text_rating = $(this).parent().parent().find(".text_rating");
  var $text_remark = $(this).parent().parent().find(".text_remark");

  var org_titel = $text_titel.find("span").text();
  var org_season = $text_season.find("span").text();

  $text_genre.removeAttr("style");
  $text_platform.removeAttr("style");
  $text_rating.removeAttr("style");

  var $icon_bottom = $(this).parent().parent().find(".icon-bottom");
  var $delete_icon = $(this).parent().find(".delete_icon");
  var $detail_icon = $(this).parent().find(".detail_icon");
  var $poster_icon = $(this).parent().find(".poster_icon");

  var $input_titel = $text_titel.find("input");
  var $input_season = $text_season.find("input");
  var $input_remark = $text_remark.find("input");

  var $span_titel = $("<span>" + $input_titel.val() + "</span>");
  var $span_season = $("<span>" + $input_season.val() + "</span>");
  var $span_remark = $("<span>" + $input_remark.val() + "</span>");

  var $span_genre = $text_genre.find(".span_genre");
  var $span_platform = $text_platform.find(".span_platform");
  var $span_rating = $text_rating.find("span");

  $span_genre.removeAttr("style");
  $span_rating.removeAttr("style");
  $span_platform.removeAttr("style");

  $span_titel.attr("class", "span_titel");
  $span_season.attr("class", "span_season");
  $span_remark.attr("class", "span_remark");

  $input_titel.remove();
  $input_season.remove();
  $input_remark.remove();

  $text_titel.append($span_titel);
  $text_season.append($span_season);
  $text_remark.append($span_remark);

  $detail_icon.fadeIn("slow", function () {
    //fade
    $detail_icon.slideDown("slow", function () {
      //slide up
      $detail_icon.removeAttr("style"); //then remove from the DOM
    });
  });

  $poster_icon.fadeIn("slow", function () {
    //fade
    $poster_icon.slideDown("slow", function () {
      //slide up
      $poster_icon.removeAttr("style"); //then remove from the DOM
    });
  });

  $delete_icon.fadeIn("slow", function () {
    //fade
    $delete_icon.slideDown("slow", function () {
      //slide up
      $delete_icon.removeAttr("style"); //then remove from the DOM
    });
  });

  $icon_bottom.fadeOut("slow", function () {
    $icon_bottom.attr("style", "display:none;");
  });

  submitNewCard(getParams("username"),$span_titel.text(), $span_season.text(),$span_genre.text(),$span_platform.text(),$span_rating.text(),$span_remark.text());
  // if(org_titel != $input_titel.val() || org_season != $input_season.val()) {
  //   var $card_Container = $(this).parent().parent();
  //   var $poster = $card_Container.find("img");
  //   getPosterOfSerie($input_titel.val(),$input_season,$poster,$card_Container);
  // }
});

if("dark" === localStorage.getItem("dark_mode"))  {
  document.write('<link rel="stylesheet" type="text/css" href="dark.css">')
  $("#night_mode_button").attr("class", "fa fa-sun");
}

$(document).ready(function () {
  // alert($(window).height())
  // var $page_Container = $(document).find("div.page-container");
  // if(localStorage.getItem("cards").length != 0) {
  //   cards = localStorage.getItem("cards");
  //   cards.forEach(function () {
  //     $page_Container.append();
  //   });
  //
  // }
  // $('a').each(function() {
  //   console.log($(this).text() + ' working');
  // });
  if(username == undefined || username == "") {
    window.location.replace("login.html");
  } else {
    // getCards(getParams("username"));
    //
    getAllCards();

    // var $page_Container = $(document).find("div.page-container");
    // $page_Container.append(
    //     "<div class='card-container'><div class='create-card-cover'><i class='fas fa-plus'></i></div><div class='create-card-back'></div></div>"
    // );
    //addNewCard("Babylon Berlin",2,"Drama","Netflix","Mediocre","Remark");


    if ($body.find("footer").length == 0) {

      var $footer;
      if ($(window).height() < $pContainer.height()) {
        $footer = $(
            "<footer style='top:" +
            ($pContainer.height() + 82) +
            "px;'>Steam™ | SS20 Gruppe IX | Web-Technologie Pflichtaufgabe-1 </footer>"
        );
      } else {
        $footer = $(
            "<footer style='top:" +
            ($(window).height() - 82) +
            "px;'>Steam™ | SS20 Gruppe IX | Web-Technologie Pflichtaufgabe-1 </footer>"
        );
      }
      $body.append($footer);
    }

    // var $footer = $body.find("footer");
    // $footer.attr("style", "top:" + ($(document).height() -84) + "px;");


    var $greeting_text = $(document).find("#greeting_text");
    if (now.getHours() >= 18 && now.getHours() <= 21) {
      $greeting_text.text(username + ", Guten Abend!");
    } else if (now.getHours() > 21 || now.getHours() <= 6) {
      $greeting_text.text(username + ", Gute Nacht!!");
    } else if (now.getHours() > 6 || now.getHours() < 18) {
      $greeting_text.text(username + ", Guten Tag!");
    }
  }
});

$(document).on("click", "div.create-card-cover", function () {
  createNewCard();
});


$(document).on("click", "#new_card_button", function () {
  if ($(document).find("div.create-card-cover").length != 0) {
    createNewCard();
    $("html, body, .content").animate(
        { scrollTop: $(document).height() },
        2700
    );
  } else {
    var $page_Container = $(document).find("div.page-container");
    $page_Container.append(
        "<div class='card-container'><div class='create-card-cover'><i class='fas fa-plus'></i></div><div class='create-card-back'></div></div>"
    );
  }
});

function addNewCard(username,titel,season,genre,platform,rating,remark) {
  var $page_Container = $(document).find("div.page-container");
  var $card_Container = $("<div class='card-container' data-series-id=''>");
  var $cover = $("<div class='cover'></div>");
  var $back = $("<div class='back'></div>");
  var $poster = $("<img src='https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available' class='film_pic'/>");
  var $titel = $("<span class='film_name'>"+titel+"</span>");
  var $back_info = $("<div class='back-info'></div>");

  var $text_titel = $("<label class='text_titel'>Titel: </label>");
  var $text_season = $("<label class='text_season'>Season: </label>");
  var $text_genre = $("<label class='text_genre'>Genre: </label>");
  var $text_platform = $("<label class='text_platform'>Platform: </label>");
  var $text_rating = $("<label class='text_rating'>Rating: </label>");
  var $text_remark = $("<label class='text_remark'>Remark: </label>");
  var $text_username = $("<label class='text_username'>EditBy: </label>");
  $text_username.css("font-size","0.8rem");

  var $span_titel = $("<span class='span_titel'>"+titel+"</span>");
  var $span_season = $("<span class='span_season'>"+season+"</span>");
  var $span_genre = $("<span class='span_genre'>"+genre+"</span>");
  var $span_platform = $("<span class='span_platform'>"+platform+"</span>");
  var $span_rating = $("<span class='span_rating_bad'>"+rating+"</span>");
  var $span_remark = $("<span class='span_remark'>"+remark+"</span>");
  var $span_username = $("<span class='span_username'>"+username+"</span>");

  var $icon_top = $("<div class='icon-top'>");

  $icon_top.append("<a class=\"poster_icon\"><i class=\"far fa-images\"></i></a>&nbsp;&nbsp;");
  $icon_top.append("<a class=\"detail_icon\"><i class=\"fas fa-info\"></i></a>&nbsp;&nbsp;");
  $icon_top.append("<a class=\"edit_icon\"><i class=\"fas fa-edit\"></i></a>&nbsp;&nbsp;");
  $icon_top.append("<a class=\"delete_icon\"><i class=\"fas fa-times\"></i></a>");

  var $icon_bottom = $("<div class='icon-bottom' style='display:none;'>");
  $icon_bottom.append("<i id=\"save_icon\" class=\"fas fa-save\"></i>");

  $cover.append($poster);
  $cover.append($titel);

  $back_info.append($text_titel.append($span_titel));
  $back_info.append($text_season.append($span_season));
  $back_info.append($text_genre.append($span_genre));
  if(platform.indexOf('Netflix') != -1) {
    $span_platform.attr("style","color:red;");
    $span_platform.text("Netflix");
  } else if (platform.indexOf('AmazonPrime') != -1) {
    $span_platform.attr("style","color:#232f3e;");
    $span_platform.text("AmazonPrime");
  } else {
    $span_platform.attr("style","color:#1e90ff;");
    $span_platform.text("Skye");
  }

  $back_info.append($text_platform.append($span_platform));
  if(rating.indexOf("v") != -1) {
    $span_rating.attr("class","span_rating_veryGood")
    $span_rating.html("Very Good <i class=\"fas fa-star\"></i><i class=\"fas fa-star\"></i>");
  } else if(rating.indexOf("ad") != -1) {
    $span_rating.attr("class","span_rating_bad");
    $span_rating.html("Bad <i class=\"fas fa-star-half\"></i>");
  } else if(rating.indexOf("ediocre") != -1) {
    $span_rating.attr("class","span_rating_mediocre");
    $span_rating.html("Mediocre <i class=\"fas fa-star\"></i>");
  } else {
    $span_rating.attr("class","span_rating_good");
    $span_rating.html("Good  <i class=\"fas fa-star\"></i><i class=\"fas fa-star-half\"></i>");
  }
  $back_info.append($text_rating.append($span_rating));
  $back_info.append($text_remark.append($span_remark));
  $back_info.append($text_username.append($span_username));

  $back.append($back_info);
  $back.append($icon_top);
  $back.append($icon_bottom);

  $card_Container.append($cover);
  $card_Container.append($back);
  getPosterOfSerie(titel, season, $poster,$card_Container);
  $page_Container.append($card_Container);
}

function createNewCard() {
  var $page_Container = $(document).find("div.page-container");
  var $create_card_cover = $(document).find("div.create-card-cover");
  var $create_card_back = $(document).find("div.create-card-back");
  var $card_Container = $create_card_cover.parent();
  $create_card_cover.remove();
  var $cover = $("<div class='cover'></div>");
  var $back = $("<div class='back'></div>");
  var $cover_img = $(
      "<img src='https://dummyimage.com/300x400/F8F8F8/727878.jpg&text=No+image+Available' class='film_pic'/>"
  );
  var $cover_text = $("<span class='film_name'></span>");
  var $back_info = $("<div class='back-info'></div>");

  var $icon_bottom = $("<div class='icon-bottom'></div>");
  $icon_bottom.append("<i id='save_icon' class='fas fa-save'></i>");

  var $text_titel = $("<label class='text_titel'>Titel: </label>");
  var $text_season = $("<label class='text_season'>Season: </label>");
  var $text_genre = $("<label class='text_genre'>Genre: </label>");
  var $text_platform = $("<label class='text_platform'>Platform: </label>");
  var $text_rating = $("<label class='text_rating'>Rating: </label>");
  var $text_remark = $("<label class='text_remark'>Remark: </label>");
  var $text_username = $("<label class='text_username'>EditBy: </label>");
  $text_username.css("font-size","0.8rem");

  var $input_titel = $(
      "<input class='input_titel' value='Serie Name'></input>"
  );
  var $input_season = $("<input class='input_season' value='0'></input>");
  var $input_remark = $(
      "<input class='input_remark' value='Comment hier'></input>"
  );

  var $span_genre = $("<span class='span_genre'>Thriller</span>");
  var $span_platform = $(
      "<span class='span_platform' style=\"color:red;\">Netflix</span>"
  );
  var $span_rating = $(
      "<span class='span_rating_good'>Good   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i></span>"
  );
  var $span_username = $("<span class='span_username'>"+getParams("username")+"</span>");

  $back_info.append($text_titel.append($input_titel));
  $back_info.append($text_season.append($input_season));
  $back_info.append($text_genre.append($span_genre));

  $text_genre.attr("style", "cursor: pointer;");
  $text_platform.attr("style", "cursor: pointer;");
  $text_rating.attr("style", "cursor: pointer;");

  appendGenreDropDown($text_genre);
  appendPlatformDropDown($text_platform);
  appendRatingDropDown($text_rating);

  $span_genre_dropdown_Thriller.click(function () {
    $span_genre.text("Thriller");
  });
  $span_genre_dropdown_ScienceFiction.click(function () {
    $span_genre.text("ScienceFiction");
  });
  $span_genre_dropdown_Drama.click(function () {
    $span_genre.text("Drama");
  });
  $span_genre_dropdown_Action.click(function () {
    $span_genre.text("Action");
  });
  $span_genre_dropdown_Comedy.click(function () {
    $span_genre.text("Comedy");
  });
  $span_genre_dropdown_Documentary.click(function () {
    $span_genre.text("Documentary");
  });

  $span_platform_dorpdown_Netflix.click(function () {
    $span_platform.text("Netflix");
    $span_platform.attr("style", "color:red;");
  });
  $span_platform_dorpdown_AmazonPrime.click(function () {
    $span_platform.text("AmazonPrime");
    $span_platform.attr("style", "color:#232f3e;");
  });
  $span_platform_dorpdown_Skye.click(function () {
    $span_platform.text("Skye");
    $span_platform.attr("style", "color:#1e90ff;");
  });

  $span_rating_dropdown_bad.click(function () {
    $span_rating.attr("class", "span_rating_bad");
    $span_rating.html(
        "Bad   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i>"
    );
  });
  $span_rating_dropdown_good.click(function () {
    $span_rating.attr("class", "span_rating_good");
    $span_rating.html(
        "Good   <i class='fas fa-star'></i>  <i class='fas fa-star-half'></i>"
    );
  });
  $span_rating_dropdown_veryGood.click(function () {
    $span_rating.attr("class", "span_rating_veryGood");
    $span_rating.html(
        "Very Good   <i class='fas fa-star'></i>  <i class='fas fa-star'></i>"
    );
  });
  $span_rating_dropdown_mediocre.click(function () {
    $span_rating.attr("class", "span_rating_mediocre");
    $span_rating.html("Mediocre   <i class='fas fa-star'></i>");
  });

  $back_info.append($text_platform.append($span_platform));
  $back_info.append($text_rating.append($span_rating));
  $back_info.append($text_remark.append($input_remark));
  $back_info.append($text_username.append($span_username));

  $create_card_back.append($back_info);
  $create_card_back.append($icon_bottom);
  $create_card_back.attr("style", "transform: rotatey(0deg);");
  $create_card_cover.attr("style", "transform: rotatey(180deg);");

  // $create_card_cover.remove();

  $icon_bottom.click(function () {
    getPosterOfSerie($input_titel.val(), $input_season.val(), $cover_img,$card_Container);
    $cover.append($cover_img);
    $cover.append($cover_text.text($input_titel.val()));
    $create_card_back.remove();

    // $back_info.find("div.dropdown-content").remove();
    // $back_info.find("div.dropdown-content-rating").remove();
    // $text_genre.removeAttr("style");
    // $text_platform.removeAttr("style");
    // $text_rating.removeAttr("style");
    // $back_info.append(
    //   $text_titel.append(
    //     "<span class='span_titel'>" + $input_titel.val() + "</span>"
    //   )
    // );
    // $input_titel.remove();
    // $back_info.append(
    //   $text_season.append(
    //     "<span class='span_season'>" + $input_season.val() + "</span>"
    //   )
    // );
    // $input_season.remove();
    // $back_info.append($text_genre);
    // $back_info.append($text_platform);
    // $back_info.append($text_rating);
    // $back_info.append(
    //   $text_remark.append(
    //     "<span class='span_remark'>" + $input_remark.val() + "</span>"
    //   )
    // );
    // $input_remark.remove();
    $back.append($back_info);
    var $icon_top = $("<div class='icon-top'></div>");
    $icon_top.append(
        "<a class='poster_icon'><i class='far fa-images'></i></a>&nbsp;&nbsp;"
    );

    $icon_top.append("<a class='detail_icon'><i class='fas fa-info'></i></a>&nbsp;&nbsp;");
    $icon_top.append("<a class='edit_icon'><i class='fas fa-edit'></i></a>&nbsp;&nbsp;");
    $icon_top.append(
        "<a class='delete_icon'><i class='fas fa-times'></i></a>"
    );
    $icon_bottom.attr("style", "display:none;");
    $back.append($icon_top);
    $back.append($icon_bottom);

    $card_Container.prepend($cover);
    $card_Container.append($back);
    $card_Container.css("z-index", "1");
    $create_card_back.removeAttr("style");
    $page_Container.append(
        "<div class='card-container'><div class='create-card-cover'><i class='fas fa-plus'></i></div><div class='create-card-back'></div></div>"
    );
    // submitNewCard(getParams("username"),$input_titel.val(), $input_season.val(),$span_genre.text(),$span_platform.text(),$span_rating.text(),$input_remark.val());

    // var insertedCards = new Array();
    // insertedCards.push($card_Container);
    // localStorage.setItem("cards",insertedCards);
  });
}

function appendGenreDropDown($text_genre) {
  $span_genre_dropdown.append($span_genre_dropdown_Thriller);
  $span_genre_dropdown.append($span_genre_dropdown_ScienceFiction);
  $span_genre_dropdown.append($span_genre_dropdown_Drama);
  $span_genre_dropdown.append($span_genre_dropdown_Action);
  $span_genre_dropdown.append($span_genre_dropdown_Comedy);
  $span_genre_dropdown.append($span_genre_dropdown_Documentary);
  $text_genre.append($span_genre_dropdown);
}

function appendPlatformDropDown($text_platform) {
  $span_platform_dropdown.append($span_platform_dorpdown_Netflix);
  $span_platform_dropdown.append($span_platform_dorpdown_AmazonPrime);
  $span_platform_dropdown.append($span_platform_dorpdown_Skye);
  $text_platform.append($span_platform_dropdown);
}

function appendRatingDropDown($text_rating) {
  $span_rating_dropdown.append($span_rating_dropdown_veryGood);
  $span_rating_dropdown.append($span_rating_dropdown_good);
  $span_rating_dropdown.append($span_rating_dropdown_mediocre);
  $span_rating_dropdown.append($span_rating_dropdown_bad);
  $text_rating.append($span_rating_dropdown);
}

$("span.tool_bar #cate_by_button").click(function () {
  if ($cate_by.attr("style") == "opacity: 0.0") {
    $cate_by.attr("style", "opacity: 1.0");
  } else {
    $cate_by.attr("style", "opacity: 0.0");
  }
});



$(document).on("click", "a.poster_icon", function () {
  var $cover_img = $(this).parent().parent().parent().find("img");
  var $poster_src = $cover_img.attr("src");
  $(this).attr("href", $poster_src);
  $(this).attr("target", "_blank");
});

$("span.tool_bar #night_mode_button").click(function () {
  // alert(localStorage.getItem("night"));
  if (localStorage.getItem("dark_mode") === "dark") {
    removeCss("dark.css");
    loadCss("light.css");
    $("#night_mode_button").attr("class", "fa fa-moon");
    localStorage.setItem("dark_mode", "light");
  } else {
    removeCss("light.css");
    loadCss("dark.css");
    $("#night_mode_button").attr("class", "fa fa-sun");
    localStorage.setItem("dark_mode", "dark");
  }
});

function removeCss(href) {
  var links = document.getElementsByTagName("link");
  for (var i = 0; i < links.length; i++) {
    var _href = links[i].href;
    if (links[i] && links[i].href && links[i].href.indexOf(href) != -1) {
      links[i].parentNode.removeChild(links[i]);
    }
  }
}

function loadCss(href) {
  var addSign = true;
  var links = document.getElementsByTagName("link");
  for (var i = 0; i < links.length; i++) {
    if (links[i] && links[i].href && links[i].href.indexOf(href) != -1) {
      addSign = false;
    }
  }
  if (addSign) {
    var $link = document.createElement("link");
    $link.setAttribute("rel", "stylesheet");

    $link.setAttribute("href", href);
    $link.setAttribute("type", "text/css");
    document.getElementsByTagName("head").item(0).appendChild($link);
  }
}

function cateAll() {
  var $page_Container = $(document).find(".page-container");
  if (removedCard.length != 0) {
    removedCard.forEach(function (card) {
      //alert(card.html());
      $page_Container.prepend(card);
      card.fadeIn("slow", function () {
        //fade
        card.removeAttr("style"); //then remove from the DOM
      });
    });
  }
}

function cateRating(id) {
  var $page_Container = $(document).find(".page-container");
  if (removedCard.length != 0) {
    removedCard.forEach(function (card) {
      //alert(card.html());
      $page_Container.prepend(card);
      card.fadeIn("slow", function () {
        //fade
        card.removeAttr("style"); //then remove from the DOM
      });
    });
  }
  $page_Container.find("label.text_rating").each(function () {
    var $text = $(this).find("span").text().replace(/\s*/g, "").toLowerCase();
    if ($text != id) {
      var $card_Container = $(this).parent().parent().parent();
      $card_Container.fadeTo("slow", 0.01, function () {
        //fade
        $card_Container.slideUp("slow", function () {
          //slide up
          removedCard.push($card_Container);
          $card_Container.remove(); //then remove from the DOM
        });
      });
    }
  });
}

function catePlatform(id) {
  var $page_Container = $(document).find(".page-container");
  if (removedCard.length != 0) {
    removedCard.forEach(function (card) {
      //alert(card.html());
      $page_Container.prepend(card);
      card.fadeIn("slow", function () {
        //fade
        card.removeAttr("style"); //then remove from the DOM
      });
    });
  }
  $page_Container.find("label.text_platform").each(function () {
    var $text = $(this).find("span").text().replace(/\s*/g, "").toLowerCase();
    if ($text != id) {
      var $card_Container = $(this).parent().parent().parent();
      $card_Container.fadeTo("slow", 0.01, function () {
        //fade
        $card_Container.slideUp("slow", function () {
          //slide up
          removedCard.push($card_Container);
          $card_Container.remove(); //then remove from the DOM
        });
      });
    }
  });
}

function cateGenre(id) {
  var $page_Container = $(document).find(".page-container");
  if (removedCard.length != 0) {
    removedCard.forEach(function (card) {
      //alert(card.html());
      $page_Container.prepend(card);
      card.fadeIn("slow", function () {
        //fade
        card.removeAttr("style"); //then remove from the DOM
      });
    });
  }
  $page_Container.find("label.text_genre").each(function () {
    var $text = $(this).find("span").text().replace(/\s*/g, "").toLowerCase();

    if ($text != id) {
      var $card_Container = $(this).parent().parent().parent();
      $card_Container.fadeTo("slow", 0.01, function () {
        //fade
        $card_Container.slideUp("slow", function () {
          //slide up
          removedCard.push($card_Container);
          $card_Container.remove(); //then remove from the DOM
        });
      });
    }
  });
}

function  cateUser(id) {
  var $page_Container = $(document).find(".page-container");
  if (removedCard.length != 0) {
    removedCard.forEach(function (card) {
      //alert(card.html());
      $page_Container.prepend(card);
      card.fadeIn("slow", function () {
        //fade
        card.removeAttr("style"); //then remove from the DOM
      });
    });
  }
  $page_Container.find("label.text_username").each(function () {
    var $text = $(this).find("span").text().replace(/\s*/g, "").toLowerCase();

    if ($text != id) {
      var $card_Container = $(this).parent().parent().parent();
      $card_Container.fadeTo("slow", 0.01, function () {
        //fade
        $card_Container.slideUp("slow", function () {
          //slide up
          removedCard.push($card_Container);
          $card_Container.remove(); //then remove from the DOM
        });
      });
    }
  });
}

function getPosterOfSerie(name, season, img, card) {
  var serie_id;
  var poster_path;
  $.ajax({
    url: "https://api.themoviedb.org/3/search/tv",
    data: { api_key: "4e530284712767cef8547ff7f02bc6ad", query: name },
    type: "GET",
    dataType: "json",
    success: function (data) {
      if(data.total_results == 0) {
        serie_id = -1;
        card.attr("data-series-id",serie_id);
      } else {
        serie_id = data.results[0].id;
        card.attr("data-series-id",serie_id);
        poster_path = "http://image.tmdb.org/t/p/w400" + data.results[0].poster_path;
        img.attr("src", poster_path);
        $.ajax({
            url:
                "https://api.themoviedb.org/3/tv/" + serie_id + "/season/" + season,
            data: { api_key: "4e530284712767cef8547ff7f02bc6ad" },
            type: "GET",
            dataType: "json",
            success: function (data) {
              if (data.poster_path != null) {
                // console.log(data);
                poster_path = "http://image.tmdb.org/t/p/w400" + data.poster_path;
                img.attr("src", poster_path);
              }
            }
        });

      }
    }
  });
}

function submitNewCard(username,title,season,genre,platform,score,remark) {
  $.ajax({
    url: "steam_service/series/addOrChangeSeries",
    data: { username: username, title: title, season: season, genre: genre, platform: platform, score: score, remark: remark },
    type: "POST",
    contentType: "application/x-www-form-urlencoded",
    success: function (data) {
      if(data.indexOf("Error") != -1) {
        alert("Error!")
      }
    }
  });
}

function getAllCards() {
  $.ajax({
    url: "steam_service/series/getSeries",
    type: "GET",
    dataType: "json",
    success: function (data) {
      var cards = data;
      for(var i in cards) {
        var username = cards[i].username;
        var title = cards[i].title;
        var season = cards[i].season;
        var genre = cards[i].genre;
        var platform = cards[i].platform;
        var score = cards[i].score;
        var remark = cards[i].remark;
        //console.log(title + " " + season);
        addNewCard(username,title,season,genre,platform,score,remark);
        // var cardData = {
        //   username: username,
        //   title: title,
        //   season: season,
        //   genre: genre,
        //   platform: platform,
        //   score: score,
        //   remark: remark
        // };
        // cards.push(cardData);
      }
      var $page_Container = $(document).find("div.page-container");
      $page_Container.append(
          "<div class='card-container'><div class='create-card-cover'><i class='fas fa-plus'></i></div><div class='create-card-back'></div></div>"
      );
      getUsers(users);
      $.cookie('cards', JSON.stringify(cards));
    }
  });
}

function getCards(username) {
  $.ajax({
    url: "steam_service/series/getSeries/"+username,
    type: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data);
      var cards = data;
      for(var i in cards) {
        var username = cards[i].username;
        var title = cards[i].title;
        var season = cards[i].season;
        var genre = cards[i].genre;
        var platform = cards[i].platform;
        var score = cards[i].score;
        var remark = cards[i].remark;
        //console.log(title + " " + season);
        addNewCard(username,title,season,genre,platform,score,remark);
        // var cardData = {
        //   username: username,
        //   title: title,
        //   season: season,
        //   genre: genre,
        //   platform: platform,
        //   score: score,
        //   remark: remark
        // };
        // cards.push(cardData);
      }
      var $page_Container = $(document).find("div.page-container");
      $page_Container.append(
          "<div class='card-container'><div class='create-card-cover'><i class='fas fa-plus'></i></div><div class='create-card-back'></div></div>"
      );
      getUsers(users);
      // addNewCard("Babylon Berlin",2,"Drama","Netflix","Mediocre","Remark");
      $.cookie('cards', JSON.stringify(cards));
    }
  });
}

function getUsers(users) {
  var $page_Container = $(document).find("div.page-container");
  // var $card_Container = $page_Container.find("div.card-container");
  // $.each($card_Container, function(index, record) {
  //   console.log(index+" "+record);
  // });
  $(document).find(".span_username").each(function () {

    if(users.indexOf($(this).text()) == -1) {
      users.push($(this).text());
    }
  });
  var $ul =  $(document).find(".cate-by li#user_list").find("ul");
  for(var u in users) {
    $ul.append("<li><a href='#' id='"+users[u]+"' onclick='cateUser(id)'>"+users[u]+"</a></li>");
  }
  // users.forEach(function () {
  //   $ul.append("<li><a href='#' id='"+users[i]+"' onclick='cateUser(id)'>"+users[i]+"</a></li>");
  // });

  // $.ajax({
  //   url: "steam_service/user/getUsers",
  //   type: "GET",
  //   dataType: "json",
  //   success: function (data) {
  //     console.log(data);
  //     users = data;
  //     var $ul =  $(document).find(".cate-by li#user_list").find("ul");
  //     for(var i in users){
  //       $ul.append("<li><a href='#' id='"+users[i].username+"' onclick='cateUser(id)'>"+users[i].username+"</a></li>");
  //     }
  //   }
  // });
}

// function deleteSeries(title) {
//   $.ajax({
//     url: "steam_service/series/deleteSeries/" + title,
//     type: "DELETE",
//     dataType: "text",
//     success: function (data) {
//       if(data.indexOf("Error") != -1) {
//         alert("Error!")
//       }
//     }
//   });
//
// }
