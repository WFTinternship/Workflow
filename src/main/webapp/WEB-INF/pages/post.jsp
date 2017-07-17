<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%@page import="com.workfront.internship.workflow.web.PageAttributes" %>

<c:set var="allPosts" value='<%=request.getAttribute(PageAttributes.ALLPOSTS)%>'/>
<c:set var="appAreas" value='<%=request.getAttribute(PageAttributes.APPAREAS)%>'/>
<c:set var="user" value='<%=request.getSession().getAttribute(PageAttributes.USER)%>'/>
<c:set var="postsBySameAppAreaID" value='<%=request.getAttribute(PageAttributes.POSTS_OF_APPAAREA)%>'/>
<c:set var="avatar" value='<%=request.getSession().getAttribute(PageAttributes.AVATAR)%>'/>

<c:set var="numberOfLikes" value='<%=request.getAttribute(PageAttributes.NUMOFLIKES)%>'/>
<c:set var="numberOfDislikes" value='<%=request.getAttribute(PageAttributes.NUMOFDISLIKES)%>'/>

<c:set var="post" value='<%=request.getAttribute(PageAttributes.POST)%>'/>
<c:set var="answers" value='<%=request.getAttribute(PageAttributes.ANSWERS)%>'/>
<c:set var="likedPosts" value='<%=request.getAttribute(PageAttributes.LIKEDPOSTS)%>'/>
<c:set var="dislikedPosts" value='<%=request.getAttribute(PageAttributes.DISLIKEDPOSTS)%>'/>
<c:set var="comments" value='<%=request.getAttribute(PageAttributes.POSTCOMMENTS)%>'/>
<c:set var="answerComments" value='<%=request.getAttribute(PageAttributes.ANSWERCOMMENTS)%>'/>
<c:set var="message" value='<%=request.getAttribute(PageAttributes.MESSAGE)%>'/>

<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from forum.azyrusthemes.post.jsphtml by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:20 GMT -->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Workflow</title>
    <%----%>
    <link rel="apple-touch-icon" sizes="57x57" href="/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="32x32"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="96x96"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="icon" type="image/png" sizes="16x16"
          href="https://www.workfront.com/wp-content/themes/dragons/images/favicon.ico">
    <link rel="manifest" href="/manifest.json">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">
    <%----%>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom -->
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='${pageContext.request.contextPath}/text/css'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome-4.0.3/css/font-awesome.min.css">

    <!-- CSS STYLE-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>

    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/rs-plugin/css/settings.css"
          media="screen"/>

</head>
<body class="topic">
<div class="container-fluid">
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <span id="form-img"><img
                            src="https://www.workfront.com/wp-content/themes/dragons/images/logo_footer.png" alt=""
                            height="60px" width="60px/"></span>
                </div>
                <form action="/login/new-post" method="post">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="usr">Name:</label>
                            <input type="text" class="form-control" name="email" id="usr">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Password:</label>
                            <input type="password" class="form-control" name="password" id="pwd">
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="submit" class="btn btn-login">Login</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%--<!-- Slider -->--%>
    <%--<div class="tp-banner-container">--%>
    <%--<div class="tp-banner">--%>
    <%--<ul>--%>
    <%--<!-- SLIDE  -->--%>
    <%--<li data-transition="fade" data-slotamount="7" data-masterspeed="1500">--%>
    <%--<!-- MAIN IMAGE -->--%>
    <%--<img src="${pageContext.request.contextPath}/images/slide.jpg" alt="slidebg1" data-bgfit="cover"--%>
    <%--data-bgposition="left top"--%>
    <%--data-bgrepeat="no-repeat">--%>
    <%--<!-- LAYERS -->--%>
    <%--</li>--%>
    <%--</ul>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--<!-- //Slider -->--%>


    <div class="headernav">
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 col-md-2 logo "><a href="/"><img
                        src="/images/logo.png" alt=""
                        height=67px width=67px/></a></div>
                <%--<div class="col-lg-3 col-xs-9 col-sm-5 col-md-3 selecttopic">--%>
                <%--<div class="dropdown">--%>
                <%--<a data-toggle="dropdown" href="#">Borderlands 2</a> <b class="caret"></b>--%>
                <%--<ul class="dropdown-menu" role="menu">--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Borderlands 1</a></li>--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-2" href="#">Borderlands 2</a></li>--%>
                <%--<li role="presentation"><a role="menuitem" tabindex="-3" href="#">Borderlands 3</a></li>--%>

                <%--</ul>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="col-lg-4 search hidden-xs hidden-sm col-md-3">
                    <div class="wrap">
                        <form action="#" method="post" class="form">
                            <div class="pull-left txt"><input type="text" class="form-control"
                                                              placeholder="Search Topics"></div>
                            <div class="pull-right">
                                <button class="btn btn-default" type="button"><i class="fa fa-search"></i></button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <div class="col-lg-7 col-xs-12 col-sm-5 col-md-7 avt">
                    <div class="stnt">
                        <c:if test="${user == null}">
                            <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Add
                                New Post
                            </button>
                        </c:if>

                        <c:if test="${user != null}">
                            <a href="/new-post">
                                <button type="submit" class="btn btn-primary">Add New Post</button>
                            </a>
                        </c:if>

                        <c:choose>

                            <c:when test="${user == null}">
                          <span>
                              <a href="/signup"><button type="submit" class="btn btn-signup">Sign Up</button></a>
                              <a href="/login"><button type="submit" class="btn btn-login">Login</button></a>
                          </span>
                            </c:when>

                            <c:when test="${user != null}">
                                <div class="avatar pull-left dropdown">
                                    <a data-toggle="dropdown" href="#"><img src="${user.avatarURL}" alt="" width="37"
                                                                            height="37"/></a>
                                        <%--src="${pageContext.request.contextPath}/images/avatar.jpg" alt=""/></a> <b--%>
                                    <b class="caret"></b>
                                    <div class="status green">&nbsp;</div>
                                    <ul class="dropdown-menu" role="menu">
                                        <li role="presentation"><a role="menuitem" tabindex="-1"
                                                                   href="/users/${user.id}">My Profile</a>
                                        </li>
                                        <li role="presentation"><a role="menuitem" tabindex="-3" href="/logout">Log
                                            Out</a>
                                        </li>
                                    </ul>
                                </div>

                            </c:when>

                        </c:choose>
                    </div>

                    <div class="clearfix"></div>
                </div>
            </div>
        </div>
    </div>


    <section class="content">

        <div class="container">
            <div class="row">
                <div class="col-lg-8 breadcrumbf">
                    <font color="">something</font>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">

                    <!-- POST -->
                    <div class="post beforepagination">
                        <div class="topwrap">
                            <div class="userinfo pull-left">
                                <div class="avatar">
                                    <a href="/users/${post.user.id}">
                                        <img src="${post.user.avatarURL}" alt="" width="37" height="37"/>
                                    </a>
                                    <div class="status green">&nbsp;</div>
                                </div>
                                <div><a class="username" href="/users/${post.user.id}">${post.user.firstName}</a></div>

                                <%--<a href="/appArea/${post.appArea.id}">--%>
                                <%--<div class="views"><i></i>${post.appArea.name}</div>--%>
                                <%--</a>--%>
                            </div>
                            <div class="posttext pull-left">
                                <c:if test="${user.id == post.user.id}">
                                    <div class="edit-post">
                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                        <span>
                                        Edit
                                    </span>
                                    </div>
                                </c:if>
                                <h2>${post.title}</h2>
                                <p>${post.content}</p>
                                <input type="hidden" name="postId" value="${post.id}">
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="postinfobot">

                            <c:set var="isLiked" value="false"/>
                            <c:forEach var="item" items="${likedPosts}">
                                <c:if test="${item eq post}">
                                    <c:set var="isLiked" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:set var="isDisliked" value="false"/>
                            <c:forEach var="item" items="${dislikedPosts}">
                                <c:if test="${item eq post}">
                                    <c:set var="isDisliked" value="true"/>
                                </c:if>
                            </c:forEach>
                            <div class="likeblock pull-left">
                                <span onclick="like(${post.id})" class="up">
                                    <i class="${isLiked ? 'fa fa-thumbs-up' : 'fa fa-thumbs-o-up'}"
                                       id="likeColor${post.id}"></i>
                                    <span id="likeCnt${post.id}">${numberOfLikes[0]}</span>
                                </span>
                                <span onclick="dislike(${post.id})" class="down">
                                    <i class="${isDisliked ? 'fa fa-thumbs-down' : 'fa fa-thumbs-o-down'}"
                                       id="dislikeColor${post.id}"></i>
                                    <span id="dislikeCnt${post.id}">${numberOfDislikes[0]}</span>
                                </span>
                            </div>
                            <div class="prev pull-left">
                                <a href="#"><i class="fa fa-reply"></i></a>
                            </div>

                            <div class="posted pull-left"><i class="fa fa-clock-o"></i>${post.postTime}
                            </div>

                            <div class="next pull-right">
                                <a href="#"><i class="fa fa-share"></i></a>

                                <a href="#"><i class="fa fa-flag"></i></a>
                            </div>
                            <div class="divline"></div>
                            <div class="pull-left apparea">
                                <a href="/appArea/${post.appArea.id}">
                                    <div class="views">${post.appArea.name}</div>
                                </a>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="post-comment">
                            <c:if test="${fn:length(comments) != 0}">
                        <ul class="post-ul">
                                <c:forEach var="comment" items="${comments}">
                                    <li>
                                            ${comment.content}
                                    </li>
                                </c:forEach>
                            </ul>
</c:if>
                            <c:if test="${user != null}">
                                <form action="/new-comment/${post.id}" method="post">
                                    <div class="form-group newcomment">
                                        <input type="comment" class="form-control" id="new-comment"
                                               placeholder="Comment"
                                               name="content">
                                    </div>
                                    <button type="submit" class="btn btn-default" onclick="">Add</button>
                                </form>
                            </c:if>

                            </div>
                    </div><!-- POST -->

                    <div class="paginationf">
                        <div class="pull-left"><a href="#" class="prevnext"></a></div>
                        <div class="pull-left">

                        </div>
                        <div class="pull-left"><a href="#" class="prevnext last"></a>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <h4> ${fn:length(answers)} answers </h4>
                    <!-- POST -->
                    <c:forEach var="answer" items="${answers}" varStatus="answerStatus">
                        <div class="post">
                            <div class="topwrap">
                                <div class="userinfo pull-left">
                                    <div class="avatar">
                                        <a href="/users/${post.user.id}">
                                            <img src="${answer.user.avatarURL}" alt="" width="37" height="37"/>
                                        </a>
                                        <div class="status red">&nbsp;</div>
                                        <div>
                                            <a class="username"
                                               href="/users/${answer.user.id}">${answer.user.firstName}</a>
                                        </div>
                                        <c:if test="${user.id == post.user.id}">
                                            <div class="icons">
                                                <i onclick="bestAnswer(${answer.id})" class="fa fa-check"
                                                   id="tickColor${answer.id}" aria-hidden="true"></i>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="posttext pull-left">

                                    <c:if test="${user.id == answer.user.id}">
                                        <div class="edit-answer">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                                            <span>
                                        Edit
                                    </span>
                                        </div>
                                    </c:if>

                                    <p>${answer.content}</p>
                                    <input type="hidden" name="answerId" value="${answer.id}">
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="postinfobot">

                                <c:set var="isLiked" value="false"/>
                                <c:forEach var="item" items="${likedPosts}">
                                    <c:if test="${item eq answer}">
                                        <c:set var="isLiked" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:set var="isDisliked" value="false"/>
                                <c:forEach var="item" items="${dislikedPosts}">
                                    <c:if test="${item eq answer}">
                                        <c:set var="isDisliked" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <div class="likeblock pull-left">
                                    <span onclick="like(${answer.id})" id="like" class="up">
                                        <i class="${isLiked ? 'fa fa-thumbs-up' : 'fa fa-thumbs-o-up'}"
                                           id="likeColor${answer.id}"></i>
                                        <span id="likeCnt${answer.id}">${numberOfLikes[answerStatus.index + 1]}</span>
                                    </span>
                                    <span onclick="dislike(${answer.id})" class="down">
                                        <i class="${isDisliked ? 'fa fa-thumbs-down' : 'fa fa-thumbs-o-down'}"
                                           id="dislikeColor${answer.id}"></i>
                                        <span id="dislikeCnt${answer.id}">${numberOfDislikes[answerStatus.index + 1]}</span>
                                    </span>
                                </div>

                                <div class="prev pull-left">
                                    <a href="#"><i class="fa fa-reply"></i></a>
                                </div>

                                <div class="posted pull-left"><i class="fa fa-clock-o"></i> ${answer.postTime}</div>

                                <div class="next pull-right">
                                    <a href="#"><i class="fa fa-share"></i></a>

                                    <a href="#"><i class="fa fa-flag"></i></a>
                                </div>

                                <div class="clearfix"></div>
                            </div>
                            <div class="post-comment">
                                <c:if test="${fn:length(comments) != 0}">
                            <ul class="post-ul">
                                    <c:forEach var="comment" items="${answerComments[answerStatus.index]}"
                                               varStatus="commentStatus">
                                        <li>
                                                ${comment.content}
                                        </li>
                                    </c:forEach>
                                </ul>
                                </c:if>
                                <c:if test="${user != null}">
                                    <form action="/new-comment/${answer.id}" method="post">
                                        <div class="form-group newcomment">
                                            <input type="comment" class="form-control" id="new-comment"
                                                   placeholder="Comment"
                                                   name="content">
                                        </div>
                                        <button type="submit" class="btn btn-default">Add</button>
                                    </form>
                                </c:if>
                            </div>


                        </div>

                    </c:forEach>
                    <!-- POST -->


                    <!-- POST -->

                    <div class="container">
                        <div class="row">
                            <div class="col-lg-8 breadcrumbf">
                                <font color="red">${message}</font>
                            </div>
                        </div>
                    </div>
                    <!-- POST -->
                    <c:if test="${user != null}">
                        <div class="post">

                            <form action="/new-answer/${post.id}" class="form" method="post">
                                <hidden></hidden>
                                <div class="topwrap">
                                    <div class="userinfo pull-left">
                                        <div class="avatar">
                                            <img src="${user.avatarURL}" alt="" width="37" height="37"/>
                                            <div class="status red">&nbsp;</div>
                                        </div>
                                    </div>
                                    <div class="posttext pull-left">
                                        <div class="textwraper">
                                            <div class="postreply">Post a Reply</div>
                                            <textarea name="reply" id="reply"
                                                      placeholder="Type your message here"></textarea>
                                        </div>
                                    </div>
                                    <div class="clearfix replybox"></div>
                                </div>
                                <div class="postinfobot replybox">

                                    <div class="notechbox pull-left">
                                        <input type="checkbox" name="note" id="note" class="form-control"/>
                                    </div>

                                    <div class="pull-left">
                                        <label for="note"> Email me when some one post a reply</label>
                                    </div>

                                    <div class="pull-right postreply">
                                        <div class="pull-left smile"><a href="#"><i class="fa fa-smile-o"></i></a></div>
                                        <div class="pull-left">
                                            <button class="btn btn-primary">Post Reply</button>
                                        </div>
                                        <div class="clearfix"></div>
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </form>
                        </div>
                        <!-- POST -->
                    </c:if>

                </div>
                <div class="col-lg-4 col-md-4">

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>Application Areas</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <ul class="cats">
                                <c:forEach var="appArea" items="${appAreas}" varStatus="status">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/appArea/${appArea.id}">${appArea.name}</a>
                                        <span class="badge pull-right">${postsBySameAppAreaID[status.index]}</span>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>Poll of the Week</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <p>Which game you are playing this week?</p>
                            <form action="#" method="post" class="form">
                                <table class="poll">
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color1" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 90%">
                                                    Call of Duty Ghosts
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt1" type="radio" name="opt" value="1">
                                            <label for="opt1"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color2" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 63%">
                                                    Titanfall
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt2" type="radio" name="opt" value="2" checked>
                                            <label for="opt2"></label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="progress">
                                                <div class="progress-bar color3" role="progressbar" aria-valuenow="40"
                                                     aria-valuemin="0" aria-valuemax="100" style="width: 75%">
                                                    Battlefield 4
                                                </div>
                                            </div>
                                        </td>
                                        <td class="chbox">
                                            <input id="opt3" type="radio" name="opt" value="3">
                                            <label for="opt3"></label>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                            <p class="smal">Voting ends on 19th of October</p>
                        </div>
                    </div>

                    <!-- -->
                    <div class="sidebarblock">
                        <h3>My Active Threads</h3>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">This Dock Turns Your iPhone Into a Bedside Lamp</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Who Wins in the Battle for Power on the Internet?</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Sony QX10: A Funky, Overpriced Lens Camera for Your Smartphone</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">FedEx Simplifies Shipping for Small Businesses</a>
                        </div>
                        <div class="divline"></div>
                        <div class="blocktxt">
                            <a href="#">Loud and Brave: Saudi Women Set to Protest Driving Ban</a>
                        </div>
                    </div>


                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="pull-left"><a href="#" class="prevnext"></a></div>
                    <div class="pull-left">

                    </div>
                    <div class="pull-left"><a href="#" class="prevnext last"></a></div>
                    <div class="clearfix"></div>
                </div>
            </div>
        </div>

    </section>

    <footer>
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 logo "><a href="/"><img
                        src="/images/logo.png" alt=""
                        height=67px width=67px/></a></div>
                <div class="col-lg-8 col-xs-9 col-sm-5 ">Workflow 2017</div>
                <div class="col-lg-3 col-xs-12 col-sm-5 sociconcent">
                    <ul class="socialicons">
                        <li><a href="#"><i class="fa fa-facebook-square"></i></a></li>
                        <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                        <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                        <li><a href="#"><i class="fa fa-dribbble"></i></a></li>
                        <li><a href="#"><i class="fa fa-cloud"></i></a></li>
                        <li><a href="#"><i class="fa fa-rss"></i></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</div>

<!-- get jQuery from the google apis -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<!-- SLIDER REVOLUTION 4.x SCRIPTS  -->
<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.plugins.min.js"></script>
<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.revolution.min.js"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Custom -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom.js"></script>


<!-- LOOK THE DOCUMENTATION FOR MORE INFORMATIONS -->
<script type="text/javascript">
    var revapi;
    jQuery(document).ready(function () {
        "use strict";
        revapi = jQuery('.tp-banner').revolution(
            {
                delay: 15000,
                startwidth: 1200,
                startheight: 278,
                hideThumbs: 10,
                fullWidth: "on"
            });
    });	//ready
</script>

<script type="text/javascript">
    function like(x) {
        var likeColor = "likeColor" + x;
        if ($('#' + likeColor).hasClass('fa-thumbs-up')) {
            remove_like(x);
        } else {
            insert_like(x);
        }
    }
    function dislike(x) {
        var dislikeColor = "dislikeColor" + x;
        if ($('#' + dislikeColor).hasClass('fa-thumbs-down')) {
            remove_dislike(x);
        } else {
            insert_dislike(x);
        }
    }


    function insert_like(x) {
        $.ajax({
            type: 'post',
            url: '/like/' + x,
            data: {
                type: "like"
            },
            success: function (response) {
                var likeCnt = "likeCnt" + x;
                var likeColor = "likeColor" + x;
                $('#' + likeCnt).html(response);
                $('#' + likeColor).addClass('fa-thumbs-up');
                $('#' + likeColor).removeClass('fa-thumbs-o-up');
            }
        });
    }

    function remove_like(x) {
        $.ajax({
            type: 'post',
            url: '/removeLike/' + x,
            data: {
                type: "like"
            },
            success: function (response) {
                var likeCnt = "likeCnt" + x;
                var likeColor = "likeColor" + x;
                $('#' + likeCnt).html(response);
                $('#' + likeColor).addClass('fa-thumbs-o-up');
                $('#' + likeColor).removeClass('fa-thumbs-up');
            }
        });
    }

    function insert_dislike(x) {
        $.ajax({
            type: 'post',
            url: '/dislike/' + x,
            data: {
                type: "dislike"
            },
            success: function (response) {
                var dislikeCnt = "dislikeCnt" + x;
                var dislikeColor = "dislikeColor" + x;
                $('#' + dislikeCnt).html(response);
                $('#' + dislikeColor).addClass('fa-thumbs-down');
                $('#' + dislikeColor).removeClass('fa-thumbs-o-down');
            }
        });
    }

    function remove_dislike(x) {
        $.ajax({
            type: 'post',
            url: '/removeDislike/' + x,
            data: {
                type: "dislike"
            },
            success: function (response) {
                var dislikeCnt = "dislikeCnt" + x;
                var dislikeColor = "dislikeColor" + x;
                $('#' + dislikeCnt).html(response);
                $('#' + dislikeColor).addClass('fa-thumbs-o-down');
                $('#' + dislikeColor).removeClass('fa-thumbs-down');
            }
        });
    }
    function bestAnswer(x) {
        var tickColor = "tickColor" + x;
        if ($('#' + tickColor).hasClass('greenTick')) {
            removeBestAnswer(x);
        } else {
            setBestAnswer(x);
        }
    }
    function setBestAnswer(x) {
        $.ajax({
            type: 'post',
            url: '/setBestAnswer/' + x,
            data: {
                type: "like"
            },
            success: function (response) {
                var tickColor = "tickColor" + x;
                $('#' + tickColor).addClass('greenTick');
            }
        });
    }
    function removeBestAnswer(x) {
        $.ajax({
            type: 'post',
            url: '/removeBestAnswer/' + x,
            data: {
                type: "like"
            },
            success: function (response) {
                var tickColor = "tickColor" + x;
                $('#' + tickColor).removeClass('greenTick');
            }
        });
    }
</script>

<!-- END REVOLUTION SLIDER -->
</body>

<!-- Mirrored from forum.azyrusthemes.post.jsphtml by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:21 GMT -->
</html>