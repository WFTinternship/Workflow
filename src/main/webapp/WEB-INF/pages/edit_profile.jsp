<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%@page import="com.workfront.internship.workflow.controller.PageAttributes" %>

<c:set var="allPosts" value='<%=request.getAttribute(PageAttributes.ALLPOSTS)%>'/>
<c:set var="appAreas" value='<%=request.getAttribute(PageAttributes.APPAREAS)%>'/>
<c:set var="myAppAreas" value='<%=request.getAttribute(PageAttributes.MY_APPAREAS)%>'/>
<c:set var="postsBySameAppAreaID" value='<%=request.getAttribute(PageAttributes.POSTS_OF_APPAREA)%>'/>
<c:set var="user" value='<%=request.getSession().getAttribute(PageAttributes.USER)%>'/>
<c:set var="message" value='<%=request.getAttribute(PageAttributes.MESSAGE)%>'/>
<c:set var="profileOwnerId" value='<%=request.getAttribute(PageAttributes.PROFILE_OWNER_ID)%>'/>
<c:set var="profileOwner" value='<%=request.getAttribute(PageAttributes.PROFILE_OWNER)%>'/>
<c:set var="numberOfAnswers" value='<%=request.getAttribute(PageAttributes.NUM_OF_ANSWERS)%>'/>


<!DOCTYPE html>
<html lang="en">

<!-- Mirrored from forum.azyrusthemes.com/03_new_topic.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:11 GMT -->
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
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/font-awesome-4.0.3/css/font-awesome.min.css">

    <!-- CSS STYLE-->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>

    <!-- SLIDER REVOLUTION 4.x CSS SETTINGS -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/rs-plugin/css/settings.css"
          media="screen"/>

</head>
<body>
<div id="loader" style="display: none;">
    <div class="leftEye"></div>
    <div class="rightEye"></div>
    <div class="mouth"></div>
</div>
<div class="container-fluid">
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
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <span id="form-img"><img
                            src="/images/logo.png" alt=""
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
    <div class="headernav">
        <div class="container">
            <div class="row">
                <div class="col-lg-1 col-xs-3 col-sm-2 col-md-2 logo "><a href="/"><img
                        src="/images/logo.png" alt=""
                        height=67px width=67px/></a></div>
                <div class="col-lg-4 search hidden-xs hidden-sm col-md-3">
                    <div class="wrap">
                        <form action="/searchPost" method="post" class="form">
                            <div class="pull-left txt"><input type="text" class="form-control" name="postTitle"
                                                              placeholder="Search posts"></div>
                            <div class="pull-right">
                                <button class="btn btn-default" type="submit"><i class="fa fa-search"></i></button>
                            </div>
                            <div class="clearfix"></div>
                        </form>
                    </div>
                </div>
                <div class='col-lg-7 col-xs-12 col-sm-5 col-md-7 avt <c:if test="${user != null}"> logedin </c:if>'>
                    <div class="stnt">
                        <c:if test="${user == null}">
                            <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">Add New Post
                            </button>
                        </c:if>

                        <c:if test="${user != null}">
                            <a href="/new-post">
                                <button class="btn btn-primary">Add New Post</button>
                            </a>
                        </c:if>

                        <c:if test="${user == null}">
                          <span>
                              <a href="/signup"><button class="btn btn-signup">Sign Up</button></a>
                              <a href="/login"><button type="submit" class="btn btn-login">Login</button></a>
                          </span>
                        </c:if>

                    </div>

                    <div class="clearfix"></div>
                    <c:if test="${user != null}">
                        <div class="avatar pull-left dropdown">
                            <a data-toggle="dropdown" href="#"><img
                                    src="${user.avatarURL}" alt="" width="37"
                                    height="37"/></a> <b
                                class="caret"></b>
                            <div class="status green">&nbsp;</div>
                            <ul class="dropdown-menu" role="menu">
                                <li role="presentation"><a role="menuitem" tabindex="-1" href="/users/${user.id}">My
                                    Profile</a>
                                </li>
                                <li role="presentation">
                                    <a role="menuitem" tabindex="-1" href="/edit/${user.id}">Edit Profile</a>
                                </li>
                                <li role="presentation"><a role="menuitem" tabindex="-3" href="/logout">Log Out</a>
                                </li>
                            </ul>
                        </div>

                    </c:if>
                </div>
            </div>
        </div>
    </div>


    <section class="content totop">

        <div class="container">
            <div class="row">
                <div class="col-lg-8 breadcrumbf">
                    <font color="red">${message}</font>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-8">


                    <div class="user-card">
                        <div class="row">
                            <div class="col-md-6 col-sidebar">
                                <div class="avatar-card">
                                    <c:if test="${(user != null) && (user.id == profileOwner.id)}">
                                        <form action="/updateAvatar" class="form newtopic" method="post"
                                              enctype="multipart/form-data">
                                            <div class="avatar center-block">
                                                <input type="image" name="avatar" id="image"
                                                       src="${profileOwner.avatarURL}"
                                                       height="140" width="140"/>
                                                    <%--<label for="avatar" class="btn"><img src="${profileOwner.avatarURL}"--%>
                                                    <%--id="image1" alt="" height="140" width="140"/>--%>
                                                    <%--</label>--%>
                                                <input type="file" name="avatar" id="my_file" style="display: none;"
                                                       required title="Click on the photo to choose a file"/>
                                            </div>
                                            <input class="btn btn-primary" type="submit" value="Update Avatar" required
                                                   title="Click on the photo to choose a file"/>
                                        </form>
                                    </c:if>
                                    <c:if test="${(user == null) || (user !=null && user.id != profileOwner.id)}">
                                        <div class="avatar center-block">
                                            <img src="${profileOwner.avatarURL}"
                                                 id="image2" alt="" height="140" width="140"/>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="col-md-6 col-md-12 col-content">
                                <form action="/edit-profile" class="form newtopic" method="post">
                                    <ul class="post-ul">
                                        <li>
                                            <div class="form-group">
                                                <label for="usr">First Name:</label>
                                                <input type="text" class="form-control" value="${profileOwner.firstName}" name="firstName" id="firstName">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form-group">
                                                <label for="usr">Last Name:</label>
                                                <input type="text" class="form-control" value="${profileOwner.lastName}" name="lastName" id="lastName">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form-group">
                                                <label for="usr">Email:</label>
                                                <input type="text" class="form-control" value="${profileOwner.email}" name="email" id="email">
                                            </div>
                                        </li>
                                        <li>
                                            <div class="form-group">
                                                <label for="usr">Password:</label>
                                                <input type="text" class="form-control" value="000000" name="password" id="password">
                                            </div>
                                        </li>
                                    </ul>

                                    <div class="pull-right">
                                        <button type="submit" class="btn btn-primary">Update</button>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>


                <div class="col-lg-4 col-md-4">

                </div>
                <%--Edit Avatar Place--%>

            </div>
        </div>

        <div class="container">
            <div class="row">
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-lg-12">

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
<%--<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.plugins.min.js"></script>--%>
<%--<script type="text/javascript" src="rs-plugin/js/jquery.themepunch.revolution.min.js"></script>--%>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


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

<script>
    function subscription(element, x) {
        element.checked = !element.checked;
        if (element.checked) {
            unsubscribe(element, x);
        } else {
            subscribe(element, x);
        }
    }
    function subscribe(element, x) {
        $.ajax({
            type: 'post',
            url: '/subscribe/' + x,
            data: {
                type: "subscription"
            },
            success: function (response) {
                element.checked = !element.checked;
            }
        });
    }
    function unsubscribe(element, x) {
        $.ajax({
            type: 'post',
            url: '/unsubscribe/' + x,
            data: {
                type: "subscription"
            },
            success: function (response) {
                element.checked = !element.checked;
            }
        });
    }
</script>


<script>
    function addComment(x) {
        $.ajax({
            type: 'post',
            url: '/new-comment/' + x,
            data: {
                type: "comment"
            },
            success: function (response) {
                document.getElementById("all_comments").innerHTML = response + document.getElementById("all_comments").innerHTML;
                document.getElementById("comment").value = "";
                document.getElementById("username").value = "";
            }
        });
    }
</script>


<script>document.getElementById("my_file").onchange = function () {
    var reader = new FileReader();
    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("image").src = e.target.result;
    };
    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);
};
</script>

<script>
    $("input[type='image']").click(function (event) {
        event.preventDefault();
        $("input[id='my_file']").click();
    });
</script>

<!-- END REVOLUTION SLIDER -->
</body>

<!-- Mirrored from forum.azyrusthemes.home.jsphtml by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 07 Jun 2017 20:05:13 GMT -->
</html>